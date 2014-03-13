package br.ufpb.tcc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexaoPostgres{
	
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/tcc";
	private static final String USUARIO = "postgres";
	private static final String SENHA = "oex9536";

	public static Connection getConexao() throws TccException {
		try {
			
			Class.forName(DRIVER);
			
			return DriverManager.getConnection(URL, USUARIO, SENHA);
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

	public static void closeConexao(Connection con) throws TccException {
		closeConexao(con, null, null);
	}

	public static void closeConexao(Connection con, PreparedStatement stmt)
			throws TccException {
		closeConexao(con, stmt, null);
	}

	public static void closeConexao(Connection con, PreparedStatement stmt,
			ResultSet rs) throws TccException {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException exc) {
			StringBuffer mensagem = new StringBuffer(
					"Não foi possível finalizar a conexão com banco de dados.");
			mensagem.append("\nMotivo: " + exc.getMessage());
			throw new TccException(mensagem.toString());
		}
	}
}