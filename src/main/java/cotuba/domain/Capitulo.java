package cotuba.domain;

public class Capitulo implements cotuba.plugin.Capitulo {

	private String titulo;

	private String conteudoHtml;

	public Capitulo() {
	}

	public Capitulo(String titulo, String conteudoHtml) {
		this.titulo = titulo;
		this.conteudoHtml = conteudoHtml;
	}

	@Override
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public String getConteudoHtml() {
		return conteudoHtml;
	}

	public void setConteudoHtml(String conteudoHtml) {
		this.conteudoHtml = conteudoHtml;
	}

}