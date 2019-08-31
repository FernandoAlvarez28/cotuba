package cotuba.core.plugin.posgeracao;

import cotuba.core.domain.Ebook;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PosGeracaoEbookLoader {

	private static final List<PosGeracaoEbook> implementacoes;

	static {
		implementacoes = new ArrayList<>();
		ServiceLoader<PosGeracaoEbook> loader = ServiceLoader.load(PosGeracaoEbook.class);

		for (PosGeracaoEbook implementacao : loader) {
			implementacoes.add(implementacao);
		}
	}

	private PosGeracaoEbookLoader() {}

	public static List<PosGeracaoEbook> getImplementacoes() {
		return new ArrayList<>(implementacoes);
	}

	public static void executarImplementacoes(Ebook ebook) {
		for (PosGeracaoEbook posGeracaoEbook : getImplementacoes()) {
			try {
				posGeracaoEbook.executarAposGeracao(ebook);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}