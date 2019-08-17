package cotuba.hardcode;

import cotuba.application.gerador.Gerador;
import cotuba.gerador.GeradorEPUB;
import cotuba.gerador.GeradorPDF;

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