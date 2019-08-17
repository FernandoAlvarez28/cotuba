package cotuba.application.gerador;

import cotuba.hardcode.Formato;

import java.util.EnumMap;
import java.util.Map;

public class GeradorFactory {

	private static final Map<Formato, Gerador> GERADORES = new EnumMap<>(Formato.class);

	static {
		for (Formato formato : Formato.values()) {
			GERADORES.put(formato, formato.getGeradorCorrespondente());
		}
	}

	private GeradorFactory() {}

	public static Gerador getGerador(Formato formato) {
		final Gerador gerador = GERADORES.get(formato);

		if (gerador != null) {
			return gerador;
		}

		throw new RuntimeException("Formato do ebook inválido: " + formato);
	}

}