package cotuba.plugin;

import cotuba.application.gerador.Gerador;
import cotuba.hardcode.Formato;

import java.util.List;

public interface Ebook {

	Gerador getGerador();

	Formato getFormato();

	List<? extends Capitulo> getCapitulos();

}