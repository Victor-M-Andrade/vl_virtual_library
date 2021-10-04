package br.fai.vl.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.fai.vl.db.connection.ConnectionFactory;
import br.fai.vl.db.dao.EntregaDao;
import br.fai.vl.model.Entrega;

@Repository
public class EntregaDaoImpl implements EntregaDao {

	public List<Entrega> readAll() {
		final List<Entrega> entregas = new ArrayList<Entrega>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			final String sql = "select * from entrega;";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final Entrega entrega = new Entrega();
				entrega.setId(resultSet.getInt("id"));
				entrega.setDataSolicitacao(resultSet.getTimestamp("datasolicitacao"));
				entrega.setDataEntrega(resultSet.getTimestamp("dataentrega"));
				entrega.setEntregue(resultSet.getBoolean("entregue"));
				entrega.setLeitorId(resultSet.getInt("leitor_id"));
				entrega.setEmprestimoId(resultSet.getInt("emprestimo_id"));
				entregas.add(entrega);
			}

		} catch (final Exception e) {
			System.out.println("N�o foi poss�vel resgatar as entregas ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return entregas;
	}

	public Entrega readById(final int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		Entrega entrega = null;

		try {
			final String sql = "SELECT * FROM entrega WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				entrega = new Entrega();
				entrega.setId(resultSet.getInt("id"));
				entrega.setDataSolicitacao(resultSet.getTimestamp("datasolicitacao"));
				entrega.setDataEntrega(resultSet.getTimestamp("dataentrega"));
				entrega.setEntregue(resultSet.getBoolean("entregue"));
				entrega.setLeitorId(resultSet.getInt("leitor_id"));
				entrega.setEmprestimoId(resultSet.getInt("emprestimo_id"));
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return entrega;
	}

	public int create(final Entrega entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		final String sql = "INSERT INTO entrega(datasolicitacao, dataentrega, entregue, leitor_id, emprestimo_id) "
				+ "VALUES(?, ?, ?, ?, ?);";
		int id = Integer.valueOf(-1);

		try {
			connection = ConnectionFactory.getConnection();

			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setTimestamp(1, entity.getDataSolicitacao());
			preparedStatement.setTimestamp(2, entity.getDataEntrega());
			preparedStatement.setBoolean(3, entity.isEntregue());
			preparedStatement.setInt(4, entity.getLeitorId());
			preparedStatement.setInt(5, entity.getEmprestimoId());

			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}

			connection.commit();
		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final SQLException e2) {
				System.out.println(e2.getMessage());
			}

		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return id;
	}

	public boolean update(final Entrega entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		final String sql = "UPDATE entrega SET dataentrega = ?, entregue = ? WHERE id = ?;";

		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setTimestamp(1, entity.getDataEntrega());
			preparedStatement.setBoolean(2, entity.isEntregue());
			preparedStatement.setInt(3, entity.getId());

			preparedStatement.execute();
			connection.commit();

			return true;
		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final SQLException e2) {
				System.out.println(e2.getMessage());
			}

			return false;
		} finally {
			ConnectionFactory.close(preparedStatement, connection);
		}
	}

	public boolean delete(final int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		final String sql = "DELETE FROM entrega WHERE id = ?;";

		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);

			preparedStatement.execute();
			connection.commit();
			return true;

		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final SQLException e2) {
				System.out.println(e2.getMessage());
			}

			return false;
		} finally {
			ConnectionFactory.close(preparedStatement, connection);
		}
	}

}