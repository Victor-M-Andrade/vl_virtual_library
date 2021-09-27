package br.fai.vl.model;

public class Artigo extends Arquivo {
	private int edicao;
	private int editoraId;
	private int generoId;
	private int autorId;

	public int getEdicao() {
		return edicao;
	}

	public void setEdicao(final int edicao) {
		this.edicao = edicao;
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
