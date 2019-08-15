package cotuba.domain;

public class Capitulo {

	private String titulo;

	private String conteudoHtml;

	public Capitulo() {
	}

	public Capitulo(String titulo, String conteudoHtml) {
		this.titulo = titulo;
		this.conteudoHtml = conteudoHtml;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConteudoHtml() {
		return conteudoHtml;
	}

	public void setConteudoHtml(String conteudoHtml) {
		this.conteudoHtml = conteudoHtml;
	}

}