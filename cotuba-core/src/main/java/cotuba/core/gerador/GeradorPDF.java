package cotuba.core.gerador;

import cotuba.core.application.gerador.Gerador;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.property.AreaBreakType;
import cotuba.core.domain.Capitulo;
import cotuba.core.domain.Ebook;

import java.nio.file.Files;
import java.util.List;

public class GeradorPDF implements Gerador {

	@Override
	public void gerarArquivo(Ebook ebook) {

		try (PdfWriter writer = new PdfWriter(Files.newOutputStream(ebook.getArquivoDeSaida()));
			final PdfDocument pdf = new PdfDocument(writer);
			final Document pdfDocument = new Document(pdf)) {

			for (Capitulo capitulo : ebook.getCapitulos()) {

				final List<IElement> convertToElements = HtmlConverter.convertToElements(capitulo.getConteudoHtml());
				for (IElement element : convertToElements) {
					pdfDocument.add((IBlockElement) element);
				}
				// TODO: não adicionar página depois do último capítulo
				pdfDocument.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			}
		} catch (Exception ex) {
			throw new RuntimeException("Erro ao criar arquivo PDF: " + ebook.getArquivoDeSaida().toAbsolutePath(), ex);
		}

	}

}