package br.ufpb.tcc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufpb.tcc.dao.OperadoraDAO;
import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.util.ConexaoPostgres;
import br.ufpb.tcc.util.TccException;

public class OperadoraDAOPostgres implements OperadoraDAO {

	public void save(Operadora entidade) throws TccException {
		if (entidade == null) {
			String mensagem = "Não foi informado a operadora a cadastrar.";
			throw new TccException(mensagem);
		}

		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "INSERT INTO operadora (razaosocial) VALUES (?)";

			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, entidade.getRazaoSocial());
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

	public void update(Operadora entidade) throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "UPDATE operadora SET razaosocial = ? WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, entidade.getRazaoSocial());
            pstm.setInt(2, entidade.getId());
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoPostgres.closeConexao(conn, pstm);
        }
	}

	public void delete(Operadora entidade) throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "DELETE FROM operadora WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, entidade.getId());
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoPostgres.closeConexao(conn, pstm);
        }

	}

	public Operadora findOne(Operadora entidade) throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		Operadora operadora = null;
		
		try {
			conn = ConexaoPostgres.getConexao();
        
			String sql = "SELECT * FROM operadora WHERE id = ?";
			            
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, entidade.getId());
            
			rs = pstm.executeQuery();
            if(rs.next()) {
                operadora = new Operadora();
                operadora.setId(rs.getInt("id"));
                operadora.setRazaoSocial(rs.getString("razaosocial"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	ConexaoPostgres.closeConexao(conn, pstm, rs);
        }
		
		return operadora;
	}

	public List<Operadora> findAll() throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Operadora> operadoras = new ArrayList<Operadora>();
		
		try {
			conn = ConexaoPostgres.getConexao();
        
			String sql = "SELECT * FROM operadora";
			Operadora operadora = null;
            
			pstm = conn.prepareStatement(sql);
            
			rs = pstm.executeQuery();
            while (rs.next()) {
                operadora = new Operadora();
                operadora.setId(rs.getInt("id"));
                operadora.setRazaoSocial(rs.getString("razaosocial"));
                 
                operadoras.add(operadora);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	ConexaoPostgres.closeConexao(conn, pstm, rs);
        }
        return operadoras;
	}

	public void deleteAll() throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "DELETE FROM operadora";
        
            pstm = conn.prepareStatement(sql);
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoPostgres.closeConexao(conn, pstm);
        }		
	}
}
