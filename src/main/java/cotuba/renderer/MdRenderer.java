package cotuba.renderer;

import cotuba.domain.Capitulo;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MdRenderer {

	public List<Capitulo> renderizarMdParaHtml(Path diretorioDosMd) {

		final List<Capitulo> capitulos = new ArrayList<>();

		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");
		try (Stream<Path> arquivosMD = Files.list(diretorioDosMd)) {
			arquivosMD.filter(matcher::matches).sorted().forEach(arquivoMD -> {
				final Capitulo capitulo = new Capitulo();
				final Parser parser = Parser.builder().build();
				final Node document;
				try {
					document = parser.parseReader(Files.newBufferedReader(arquivoMD));
					document.accept(new AbstractVisitor() {
						@Override
						public void visit(Heading heading) {
							if (heading.getLevel() == 1) {
								// capítulo
								String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
								capitulo.setTitulo(tituloDoCapitulo);
							} else if (heading.getLevel() == 2) {
								// seção
							} else if (heading.getLevel() == 3) {
								// título
							}
						}

					});
				} catch (Exception ex) {
					throw new RuntimeException("Erro ao fazer parse do arquivo " + arquivoMD, ex);
				}

				try {
					HtmlRenderer renderer = HtmlRenderer.builder().build();
					capitulo.setConteudoHtml(renderer.render(document));

				} catch (Exception ex) {
					throw new RuntimeException("Erro ao renderizar para HTML o arquivo " + arquivoMD, ex);
				}

				capitulos.add(capitulo);

			});
		} catch (IOException ex) {
			throw new RuntimeException("Erro tentando encontrar arquivos .md em " + diretorioDosMd.toAbsolutePath(), ex);
		}

		return capitulos;
	}

}