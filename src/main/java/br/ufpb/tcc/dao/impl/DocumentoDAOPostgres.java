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

	public void save(Documento entidade) throws TccException {
		if (entidade == null) {
			String mensagem = "Não foi informado o documento a cadastrar.";
			throw new TccException(mensagem);
		}

		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
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
			e.printStackTrace();
		} finally {
			ConexaoPostgres.closeConexao(conn, pstm);
		}
	}

	public void update(Documento entidade) throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "UPDATE documento SET numero = ?,"
					+ " tipo = ? WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, entidade.getNumero());
            pstm.setInt(2, entidade.getTipo());
            
            pstm.setInt(3, entidade.getId());
            pstm.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoPostgres.closeConexao(conn, pstm);
        }
	}

	public void delete(Documento entidade) throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "DELETE FROM documento WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, entidade.getId());
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoPostgres.closeConexao(conn, pstm);
        }
	}

	public Documento findOne(Documento entidade) throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		Documento documento = null;
		
		try {
			conn = ConexaoPostgres.getConexao();
        
			String sql = "SELECT * FROM documento WHERE id = ?";
			            
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, entidade.getId());
            
			rs = pstm.executeQuery();
            if(rs.next()) {
                documento = new Documento();
                documento.setId(rs.getInt("id"));
                documento.setNumero(rs.getString("numero"));
                documento.setTipo((byte) rs.getInt("tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	ConexaoPostgres.closeConexao(conn, pstm, rs);
        }
		
		return documento;
	}

	public List<Documento> findAll() throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Documento> documentos = new ArrayList<Documento>();
		
		try {
			conn = ConexaoPostgres.getConexao();
        
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
            e.printStackTrace();
        } finally {
        	ConexaoPostgres.closeConexao(conn, pstm, rs);
        }
        return documentos;
	}

	public void deleteAll() throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "DELETE FROM documento";
        
            pstm = conn.prepareStatement(sql);
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoPostgres.closeConexao(conn, pstm);
        }		
	}

}
