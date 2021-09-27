package br.fai.vl.model;

import java.sql.Timestamp;

public class Entrega {
	private int id;
	private Timestamp dataSolicitacao;
	private Timestamp dataentrega;
	private boolean entregue;
	private int leitorId;
	private int emprestimoId;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Timestamp getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(final Timestamp dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public Timestamp getDataentrega() {
		return dataentrega;
	}

	public void setDataentrega(final Timestamp dataentrega) {
		this.dataentrega = dataentrega;
	}

	public boolean isEntregue() {
		return entregue;
	}

	public void setEntregue(final boolean entregue) {
		this.entregue = entregue;
	}

	public int getLeitorId() {
		return leitorId;
	}

	public void setLeitorId(final int leitorId) {
		this.leitorId = leitorId;
	}

	public int getEmprestimoId() {
		return emprestimoId;
	}

	public void setEmprestimoId(final int emprestimoId) {
		this.emprestimoId = emprestimoId;
	}

}