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
import br.fai.vl.db.dao.BibliotecarioDao;
import br.fai.vl.model.Bibliotecario;

@Repository
public class BibliotecarioDaoImpl implements BibliotecarioDao {

	public List<Bibliotecario> readAll() {
		final List<Bibliotecario> bibliotecarios = new ArrayList<Bibliotecario>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conex�o
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM pessoa P inner join bibliotecario B on P.id = B.pessoa_id;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				final Bibliotecario bibliotecario = new Bibliotecario();
				bibliotecario.setId(resultSet.getInt("id"));
				bibliotecario.setCpf(resultSet.getString("cpf"));
				bibliotecario.setNome(resultSet.getString("nome"));
				bibliotecario.setRua(resultSet.getString("rua"));
				bibliotecario.setNumero(resultSet.getInt("numero"));
				bibliotecario.setBairro(resultSet.getString("bairro"));
				bibliotecario.setCidade(resultSet.getString("cidade"));
				bibliotecario.setEstado(resultSet.getString("estado"));
				bibliotecario.setTelefone(resultSet.getString("telefone"));
				bibliotecario.setRegistro(resultSet.getInt("registro"));
				bibliotecario.setEmail(resultSet.getString("email"));
				bibliotecario.setSenha(resultSet.getString("senha"));
				bibliotecario.setPessoaId(resultSet.getInt("pessoa_id"));
				bibliotecarios.add(bibliotecario);
			}

		} catch (final Exception e) {
			System.out.println("N�o foi poss�vel resgatar os Leitores ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return bibliotecarios;
	}

	public Bibliotecario readById(final int id) {
		Bibliotecario bibliotecario = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// faz a conex�o
			connection = ConnectionFactory.getConnection();
			final String sql = "SELECT * FROM  bibliotecario B left JOIN pessoa P on P.id = B.pessoa_id WHERE B.id = ?;";
			// prepara a query
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			// executa a query
			resultSet = preparedStatement.executeQuery();

			// pegando os resultados obtidos
			while (resultSet.next()) {
				bibliotecario = new Bibliotecario();
				bibliotecario.setId(resultSet.getInt("id"));
				bibliotecario.setCpf(resultSet.getString("cpf"));
				bibliotecario.setNome(resultSet.getString("nome"));
				bibliotecario.setRua(resultSet.getString("rua"));
				bibliotecario.setNumero(resultSet.getInt("numero"));
				bibliotecario.setBairro(resultSet.getString("bairro"));
				bibliotecario.setCidade(resultSet.getString("cidade"));
				bibliotecario.setEstado(resultSet.getString("estado"));
				bibliotecario.setTelefone(resultSet.getString("telefone"));
				bibliotecario.setRegistro(resultSet.getInt("registro"));
				bibliotecario.setEmail(resultSet.getString("email"));
				bibliotecario.setSenha(resultSet.getString("senha"));
				bibliotecario.setPessoaId(resultSet.getInt("pessoa_id"));
			}

		} catch (final Exception e) {
			System.out.println("N�o foi poss�vel resgatar o Leitor solicitado ou houve um erro interno no sistema");
		} finally {
			ConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return bibliotecario;
	}

	public int create(final Bibliotecario entity) {
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
				sql = "INSERT INTO bibliotecario(registro, email, senha, pessoa_id)" + "VALUES(?, ?, ?, ?);";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, entity.getRegistro());
				preparedStatement.setString(2, entity.getEmail());
				preparedStatement.setString(3, entity.getSenha());
				preparedStatement.setInt(4, id);

				preparedStatement.execute();
				result = true;
			}
			;
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

	public boolean update(final Bibliotecario entity) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// sql
		final String sql = "UPDATE bibliotecario SET email = ?, senha = ?" + " WHERE id = ?;";

		try {
			// prepara a query
			connection = ConnectionFactory.getConnection();
			// para impedir que, caso for inserir dados em mais de uma tabela, n�o seja
			// inserido lixo no BD
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, entity.getEmail());
			preparedStatement.setString(2, entity.getSenha());
			preparedStatement.setLong(3, entity.getId());

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
		final String sql = "DELETE FROM bibliotecario WHERE id = ?;";

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
