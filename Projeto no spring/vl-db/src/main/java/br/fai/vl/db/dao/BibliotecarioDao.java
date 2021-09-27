package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.model.Bibliotecario;

public interface BibliotecarioDao {

	List<Bibliotecario> readAll();
	
	Bibliotecario readById(int id);
	
	int create(Bibliotecario entity);
	
	boolean update(Bibliotecario entity);
	
	boolean delete(int id);
}
