package br.fai.vl.model;

import java.sql.Timestamp;

public class Pdf extends Arquivo {
	private String arq_path;
	private Timestamp dataCriacao;
	private int editoraId;
	private int generoId;
	private int autorId;
	private int livroId;
	private int artigoId;

	public String getArq_path() {
		return arq_path;
	}

	public void setArq_path(final String arq_path) {
		this.arq_path = arq_path;
	}

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(final Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
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

	public int getLivroId() {
		return livroId;
	}

	public void setLivroId(final int livroId) {
		this.livroId = livroId;
	}

	public int getArtigoId() {
		return artigoId;
	}

	public void setArtigoId(final int artigoId) {
		this.artigoId = artigoId;
	}

}
