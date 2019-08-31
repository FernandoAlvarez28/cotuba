package cotuba.core.application.renderizador;

import cotuba.core.domain.Capitulo;
import cotuba.core.renderizador.RenderizadorMdComCommonMark;

import java.nio.file.Path;
import java.util.List;

public interface RenderizadorMd {

	static RenderizadorMd criarInstanciaPadrao() {
		return new RenderizadorMdComCommonMark();
	}

	List<Capitulo> renderizarMdParaHtml(Path diretorioDosMd);

}