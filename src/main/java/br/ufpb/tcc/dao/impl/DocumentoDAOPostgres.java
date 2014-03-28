package br.ufpb.tcc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufpb.tcc.dao.DocumentoDAO;
import br.ufpb.tcc.model.Documento;
import br.ufpb.tcc.util.ConexaoPostgres;
import br.ufpb.tcc.util.TccException;

public class DocumentoDAOPostgres implements DocumentoDAO {

	private Connection conn;

	public DocumentoDAOPostgres(Connection conn) {
		this.conn = conn;
	}

	public void save(Documento entidade) throws TccException {
		if (entidade == null) {
			String mensagem = "Não foi informado o documento a cadastrar.";
			throw new TccException(mensagem);
		}

		PreparedStatement pstm = null;
		try {
			String sql = "INSERT INTO documento (numero, tipo) VALUES (?, ?)";

			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, entidade.getNumero());
			pstm.setByte(2, entidade.getTipo());
			pstm.executeUpdate();

			// recuperar o ResultSet
			ResultSet rs = pstm.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1); // <= o valor do campo está aqui

			entidade.setId(id);

		} catch (SQLException e) {
			throw new TccException(e);
		} finally {
			ConexaoPostgres.closeConexao(pstm);
		}
	}

	public void update(Documento entidade) throws TccException {
		PreparedStatement pstm = null;
		try {
			String sql = "UPDATE documento SET numero = ?,"
					+ " tipo = ? WHERE id = ?";

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, entidade.getNumero());
			pstm.setInt(2, entidade.getTipo());

			pstm.setInt(3, entidade.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new TccException(e);
		} finally {
			ConexaoPostgres.closeConexao(pstm);
		}
	}

	public void delete(Documento entidade) throws TccException {
		PreparedStatement pstm = null;
		try {
			String sql = "DELETE FROM documento WHERE id = ?";

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, entidade.getId());

			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new TccException(e);
		} finally {
			ConexaoPostgres.closeConexao(pstm);
		}
	}

	public Documento findOne(Integer id) throws TccException {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		Documento documento = null;

		try {

			String sql = "SELECT * FROM documento WHERE id = ?";

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			if (rs.next()) {
				documento = new Documento();
				documento.setId(rs.getInt("id"));
				documento.setNumero(rs.getString("numero"));
				documento.setTipo((byte) rs.getInt("tipo"));
			}
		} catch (SQLException e) {
			throw new TccException(e);
		} finally {
			ConexaoPostgres.closeConexao(pstm, rs);
		}

		return documento;
	}

	public List<Documento> findAll() throws TccException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Documento> documentos = new ArrayList<Documento>();

		try {

			String sql = "SELECT * FROM documento";
			Documento documento = null;

			pstm = conn.prepareStatement(sql);

			rs = pstm.executeQuery();
			while (rs.next()) {
				documento = new Documento();
				documento.setId(rs.getInt("id"));
				documento.setNumero(rs.getString("numero"));
				documento.setTipo((byte) rs.getInt("tipo"));

				documentos.add(documento);
			}
		} catch (SQLException e) {
			throw new TccException(e);
		} finally {
			ConexaoPostgres.closeConexao(pstm, rs);
		}
		return documentos;
	}

	public void deleteAll() throws TccException {
		PreparedStatement pstm = null;
		try {
			String sql = "DELETE FROM documento";

			pstm = conn.prepareStatement(sql);

			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new TccException(e);
		} finally {
			ConexaoPostgres.closeConexao(pstm);
		}
	}

}
