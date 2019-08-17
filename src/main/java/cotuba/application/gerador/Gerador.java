package cotuba.application.gerador;

import cotuba.domain.Ebook;
import cotuba.gerador.GeradorEPUBComEpublib;
import cotuba.gerador.GeradorPDFComIText;
import cotuba.hardcode.Formato;

public interface Gerador {

	static Gerador criarInstanciaDeAcordoComFormato(Formato formato) {
		if (Formato.PDF.equals(formato)) {
			return new GeradorPDFComIText();

		} else if (Formato.EPUB.equals(formato)) {
			return new GeradorEPUBComEpublib();

		} else {
			throw new RuntimeException("Formato do ebook inválido: " + formato);
		}
	}

	void gerarArquivo(Ebook ebook);

}