package cotuba.core.domain.builder;

import cotuba.core.domain.Capitulo;

public class CapituloBuilder {

	private String titulo;

	private String conteudoHtml;

	public String getTitulo() {
		return titulo;
	}

	public CapituloBuilder comTitulo(String titulo) {
		this.titulo = titulo;
		return this;
	}

	public String getConteudoHtml() {
		return conteudoHtml;
	}

	public CapituloBuilder comConteudoHtml(String conteudoHtml) {
		this.conteudoHtml = conteudoHtml;
		return this;
	}

	public Capitulo build() {
		return new Capitulo(this.titulo, this.conteudoHtml);
	}

}