package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.EmprestimoService;
import br.fai.vl.db.dao.EmprestimoDao;
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

		List<Integer> emprestimoList = null;
		List<Integer> emprestimoAberto = null;

		emprestimoList = dao.checkAvaliableCopies(livroId);

		if (emprestimoList != null && emprestimoList.size() >= 1) {

			emprestimoAberto = dao.checkOpenReaderLoads(leitorId);
			if (emprestimoAberto == null || emprestimoAberto.size() == 0) {
				return dao.create(emprestimoList.get(0));
			} else {
				return dao.addToCart(emprestimoAberto.get(0), emprestimoList.get(0));
			}

		} else {
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

}
