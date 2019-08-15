package cotuba.application;

import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import cotuba.gerador.Gerador;
import cotuba.cli.LeitorOpcoesCLI;
import cotuba.renderer.MdRenderer;

import java.util.List;

public class Cotuba {

	public void executar(LeitorOpcoesCLI leitorOpcoesCLI) {
		final MdRenderer mdRenderer = new MdRenderer();

		final List<Capitulo> capitulos = mdRenderer.renderizarMdParaHtml(leitorOpcoesCLI.getDiretorioDosMD());

		final Ebook ebook = new Ebook(leitorOpcoesCLI.getFormato(), leitorOpcoesCLI.getArquivoDeSaida(), capitulos);

		final Gerador gerador = ebook.getGerador();

		gerador.gerarArquivo(ebook);
	}

}