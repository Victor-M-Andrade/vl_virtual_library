package br.fai.vl.model;

public class Livro extends Arquivo {
	private Long isbn;
	private int editoraId;
	private int generoId;
	private int autorId;

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(final Long isbn) {
		this.isbn = isbn;
	}

	public int getEditoraId() {
		return editoraId;
	}

	public void setEditoraId(final int editoraId) {
		this.editoraId = editoraId;
	}

	public int getGeneroId() {
		return generoId;
	}

	public void setGeneroId(final int generoId) {
		this.generoId = generoId;
	}

	public int getAutorId() {
		return autorId;
	}

	public void setAutorId(final int autorId) {
		this.autorId = autorId;
	}

}
