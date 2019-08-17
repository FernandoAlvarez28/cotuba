package cotuba.renderizador;

import cotuba.domain.Capitulo;

import java.nio.file.Path;
import java.util.List;

public interface MdRenderer {

	static MdRenderer criarInstanciaPadrao() {
		return new MdRendererImpl();
	}

	List<Capitulo> renderizarMdParaHtml(Path diretorioDosMd);

}