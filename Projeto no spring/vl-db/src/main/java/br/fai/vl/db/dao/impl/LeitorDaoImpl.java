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
import br.fai.vl.db.dao.LeitorDao;
import br.fai.vl.model.Leitor;

@Repository
public class LeitorDaoImpl implements LeitorDao {

	public List<Leitor> readAll() {
		final List<Leitor> leitors = new ArrayList<Leitor>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conex�o
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM pessoa p inner join leitor l on p.id = l.pessoa_id;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				final Leitor leitor = new Leitor();
				leitor.setId(resultSet.getInt("id"));
				leitor.setCpf(resultSet.getString("cpf"));
				leitor.setNome(resultSet.getString("nome"));
				leitor.setRua(resultSet.getString("rua"));
				leitor.setNumero(resultSet.getInt("numero"));
				leitor.setBairro(resultSet.getString("bairro"));
				leitor.setCidade(resultSet.getString("cidade"));
				leitor.setEstado(resultSet.getString("estado"));
				leitor.setTelefone(resultSet.getString("telefone"));
				leitor.setMatricula(resultSet.getInt("matricula"));
				leitor.setEmail(resultSet.getString("email"));
				leitor.setSenha(resultSet.getString("senha"));
				leitor.setPessoaId(resultSet.getInt("pessoa_id"));
				leitors.add(leitor);
			}

		} catch (final Exception e) {
			System.out.println("N�o foi poss�vel resgatar os Leitores ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return leitors;
	}

	public Leitor readById(final int id) {
		Leitor leitor = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conex�o
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM leitor L INNER JOIN pessoa P on L.id = P.id WHERE L.id = ?;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				leitor = new Leitor();
				leitor.setId(resultSet.getInt("id"));
				leitor.setCpf(resultSet.getString("cpf"));
				leitor.setNome(resultSet.getString("nome"));
				leitor.setRua(resultSet.getString("rua"));
				leitor.setNumero(resultSet.getInt("numero"));
				leitor.setBairro(resultSet.getString("bairro"));
				leitor.setCidade(resultSet.getString("cidade"));
				leitor.setEstado(resultSet.getString("estado"));
				leitor.setTelefone(resultSet.getString("telefone"));
				leitor.setMatricula(resultSet.getInt("matricula"));
				leitor.setEmail(resultSet.getString("email"));
				leitor.setSenha(resultSet.getString("senha"));
				leitor.setPessoaId(resultSet.getInt("pessoa_id"));
			}

		} catch (final Exception e) {
			System.out.println("N�o foi poss�vel resgatar o Leitor solicitado ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return leitor;
	}

	public int create(final Leitor entity) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "";

		// sql
		sql = "INSERT INTO pessoa(cpf, nome, rua, numero, bairro, cidade, estado, telefone) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
		int id = Integer.valueOf(-1);

		try {
			// faz a conex�o
			connection = ConnectionFactory.getConnection();

			// para impedir que, caso for inserir dados em mais de uma tabela, n�o seja
			// inserido lixo no BD
			connection.setAutoCommit(false);
			// prepara a query
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, entity.getCpf());
			preparedStatement.setString(2, entity.getNome());
			preparedStatement.setString(3, entity.getRua());
			preparedStatement.setInt(4, entity.getNumero());
			preparedStatement.setString(5, entity.getBairro());
			preparedStatement.setString(6, entity.getCidade());
			preparedStatement.setString(7, entity.getEstado());
			preparedStatement.setString(8, entity.getTelefone());

			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
				preparedStatement.close();
				sql = "INSERT INTO leitor(matricula, email, senha, pessoa_id)" + "VALUES(?, ?, ?, ?);";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, entity.getMatricula());
				preparedStatement.setString(2, entity.getEmail());
				preparedStatement.setString(3, entity.getSenha());
				preparedStatement.setInt(4, id);

				preparedStatement.execute();
				result = true;
			}

			if (result) {
				connection.commit();
			} else {
				connection.rollback();
			}

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

	public boolean update(final Leitor entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// sql
		final String sql = "UPDATE leitor SET matricula = ?, email = ?, senha = ?" + " WHERE id = ?;";

		try {
			// prepara a query
			connection = ConnectionFactory.getConnection();
			// para impedir que, caso for inserir dados em mais de uma tabela, n�o seja
			// inserido lixo no BD
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, entity.getMatricula());
			preparedStatement.setString(2, entity.getEmail());
			preparedStatement.setString(3, entity.getSenha());
			preparedStatement.setLong(4, entity.getId());

			// executa a query
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

		// sql
		final String sql = "DELETE FROM leitor WHERE id = ?;";

		try {
			// faz a conex�o
			connection = ConnectionFactory.getConnection();
			// para impedir que, caso for inserir dados em mais de uma tabela, n�o seja
			// inserido lixo no BD
			connection.setAutoCommit(false);
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);

			// executa a query
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
