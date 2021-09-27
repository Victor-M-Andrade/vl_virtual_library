package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.model.Entrega;

public interface EntregaDao {

	List<Entrega> readAll();

	Entrega readById(int id);

	int create(Entrega entity);

	boolean update(Entrega entity);

	boolean delete(int id);
}
