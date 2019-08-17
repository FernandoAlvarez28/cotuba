package cotuba.application;

import cotuba.hardcode.Formato;

import java.nio.file.Path;

public interface ParametrosCotuba {

	Path getDiretorioDosMD();

	Path getArquivoDeSaida();

	Formato getFormato();

}