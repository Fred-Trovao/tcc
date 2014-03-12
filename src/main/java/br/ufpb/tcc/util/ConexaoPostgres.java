package br.ufpb.tcc.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoPostgres{
	
	private static String driver;
	private static String url;
	private static String usuario;
	private static String senha;

	public static Connection getConexao() throws TccException {
		try {
			init();

			return DriverManager.getConnection(url, usuario, senha);
		} catch (SQLException exc) {
			StringBuffer mensagem = new StringBuffer(
					"Não foi possível estabelecer conexão "
							+ "com o banco de dados.");
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
	
	public static void init() throws TccException {
		Properties properties = new Properties();
		FileInputStream arquivoDePropriedades = null;
		try {
			arquivoDePropriedades = new FileInputStream("databases.properties");
			properties.load(arquivoDePropriedades);
			driver = properties.getProperty("jdbc.postgres.driver");
			url = properties.getProperty("jdbc.postgres.url");
			usuario = properties.getProperty("jdbc.postgres.username");
			senha = properties.getProperty("jdbc.postgres.password");
			Class.forName(driver);
		} catch (FileNotFoundException exc) {
			StringBuffer mensagem = new StringBuffer(
					"Não foi possível conectar com banco de dados.");
			mensagem.append("\nMotivo: " + exc.getMessage());
			throw new TccException(mensagem.toString());
		} catch (IOException exc) {
			StringBuffer mensagem = new StringBuffer(
					"Não foi possível conectar com banco de dados.");
			mensagem.append("\nMotivo: " + exc.getMessage());
			throw new TccException(mensagem.toString());
		} catch (ClassNotFoundException exc) {
			StringBuffer mensagem = new StringBuffer(
					"Não foi possível conectar com banco de dados.");
			mensagem.append("\nMotivo: " + exc.getMessage());
			throw new TccException(mensagem.toString());
		}
	}
}