package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.model.Artigo;

public interface ArtigoDao {

	List<Artigo> readAll();

	Artigo readById(int id);

	int create(Artigo entity);

	boolean update(Artigo entity);

	boolean delete(int id);
}
