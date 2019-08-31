package cotuba.core.tema;

import cotuba.core.plugin.tema.TemaLoader;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

public class AplicadorTema {

	public String getHtmlComTema(String html) {
		final Document document = Jsoup.parse(html);

		final List<String> temas = TemaLoader.getTemas();

		for (String tema : temas) {
			document.select("head").append(StringUtils.join("<style>", tema, "</style>"));
		}

		return document.html();
	}

}