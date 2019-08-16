package cotuba.cli;

import cotuba.exception.IllegalParameterException;
import cotuba.hardcode.Formato;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LeitorOpcoesCLI {

	private final Options options;
	private final Path diretorioDosMD;
	private final Formato formato;
	private final Path arquivoDeSaida;
	private final boolean modoVerboso;

	public LeitorOpcoesCLI(String[] args) throws IllegalParameterException {

		this.options = this.gerarOptions();

		final CommandLine cmd;

		try {
			final CommandLineParser cmdParser = new DefaultParser();
			cmd = cmdParser.parse(options, args);
		} catch (ParseException e) {
			throw new IllegalParameterException(e.getMessage(), options);
		}

		String nomeDoDiretorioDosMD = cmd.getOptionValue("dir");

		if (nomeDoDiretorioDosMD != null) {
			diretorioDosMD = Paths.get(nomeDoDiretorioDosMD);
			if (!diretorioDosMD.toFile().isDirectory()) {
				throw new IllegalParameterException(nomeDoDiretorioDosMD + " não é um diretório.", options);
			}
		} else {
			diretorioDosMD = Paths.get(StringUtils.EMPTY);
		}

		final String format = StringUtils.upperCase(cmd.getOptionValue("format"));
		if (EnumUtils.isValidEnum(Formato.class, format)) {
			formato = EnumUtils.getEnum(Formato.class, format);
		} else {
			throw new IllegalParameterException("O formato não é valido!", options);
		}

		String nomeDoArquivoDeSaidaDoEbook = cmd.getOptionValue("output");
		if (nomeDoArquivoDeSaidaDoEbook != null) {
			arquivoDeSaida = Paths.get(nomeDoArquivoDeSaidaDoEbook);
			if (arquivoDeSaida.toFile().exists() && arquivoDeSaida.toFile().isDirectory()) {
				throw new IllegalParameterException(nomeDoArquivoDeSaidaDoEbook + " é um diretório.", options);
			}
		} else {
			arquivoDeSaida = Paths.get("book." + formato.toLowerCase());
		}

		modoVerboso = cmd.hasOption("verbose");
	}

	private Options gerarOptions() {
		Options newOptions = new Options();

		newOptions.addOption(new Option("d", "dir", true,
				"Diretório que contem os arquivos md. Default: diretório atual."));

		newOptions.addOption(new Option("f", "format", true,
				"Formato de saída do ebook. Pode ser: pdf ou epub. Default: pdf"));

		newOptions.addOption(new Option("o", "output", true,
				"Arquivo de saída do ebook. Default: book.{formato}."));

		newOptions.addOption(new Option("v", "verbose", false,
				"Habilita modo verboso."));

		return newOptions;
	}

	private Options getOptions() {
		return options;
	}

	public Path getDiretorioDosMD() {
		return diretorioDosMD;
	}

	public Formato getFormato() {
		return formato;
	}

	public Path getArquivoDeSaida() {
		return arquivoDeSaida;
	}

	public boolean isModoVerboso() {
		return modoVerboso;
	}

}