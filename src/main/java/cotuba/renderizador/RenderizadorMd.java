package cotuba.renderizador;

import cotuba.domain.Capitulo;

import java.nio.file.Path;
import java.util.List;

public interface RenderizadorMd {

	static RenderizadorMd criarInstanciaPadrao() {
		return new RenderizadorMdComCommonMark();
	}

	List<Capitulo> renderizarMdParaHtml(Path diretorioDosMd);

}