package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.EmprestimoService;
import br.fai.vl.db.dao.EmprestimoDao;
import br.fai.vl.dto.EmprestimoDTO;
import br.fai.vl.model.Emprestimo;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

	@Autowired
	private EmprestimoDao dao;

	@Override
	public List<Emprestimo> readAll() {
		return dao.readAll();
	}

	@Override
	public Emprestimo readById(final int id) {
		return dao.readById(id);
	}

	@Override
	public int create(final int livroId, final int leitorId) {

		List<Integer> availableCopies = null;
		List<Integer> emprestimoAberto = null;

		availableCopies = dao.checkAvaliableCopies(livroId);

		if (availableCopies != null && availableCopies.size() >= 1) {

			emprestimoAberto = dao.checkOpenReaderLoads(leitorId);

			if (emprestimoAberto == null || emprestimoAberto.size() == 0) {

				System.out.println("irá criar um novo empréstimo" + availableCopies.get(0));
				return dao.create(leitorId, availableCopies.get(0));

			} else {
				System.out.println("Ainda tem que finalizar o empréstimo");
				return dao.addToLoads(emprestimoAberto.get(0), availableCopies.get(0));

			}

		} else {
			System.out.println("não pode fazer empréstimo");
			return -1;
		}
	}

	@Override
	public boolean update(final Emprestimo entity) {
		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {
		return dao.delete(id);
	}

	@Override
	public List<EmprestimoDTO> checkOpenUserLoans(final int idLeitor) {
		return dao.checkOpenUserLoans(idLeitor);
	}

	@Override
	public boolean terminateLoan(final int idEmprestimo) {

		return dao.terminateLoan(idEmprestimo);
	}

	@Override
	public boolean removeLoanBook(final EmprestimoDTO entity) {

		return dao.removeLoanBook(entity);
	}

	@Override
	public Emprestimo lastLoanRecord(final int id) {

		return dao.lastLoanRecord(id);
	}

}
