package cotuba.cli;

import cotuba.core.application.ParametrosCotuba;
import cotuba.core.hardcode.Formato;
import cotuba.cli.exception.IllegalParameterException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LeitorOpcoesCLI implements ParametrosCotuba {

	private final Options options;
	private final Path diretorioDosMD;
	private final Formato formato;
	private final Path arquivoDeSaida;
	private final boolean modoVerboso;

	public LeitorOpcoesCLI(String[] args) throws IllegalParameterException {
		this.options = this.gerarOptions();

		final CommandLine cmd = obterArgumentos(args);

		this.diretorioDosMD = obterParametroDiretorioDaFonte(cmd.getOptionValue("dir"));
		this.formato = obterParametroFormato(cmd.getOptionValue("format"));
		this.arquivoDeSaida = obterParametroCaminhoDoArquivoDeSaida(cmd.getOptionValue("output"), this.formato);

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

	private Path obterParametroDiretorioDaFonte(String parametroDiretorio) throws IllegalParameterException {

		if (StringUtils.isNotBlank(parametroDiretorio)) {
			final Path diretorioResultado = Paths.get(parametroDiretorio);

			final File file = diretorioResultado.toFile();
			if (!file.exists()) {
				throw new IllegalParameterException("Entrada \"" + parametroDiretorio + "\" não existe.", options);
			} else if (!file.isDirectory()) {
				throw new IllegalParameterException("Entrada \"" + parametroDiretorio + "\" não é um diretório.", options);
			}
			return diretorioResultado;
		}

		return Paths.get(StringUtils.EMPTY);
	}

	private Formato obterParametroFormato(String parametroFormato) throws IllegalParameterException {
		final Formato formatoResultado = EnumUtils.getEnum(Formato.class, StringUtils.upperCase(parametroFormato));

		if (formatoResultado != null) {
			return formatoResultado;
		}

		throw new IllegalParameterException("O formato não é valido!", options);
	}

	private Path obterParametroCaminhoDoArquivoDeSaida(String parametroNome, Formato formato) throws IllegalParameterException {

		if (StringUtils.isNotEmpty(parametroNome)) {
			final Path caminhoResultado = Paths.get(parametroNome);

			final File file = caminhoResultado.toFile();
			if (file.isDirectory()) {
				throw new IllegalParameterException("Saída \"" + parametroNome + "\" é um diretório.", options);
			} else if (file.exists()) {
				throw new IllegalParameterException("Saída \"" + parametroNome + "\" já existe.", options);
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

	@Override
	public Path getDiretorioDosMD() {
		return diretorioDosMD;
	}

	@Override
	public Formato getFormato() {
		return formato;
	}

	@Override
	public Path getArquivoDeSaida() {
		return arquivoDeSaida;
	}

	public boolean isModoVerboso() {
		return modoVerboso;
	}

}