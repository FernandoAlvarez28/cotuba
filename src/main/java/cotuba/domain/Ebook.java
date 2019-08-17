package cotuba.domain;

import cotuba.application.gerador.Gerador;
import cotuba.hardcode.Formato;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Ebook {

	private final Formato formato;

	private final Path arquivoDeSaida;

	private final List<Capitulo> capitulos;

	public Ebook(Formato formato, Path arquivoDeSaida) {
		this(formato, arquivoDeSaida, new ArrayList<>());
	}

	public Ebook(Formato formato, Path arquivoDeSaida, List<Capitulo> capitulos) {
		this.formato = formato;
		this.arquivoDeSaida = arquivoDeSaida;
		this.capitulos = capitulos;
	}

	public Gerador getGerador() {
		return Gerador.criarInstanciaDeAcordoComFormato(this.formato);
	}

	public Formato getFormato() {
		return formato;
	}

	public Path getArquivoDeSaida() {
		return arquivoDeSaida;
	}

	public List<Capitulo> getCapitulos() {
		return capitulos;
	}

}