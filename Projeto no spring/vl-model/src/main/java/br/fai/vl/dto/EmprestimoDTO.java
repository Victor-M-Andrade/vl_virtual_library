package br.fai.vl.dto;

public class EmprestimoDTO {

	private int idEmprestimo;
	private int codEmprestimo;
	private int idExemplar;
	private String nomeLivro;

	public int getIdEmprestimo() {
		return idEmprestimo;
	}

	public void setIdEmprestimo(final int idEmprestimo) {
		this.idEmprestimo = idEmprestimo;
	}

	public int getIdExemplar() {
		return idExemplar;
	}

	public void setIdExemplar(final int idExemplar) {
		this.idExemplar = idExemplar;
	}

	public String getNomeLivro() {
		return nomeLivro;
	}

	public void setNomeLivro(final String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}

	public int getCodEmprestimo() {
		return codEmprestimo;
	}

	public void setCodEmprestimo(final int codEmprestimo) {
		this.codEmprestimo = codEmprestimo;
	}

}
