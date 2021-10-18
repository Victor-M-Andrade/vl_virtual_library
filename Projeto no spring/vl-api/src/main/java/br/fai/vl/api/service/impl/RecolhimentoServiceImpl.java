package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.RecolhimentoService;
import br.fai.vl.db.dao.RecolhimentoDao;
import br.fai.vl.model.Recolhimento;

@Service
public class RecolhimentoServiceImpl implements RecolhimentoService {

	@Autowired
	private RecolhimentoDao dao;

	@Override
	public List<Recolhimento> readAll() {

		return dao.readAll();
	}

	@Override
	public Recolhimento readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public int create(final Recolhimento entity) {

		return dao.create(entity);
	}

	@Override
	public boolean update(final Recolhimento entity) {

		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {

		return dao.delete(id);
	}

	@Override
	public String requestCollection(final int idEmprestimo, final int idLeitor) {
		final Recolhimento entrega = dao.requestCollection(idEmprestimo, idLeitor);

		if (entrega != null) {
			if (entrega.getDataRecolhimento() == null) {
				if (entrega.isRecolhido() == false) {
					return "Solicitação reprovada";
				} else {
					return "Solicitação em aprovação";
				}

			} else {
				return "Previsão de entrega: " + entrega.getDataRecolhimento();
			}
		} else {
			return "Entrega não solicitada";
		}
	}

}
