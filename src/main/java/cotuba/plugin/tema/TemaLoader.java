package cotuba.plugin.tema;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class TemaLoader {

	private static final List<String> temas;

	static {
		temas = new ArrayList<>();
		ServiceLoader<Tema> loader = ServiceLoader.load(Tema.class);

		for (Tema tema : loader) {
			temas.add(tema.getCss());
		}
	}

	private TemaLoader() {}

	public static List<String> getTemas() {
		return new ArrayList<>(temas);
	}

}