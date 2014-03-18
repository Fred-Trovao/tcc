package br.ufpb.tcc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufpb.tcc.dao.TelefoneDAO;
import br.ufpb.tcc.model.Telefone;
import br.ufpb.tcc.util.ConexaoPostgres;
import br.ufpb.tcc.util.TccException;

public class TelefoneDAOPostgres implements TelefoneDAO {

	public void save(Telefone entidade) throws TccException {
		if (entidade == null) {
			String mensagem = "Não foi informado o telefone a cadastrar.";
			throw new TccException(mensagem);
		}
		
		if(entidade.getOperadora() == null){
			String mensagem = "Não foi informado o titular do telefone a cadastrar.";
			throw new TccException(mensagem);
		}
		
		if(entidade.getOperadora() == null){
			String mensagem = "Não foi informado a operadora do telefone a cadastrar.";
			throw new TccException(mensagem);
		}
		
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "INSERT INTO telefone (id_pessoa, id_operadora, numero) VALUES (?, ?, ?)";

			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setInt(1, entidade.getTitular().getId());
			pstm.setInt(2, entidade.getOperadora().getId());
			pstm.setString(3, entidade.getNumero());
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

	public void update(Telefone entidade) throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "UPDATE telefone SET id_pessoa = ?,"
					+ " id_operadora = ?, numero = ? WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, entidade.getTitular().getId());
            pstm.setInt(2, entidade.getOperadora().getId());
            pstm.setString(3, entidade.getNumero());
            
            pstm.setInt(4, entidade.getId());
            pstm.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoPostgres.closeConexao(conn, pstm);
        }
	}

	public void delete(Telefone entidade) throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "DELETE FROM telefone WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, entidade.getId());
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoPostgres.closeConexao(conn, pstm);
        }
	}

	public Telefone findOne(Telefone entidade) throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		Telefone telefone = null;
		
		try {
			conn = ConexaoPostgres.getConexao();
        
			String sql = "SELECT * FROM telefone WHERE id = ?";
			            
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, entidade.getId());
            
			rs = pstm.executeQuery();
            if(rs.next()) {
                telefone = new Telefone();
                telefone.setId(rs.getInt("id"));
                telefone.setNumero(rs.getString("numero"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	ConexaoPostgres.closeConexao(conn, pstm, rs);
        }
		
		return telefone;
	}

	public List<Telefone> findAll() throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Telefone> telefones = new ArrayList<Telefone>();
		
		try {
			conn = ConexaoPostgres.getConexao();
        
			String sql = "SELECT * FROM telefone";
			Telefone telefone = null;
            
			pstm = conn.prepareStatement(sql);
            
			rs = pstm.executeQuery();
            while (rs.next()) {
                telefone = new Telefone();
                telefone.setId(rs.getInt("id"));
                telefone.setNumero(rs.getString("numero"));
 
                telefones.add(telefone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	ConexaoPostgres.closeConexao(conn, pstm, rs);
        }
        return telefones;
	}

	public void deleteAll() throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "DELETE FROM telefone";
        
            pstm = conn.prepareStatement(sql);
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoPostgres.closeConexao(conn, pstm);
        }
	}

}
