package br.ufpb.tcc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.ufpb.tcc.dao.DocumentoOperadoraDAO;
import br.ufpb.tcc.model.DocumentoOperadora;
import br.ufpb.tcc.util.ConexaoPostgres;
import br.ufpb.tcc.util.TccException;

public class DocumentoOperadoraDAOPostgres implements DocumentoOperadoraDAO {
	private Connection conn;
	
	public DocumentoOperadoraDAOPostgres(Connection conn){
		this.conn = conn;
	}
	public void save(DocumentoOperadora entidade) throws TccException {
		if (entidade == null) {
			String mensagem = "Não foi informado o documentoOperadora a cadastrar.";
			throw new TccException(mensagem);
		}

		PreparedStatement pstm = null;
		try {
			String sql = "INSERT INTO documento_operadora (id_documento, id_operadora) VALUES (?, ?)";

			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setInt(1, entidade.getDocumento().getId());
			pstm.setInt(2, entidade.getOperadora().getId());
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

	public void update(DocumentoOperadora entidade) throws TccException {
		PreparedStatement pstm = null;
		try {
			String sql = "UPDATE documento_operadora SET id_documento = ?,"
					+ " id_operadora = ? WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, entidade.getDocumento().getId());
            pstm.setInt(1, entidade.getOperadora().getId());
                        
            pstm.setInt(3, entidade.getId());
            pstm.executeUpdate();            
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
            ConexaoPostgres.closeConexao(pstm);
        }
	}

	public void delete(DocumentoOperadora entidade) throws TccException {
		PreparedStatement pstm = null;
		try {
			String sql = "DELETE FROM documento_operadora WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, entidade.getId());
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
            ConexaoPostgres.closeConexao(pstm);
        }
	}

	public DocumentoOperadora findOne(DocumentoOperadora entidade)
			throws TccException {
		return null;
	}

	public List<DocumentoOperadora> findAll() throws TccException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteAll() throws TccException {
		PreparedStatement pstm = null;
		try {
			String sql = "DELETE FROM documento_operadora";
        
            pstm = conn.prepareStatement(sql);
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
            ConexaoPostgres.closeConexao(pstm);
        }		
	}

}
