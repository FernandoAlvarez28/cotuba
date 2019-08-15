package cotuba.domain;

import cotuba.gerador.Gerador;
import cotuba.gerador.GeradorEPUB;
import cotuba.gerador.GeradorPDF;
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
		if (Formato.PDF.equals(this.formato)) {
			return new GeradorPDF();

		} else if (Formato.EPUB.equals(this.formato)) {
			return new GeradorEPUB();

		} else {
			throw new RuntimeException("Formato do ebook inválido: " + this.formato);
		}
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