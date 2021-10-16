package br.fai.vl.api.service;

import java.util.List;

import br.fai.vl.model.Entrega;

public interface EntregaService {
	List<Entrega> readAll();

	Entrega readById(int id);

	int create(Entrega entity);

	boolean update(Entrega entity);

	boolean delete(int id);

	String checkDeliveryRequest(int idEmprestimo, int idLeitor);
}
