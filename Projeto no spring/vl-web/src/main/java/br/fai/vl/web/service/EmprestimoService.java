package br.fai.vl.web.service;

import java.util.List;

import br.fai.vl.model.Emprestimo;

public interface EmprestimoService {

	List<Emprestimo> readAll();

	Emprestimo readById(int id);

	int create(int idLivro, int idUser);

	boolean update(Emprestimo entity);

	boolean delete(int id);
}
