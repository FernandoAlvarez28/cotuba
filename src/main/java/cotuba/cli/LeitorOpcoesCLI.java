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

		final CommandLine cmd = obterArgumentos(args);

		this.diretorioDosMD = obterDiretorioDaFonteFornecido(cmd.getOptionValue("dir"));
		this.formato = obterFormatoFornecido(cmd.getOptionValue("format"));
		this.arquivoDeSaida = obterCaminhoDoArquivoDeSaidaFornecido(cmd.getOptionValue("output"), this.formato);

		modoVerboso = cmd.hasOption("verbose");
	}

	private CommandLine obterArgumentos(String[] args) throws IllegalParameterException {
		try {
			final CommandLineParser cmdParser = new DefaultParser();
			return cmdParser.parse(options, args);
		} catch (ParseException e) {
			throw new IllegalParameterException(e.getMessage(), options);
		}
	}

	private Path obterDiretorioDaFonteFornecido(String diretorioFornecido) throws IllegalParameterException {

		if (StringUtils.isNotBlank(diretorioFornecido)) {
			final Path diretorioResultado = Paths.get(diretorioFornecido);

			if (!diretorioResultado.toFile().isDirectory()) {
				throw new IllegalParameterException(diretorioFornecido + " não é um diretório.", options);
			}
			return diretorioResultado;
		}

		return Paths.get(StringUtils.EMPTY);
	}

	private Formato obterFormatoFornecido(String formatoFornecido) throws IllegalParameterException {
		final Formato formatoResultado = EnumUtils.getEnum(Formato.class, StringUtils.upperCase(formatoFornecido));

		if (formatoResultado != null) {
			return formatoResultado;
		}

		throw new IllegalParameterException("O formato não é valido!", options);
	}

	private Path obterCaminhoDoArquivoDeSaidaFornecido(String nomeFornecido, Formato formato) throws IllegalParameterException {

		if (StringUtils.isNotEmpty(nomeFornecido)) {
			final Path caminhoResultado = Paths.get(nomeFornecido);

			if (caminhoResultado.toFile().exists() && caminhoResultado.toFile().isDirectory()) {
				throw new IllegalParameterException(nomeFornecido + " é um diretório.", options);
			}

			return caminhoResultado;
		}

		return Paths.get("book." + formato.toLowerCase());
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