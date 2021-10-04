package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.EntregaService;
import br.fai.vl.db.dao.EntregaDao;
import br.fai.vl.model.Entrega;

@Service
public class EntregaServiceImpl implements EntregaService {

	@Autowired
	private EntregaDao dao;

	@Override
	public List<Entrega> readAll() {

		return dao.readAll();
	}

	@Override
	public Entrega readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public int create(final Entrega entity) {

		return dao.create(entity);
	}

	@Override
	public boolean update(final Entrega entity) {

		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {

		return dao.delete(id);
	}
}
