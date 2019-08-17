package cotuba.application;

import cotuba.cli.LeitorOpcoesCLI;
import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import cotuba.application.gerador.Gerador;
import cotuba.application.renderizador.RenderizadorMd;

import java.util.List;

public class Cotuba {

	public void executar(LeitorOpcoesCLI leitorOpcoesCLI) {
		final RenderizadorMd renderizadorMd = RenderizadorMd.criarInstanciaPadrao();

		final List<Capitulo> capitulos = renderizadorMd.renderizarMdParaHtml(leitorOpcoesCLI.getDiretorioDosMD());

		final Ebook ebook = new Ebook(leitorOpcoesCLI.getFormato(), leitorOpcoesCLI.getArquivoDeSaida(), capitulos);

		final Gerador gerador = ebook.getGerador();

		gerador.gerarArquivo(ebook);
	}

}