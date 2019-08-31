package cotuba.renderizador;

import cotuba.application.renderizador.RenderizadorMd;
import cotuba.domain.Capitulo;
import cotuba.domain.builder.CapituloBuilder;
import cotuba.tema.AplicadorTema;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RenderizadorMdComCommonMark implements RenderizadorMd {

	private static final PathMatcher MATCHER = FileSystems.getDefault().getPathMatcher("glob:**/*.md");

	@Override
	public List<Capitulo> renderizarMdParaHtml(Path diretorioDosMd) {

		final List<Path> arquivosMD = this.listarArquivos(diretorioDosMd);
		final List<Capitulo> capitulos = new ArrayList<>(arquivosMD.size());

		final AplicadorTema aplicadorTema = new AplicadorTema();

		for (Path arquivoMD : arquivosMD) {
			capitulos.add(this.gerarCapitulo(arquivoMD, aplicadorTema));
		}

		return capitulos;
	}

	private List<Path> listarArquivos(Path diretorioDosMd) {
		try (Stream<Path> arquivosMD = Files.list(diretorioDosMd)) {
			return arquivosMD.filter(MATCHER::matches).sorted().collect(Collectors.toList());

		} catch (IOException e) {
			throw new RuntimeException("Erro tentando encontrar arquivos .md em " + diretorioDosMd.toAbsolutePath(), e);
		}
	}

	private Capitulo gerarCapitulo(Path arquivoMD, @Nullable AplicadorTema aplicadorTema) {

		final CapituloBuilder capituloBuilder = new CapituloBuilder();
		final Node document = this.parsearDocumento(arquivoMD, capituloBuilder);

		try {
			final HtmlRenderer renderer = HtmlRenderer.builder().build();
			final String html = renderer.render(document);

			if (aplicadorTema != null) {
				capituloBuilder.comConteudoHtml(aplicadorTema.getHtmlComTema(html));
			} else {
				capituloBuilder.comConteudoHtml(html);
			}

		} catch (Exception ex) {
			throw new RuntimeException("Erro ao renderizar para HTML o arquivo " + arquivoMD, ex);
		}

		return capituloBuilder.build();
	}

	private Node parsearDocumento(Path arquivoMD, CapituloBuilder capituloBuilder) {
		final Parser parser = Parser.builder().build();

		try {
			final Node document = parser.parseReader(Files.newBufferedReader(arquivoMD));

			document.accept(new AbstractVisitor() {
				@Override
				public void visit(Heading heading) {
					if (heading.getLevel() == 1) {
						// capítulo
						final String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
						capituloBuilder.comTitulo(tituloDoCapitulo);
					} else if (heading.getLevel() == 2) {
						// seção
					} else if (heading.getLevel() == 3) {
						// título
					}
				}
			});

			return document;

		} catch (Exception ex) {
			throw new RuntimeException("Erro ao fazer parse do arquivo " + arquivoMD, ex);
		}
	}

}