package cotuba.tema;

import cotuba.domain.Capitulo;
import cotuba.plugin.TemaLoader;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

public class AplicadorTema {

	public void aplicarTema(Capitulo capitulo) {
		final String html = capitulo.getConteudoHtml();

		final Document document = Jsoup.parse(html);

		final List<String> temas = TemaLoader.getTemas();

		for (String tema : temas) {
			document.select("head").append(StringUtils.join("<style>", tema, "</style>"));
		}

		capitulo.setConteudoHtml(document.html());
	}

}