package cotuba.application;

import cotuba.application.gerador.Gerador;
import cotuba.application.renderizador.RenderizadorMd;
import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import cotuba.plugin.posgeracao.PosGeracaoEbookLoader;

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