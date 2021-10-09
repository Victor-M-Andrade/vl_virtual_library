package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.model.Emprestimo;

public interface EmprestimoDao {

	List<Emprestimo> readAll();

	Emprestimo readById(int id);

	int create(int idExemplar);

	boolean update(Emprestimo entity);

	boolean delete(int id);

	List<Integer> checkAvaliableCopies(int livroId);

	List<Integer> checkOpenReaderLoads(int leitoId);
}
