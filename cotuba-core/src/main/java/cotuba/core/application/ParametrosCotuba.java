package cotuba.core.application;

import cotuba.core.hardcode.Formato;

import java.nio.file.Path;

public interface ParametrosCotuba {

	Path getDiretorioDosMD();

	Path getArquivoDeSaida();

	Formato getFormato();

}