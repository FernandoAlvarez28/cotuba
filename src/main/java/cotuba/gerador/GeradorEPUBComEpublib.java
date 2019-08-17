package cotuba.gerador;

import cotuba.application.gerador.Gerador;
import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

import java.io.IOException;
import java.nio.file.Files;

public class GeradorEPUBComEpublib implements Gerador {

	@Override
	public void gerarArquivo(Ebook ebook) {
		final Book epub = new Book();

		for (Capitulo capitulo : ebook.getCapitulos()) {
			epub.addSection(capitulo.getTitulo(), new Resource(capitulo.getConteudoHtml().getBytes(), MediatypeService.XHTML));
		}

		final EpubWriter epubWriter = new EpubWriter();

		try {
			epubWriter.write(epub, Files.newOutputStream(ebook.getArquivoDeSaida()));
		} catch (IOException ex) {
			throw new RuntimeException("Erro ao criar arquivo EPUB: " + ebook.getArquivoDeSaida().toAbsolutePath(), ex);
		}
	}

}