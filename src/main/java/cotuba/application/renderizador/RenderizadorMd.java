package cotuba.application.renderizador;

import cotuba.domain.Capitulo;
import cotuba.renderizador.RenderizadorMdComCommonMark;

import java.nio.file.Path;
import java.util.List;

public interface RenderizadorMd {

	static RenderizadorMd criarInstanciaPadrao() {
		return new RenderizadorMdComCommonMark();
	}

	List<Capitulo> renderizarMdParaHtml(Path diretorioDosMd);

}