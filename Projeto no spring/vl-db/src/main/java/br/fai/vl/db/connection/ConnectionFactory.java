package br.fai.vl.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Est� classe � respons�vel por realizar a abrir e fechar as conex�es
 * realizadas ao Bancos de Dados
 *
 * @author LucasLima
 *
 */
public class ConnectionFactory {

//	private static final String url = "jdbc:postgresql://localhost:5433/virtualLibrary";
	private static final String url = "jdbc:postgresql://localhost:5432/virtualLibrary";
	private static final String userName = "postgres";
	private static final String password = "postgres";

	private static Connection connection = null;

	/**
	 * este m�todo � respons�vel por abrir a conex�o com o Banco de Dados
	 *
	 * @return connection
	 */
	public static Connection getConnection() {
		try {
			connection = DriverManager.getConnection(url, userName, password);

		} catch (final SQLException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}

	/**
	 * este m�todo � respons�vel por fechar a conex�o com o Banco de Dados
	 *
	 * @return
	 */
	private static void closeConnection() {
		try {
			connection.close();
		} catch (final SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * este m�todo � respons�vel por fechar o ResultSet toda vez que for utilizado
	 *
	 * @return
	 */
	private static void closeResultSet(final ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (final Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * este m�todo � respons�vel por fechar o PreparedStatement toda vez que for
	 * utilizado
	 *
	 * @return
	 */
	private static void closePreparedStatement(final PreparedStatement preparedStatement) {
		try {
			preparedStatement.close();
		} catch (final Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * este m�todo � respons�vel por fechar as conex�es feitas ao Banco de Dados e
	 * que utilizarem do: ResultSet, PreparedStatement e Connection. Isso permitir�
	 * mais facildade na hora de fechar a conex�o com o BD
	 *
	 * @return
	 */
	public static void close(final ResultSet resultSet, final PreparedStatement preparedStatement,
			final Connection connection) {
		closeConnection();
		closePreparedStatement(preparedStatement);
		closeResultSet(resultSet);
	}

	/**
	 * este m�todo � respons�vel por fechar as conex�es feitas ao Banco de Dados e
	 * que utilizarem do: PreparedStatement e Connection. Isso permitir� mais
	 * facildade na hora de fechar a conex�o com o BD
	 *
	 * @return
	 */
	public static void close(final PreparedStatement preparedStatement, final Connection connection) {
		closeConnection();
		closePreparedStatement(preparedStatement);
	}
}
