package br.fai.vl.web.service;

import java.util.List;

import br.fai.vl.model.Recolhimento;

public interface RecolhimentoService {

	List<Recolhimento> readAll();

	Recolhimento readById(int id);

	int create(Recolhimento entity);

	boolean update(Recolhimento entity);

	boolean delete(int id);
}