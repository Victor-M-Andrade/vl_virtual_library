package br.fai.vl.model;

public abstract class Arquivo{
	private long id;
	private String titulo;
	private int numPaginas;
	
	public long getId() {
		return id;
	}
	public void setId(final long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}
	public int getNumPaginas() {
		return numPaginas;
	}
	public void setNumPaginas(final int numPaginas) {
		this.numPaginas = numPaginas;
	}
}
