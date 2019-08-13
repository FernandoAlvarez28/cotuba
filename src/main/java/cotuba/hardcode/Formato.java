package cotuba.hardcode;

public enum Formato {

	PDF,
	EPUB;

	public String toLowerCase() {
		return name().toLowerCase();
	}

}