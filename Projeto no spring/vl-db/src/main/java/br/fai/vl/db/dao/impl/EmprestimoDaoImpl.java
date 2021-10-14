package br.fai.vl.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import br.fai.vl.db.connection.ConnectionFactory;
import br.fai.vl.db.dao.EmprestimoDao;
import br.fai.vl.model.Emprestimo;

@Repository
public class EmprestimoDaoImpl implements EmprestimoDao {

	public List<Emprestimo> readAll() {
		final List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM emprestimo;";

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				final Emprestimo emprestimo = new Emprestimo();
				emprestimo.setId(resultSet.getInt("id"));
				emprestimo.setCodigo(resultSet.getInt("codigo"));
				emprestimo.setDataRealizacao(resultSet.getTimestamp("datarealizacao"));
				emprestimo.setLeitorId(resultSet.getInt("leitor_id"));
				emprestimos.add(emprestimo);
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return emprestimos;
	}

	public Emprestimo readById(final int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Emprestimo emprestimo = null;

		try {
			final String sql = "SELECT * FROM emprestimo WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				emprestimo = new Emprestimo();
				emprestimo.setId(resultSet.getInt("id"));
				emprestimo.setCodigo(resultSet.getInt("codigo"));
				emprestimo.setDataRealizacao(resultSet.getTimestamp("datarealizacao"));
				emprestimo.setLeitorId(resultSet.getInt("leitor_id"));
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return emprestimo;
	}

	public int create(final int idLeitor) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = Integer.valueOf(-1);
		String sql = "";

		try {
			sql = "insert into emprestimo(codigo, datarealizacao, leitor_id) " + " values (default, null, ?)";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idLeitor);

			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
				preparedStatement.close();

				sql = "insert into emprestimo_exemplar(datadevolucao, devolvido, dataefetivadevolucao,"
						+ "multa, emprestimo_id, exemplar_id) " + "values(null, default, null, default, ?, ?);";

				preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, id);
				preparedStatement.setInt(2, idLeitor);

				preparedStatement.execute();
				resultSet = preparedStatement.getGeneratedKeys();

				if (!resultSet.next()) {
					id = -1;
				}
			}

			if (id != -1) {
				connection.commit();
			} else {
				connection.rollback();
			}

		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final Exception e2) {
				System.out.println(e2.getMessage());
			}
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return id;
	}

	public boolean update(final Emprestimo entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			String sql = "UPDATE emprestimo SET datarealizacao = ? WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setTimestamp(1, entity.getDataRealizacao());
			preparedStatement.setInt(2, entity.getId());

			preparedStatement.execute();
			if (preparedStatement.getUpdateCount() != -1) {

				preparedStatement.close();
				sql = "UPDATE emprestimo_exemplar SET datadevolucao = ?, devolvido = ?, dataefetivadevolucao = ?, "
						+ "multa = ? WHERE emprestimo_id = ?";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setTimestamp(1, entity.getDataRealizacao());
				preparedStatement.setBoolean(2, entity.isDevolvido());
				preparedStatement.setTimestamp(3, entity.getDataEfetivaDevolucao());
				preparedStatement.setBigDecimal(4, entity.getMulta());
				preparedStatement.setInt(5, entity.getId());

				preparedStatement.execute();
				if (preparedStatement.getUpdateCount() != -1) {
					connection.commit();
				} else {
					connection.rollback();
				}
			}

			return true;

		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final Exception e2) {
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
		ResultSet resultSet = null;
		boolean sucess = false;

		try {
			final String sql = "select deleteBorrow(?) as sucess;";

			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				sucess = resultSet.getBoolean("sucess");
			}

		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final SQLException e2) {
				System.out.println(e2.getMessage());
			}

		} finally {
			ConnectionFactory.close(preparedStatement, connection);
		}

		return sucess;

	}

	public List<Integer> checkAvaliableCopies(final int livroId) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		List<Integer> exemplaresDisponiveis = null;

		try {
			final String sql = "select Ex.id from livro L join exemplar Ex on Ex.livro_id = L.id "
					+ "where Ex.id not in (select distinct EE.exemplar_id from emprestimo E "
					+ "join emprestimo_exemplar EE on EE.emprestimo_id = E.id "
					+ "where EE.devolvido = false ) and L.id = ?;";

			connection = ConnectionFactory.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, livroId);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				exemplaresDisponiveis = Arrays.asList(resultSet.getInt("id"));
			}
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, prepareStatement, connection);
		}

		return exemplaresDisponiveis;
	}

	public List<Integer> checkOpenReaderLoads(final int leitoId) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		List<Integer> emprestimosAbertos = null;

		try {
			final String sql = "select E.* from emprestimo E where E.datarealizacao is null and E.leitor_id = ?;";

			connection = ConnectionFactory.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, leitoId);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				emprestimosAbertos = Arrays.asList(resultSet.getInt("id"));
			}
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, prepareStatement, connection);
		}

		return emprestimosAbertos;
	}

	public int addToLoads(final int emprestimoId, final int exemplarId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int id = Integer.valueOf(-1);
		String sql = "";

		try {
			sql = "insert into emprestimo_exemplar(datadevolucao, devolvido, dataefetivadevolucao,"
					+ "multa, emprestimo_id, exemplar_id) " + "values(null, default, null, default, ?, ?);";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, emprestimoId);
			preparedStatement.setInt(2, exemplarId);

			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}

			if (id != -1) {
				connection.commit();
			} else {
				connection.rollback();
			}

		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final Exception e2) {
				System.out.println(e2.getMessage());
			}
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return id;
	}

	public Map<Integer, Map<Integer, String>> checkOpenUserLoans(final int idLeitor) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		final Map<Integer, Map<Integer, String>> emprestimosAbertos = new HashMap<Integer, Map<Integer, String>>();

		try {
			final String sql = "select E.id as Emprestimo_id, EE.id as Exemplar_id, L.titulo from emprestimo E "
					+ "inner join emprestimo_Exemplar EE on E.id = EE.emprestimo_id "
					+ "inner join Exemplar Ex on Ex.id = EE.exemplar_id " + "inner join Livro L on L.id = Ex.livro_id "
					+ "where E.datarealizacao is null and E.leitor_id = ?;";

			connection = ConnectionFactory.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, idLeitor);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				final Map<Integer, String> exemplarLivro = new HashMap<Integer, String>();
				exemplarLivro.put(resultSet.getInt("Exemplar_id"), resultSet.getString("titulo"));
				emprestimosAbertos.put(resultSet.getInt("Emprestimo_id"), exemplarLivro);
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet, prepareStatement, connection);
		}

		return emprestimosAbertos;
	}

	public boolean terminateLoan(final int idEmprestimo) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			String sql = "UPDATE emprestimo SET datarealizacao = now() WHERE id = ?;";

			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idEmprestimo);

			preparedStatement.execute();
			if (preparedStatement.getUpdateCount() != -1) {

				preparedStatement.close();
				sql = "UPDATE emprestimo_exemplar SET datadevolucao = default WHERE emprestimo_id = ?";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, idEmprestimo);

				preparedStatement.execute();
				if (preparedStatement.getUpdateCount() != -1) {
					connection.commit();
				} else {
					connection.rollback();
				}
			}

			return true;

		} catch (final Exception e) {
			try {
				connection.rollback();
			} catch (final Exception e2) {
				System.out.println(e2.getMessage());
			}

			return false;

		} finally {
			ConnectionFactory.close(preparedStatement, connection);
		}
	}
}
