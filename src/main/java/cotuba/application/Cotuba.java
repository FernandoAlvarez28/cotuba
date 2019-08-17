package cotuba.application;

import cotuba.cli.LeitorOpcoesCLI;
import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import cotuba.gerador.Gerador;
import cotuba.renderer.MdRenderer;

import java.util.List;

public class Cotuba {

	public void executar(LeitorOpcoesCLI leitorOpcoesCLI) {
		final MdRenderer mdRenderer = MdRenderer.criarInstanciaPadrao();

		final List<Capitulo> capitulos = mdRenderer.renderizarMdParaHtml(leitorOpcoesCLI.getDiretorioDosMD());

		final Ebook ebook = new Ebook(leitorOpcoesCLI.getFormato(), leitorOpcoesCLI.getArquivoDeSaida(), capitulos);

		final Gerador gerador = ebook.getGerador();

		gerador.gerarArquivo(ebook);
	}

}