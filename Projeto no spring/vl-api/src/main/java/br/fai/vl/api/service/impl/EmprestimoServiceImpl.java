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
	public int create(final Emprestimo entity, final int idLivro) {

		return dao.create(entity, idLivro);

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
