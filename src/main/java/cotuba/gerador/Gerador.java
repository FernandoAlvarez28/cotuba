package cotuba.gerador;

import cotuba.domain.Ebook;
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