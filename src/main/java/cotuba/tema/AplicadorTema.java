package cotuba.tema;

import cotuba.domain.Capitulo;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AplicadorTema {

	private static final String CSS = StringUtils.join(
			"h1 {",
			"   border-bottom: 1px dashed black;",
			" }"
	);

	public void aplicarTema(Capitulo capitulo) {
		final String html = capitulo.getConteudoHtml();

		final Document document = Jsoup.parse(html);

		document.select("head").append(StringUtils.join("<style>", CSS, "</style>"));

		capitulo.setConteudoHtml(document.html());
	}

}