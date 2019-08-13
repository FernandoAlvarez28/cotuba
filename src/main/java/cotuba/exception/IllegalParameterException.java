package cotuba.exception;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class IllegalParameterException extends Exception {

	private final Options options;

	public IllegalParameterException(String s, Options options) {
		super(s);
		this.options = options;
	}

	public void printOptions() {
		final HelpFormatter ajuda = new HelpFormatter();
		ajuda.printHelp("cotuba", options);
	}

}