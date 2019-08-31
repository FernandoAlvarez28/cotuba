package cotuba.cli;

import cotuba.cli.exception.IllegalParameterException;
import cotuba.core.application.Cotuba;

public class Main {

	public static void main(String[] args) {

		final LeitorOpcoesCLI leitorOpcoesCLI;
		try {
			leitorOpcoesCLI = new LeitorOpcoesCLI(args);
		} catch (IllegalParameterException e) {
			System.err.println(e.getMessage());
			e.printOptions();
			System.exit(1);
			return;
		}

		Cotuba cotuba = new Cotuba();

		try {
			cotuba.executar(leitorOpcoesCLI);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			if (leitorOpcoesCLI.isModoVerboso()) {
				ex.printStackTrace();
			}
			System.exit(1);
		}

		System.out.println("Arquivo gerado com sucesso: " + leitorOpcoesCLI.getArquivoDeSaida());

	}

}
