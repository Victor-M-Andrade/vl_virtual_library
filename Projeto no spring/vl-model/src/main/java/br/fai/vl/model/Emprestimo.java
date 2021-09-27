package br.fai.vl.model;

import java.sql.Timestamp;

public class Emprestimo {
	private int id;
	private int codigo;
	private Timestamp dataRealizacao;
	private int leitorId;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(final int codigo) {
		this.codigo = codigo;
	}

	public Timestamp getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(final Timestamp dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public int getLeitorId() {
		return leitorId;
	}

	public void setLeitorId(final int leitor_id) {
		this.leitorId = leitor_id;
	}

}
