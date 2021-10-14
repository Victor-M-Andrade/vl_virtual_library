package br.fai.vl.db.dao;

import java.util.List;
import java.util.Map;

import br.fai.vl.model.Emprestimo;

public interface EmprestimoDao {

	List<Emprestimo> readAll();

	Emprestimo readById(int id);

	int create(int idExemplar);

	boolean update(Emprestimo entity);

	boolean delete(int id);

	List<Integer> checkAvaliableCopies(int livroId);

	List<Integer> checkOpenReaderLoads(int leitoId);

	int addToLoads(int emprestimoId, int exemplarId);

	Map<Integer, Map<Integer, String>> checkOpenUserLoans(final int idLeitor);

	boolean terminateLoan(int idEmprestimo);
}
