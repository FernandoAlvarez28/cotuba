package cotuba.core.hardcode;

import cotuba.core.application.gerador.Gerador;
import cotuba.core.gerador.GeradorEPUB;
import cotuba.core.gerador.GeradorPDF;

public enum Formato {

	PDF(new GeradorPDF()),
	EPUB(new GeradorEPUB());

	private final Gerador geradorCorrespondente;

	Formato(Gerador geradorCorrespondente) {
		this.geradorCorrespondente = geradorCorrespondente;
	}

	public Gerador getGeradorCorrespondente() {
		return geradorCorrespondente;
	}

	public String toLowerCase() {
		return name().toLowerCase();
	}

}