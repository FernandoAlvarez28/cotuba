package cotuba.core.application;

import cotuba.core.application.gerador.Gerador;
import cotuba.core.application.renderizador.RenderizadorMd;
import cotuba.core.domain.Capitulo;
import cotuba.core.domain.Ebook;
import cotuba.core.plugin.posgeracao.PosGeracaoEbookLoader;

import java.util.List;

public class Cotuba {

	public void executar(ParametrosCotuba parametros) {
		final RenderizadorMd renderizadorMd = RenderizadorMd.criarInstanciaPadrao();

		final List<Capitulo> capitulos = renderizadorMd.renderizarMdParaHtml(parametros.getDiretorioDosMD());

		final Ebook ebook = new Ebook(parametros.getFormato(), parametros.getArquivoDeSaida(), capitulos);

		final Gerador gerador = ebook.getGerador();

		gerador.gerarArquivo(ebook);

		PosGeracaoEbookLoader.executarImplementacoes(ebook);
	}

}