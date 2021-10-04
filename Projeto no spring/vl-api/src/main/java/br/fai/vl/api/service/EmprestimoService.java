package br.fai.vl.api.service;

import java.util.List;

import br.fai.vl.model.Emprestimo;

public interface EmprestimoService {

	List<Emprestimo> readAll();

	Emprestimo readById(int id);

	int create(Emprestimo entity, int idLivro);

	boolean update(Emprestimo entity);

	boolean delete(int id);
}
