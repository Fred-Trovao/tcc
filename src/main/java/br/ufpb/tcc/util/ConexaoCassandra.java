package br.ufpb.tcc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoCassandra {
	private static final String DRIVER = "org.apache.cassandra.cql.jdbc.CassandraDriver";
	private static final String URL = "jdbc:cassandra://localhost:9170/tcc";

	public static Connection getConexao() throws TccException {
		try {
			
			Class.forName(DRIVER);
			
			return DriverManager.getConnection(URL);
		} catch (SQLException exc) {
			StringBuffer mensagem = new StringBuffer(
					"Não foi possível estabelecer conexão "
							+ "com o banco de dados.");
			mensagem.append("\nMotivo: " + exc.getMessage());
			throw new TccException(mensagem.toString());
		} catch (ClassNotFoundException exc) {
			StringBuffer mensagem = new StringBuffer("Não foi possível conectar"
					+ " com banco de dados.");
			mensagem.append("\nMotivo: " + exc.getMessage());
			throw new TccException(mensagem.toString());
		}
	}
}
