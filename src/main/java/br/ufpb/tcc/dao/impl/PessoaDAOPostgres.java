package br.ufpb.tcc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.ufpb.tcc.dao.PessoaDAO;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.ConexaoPostgres;
import br.ufpb.tcc.util.TccException;

public class PessoaDAOPostgres implements PessoaDAO {

	public void save(Pessoa entidade) throws TccException {
		
		if (entidade == null) {
			String mensagem = "Não foi informado a pessoa a cadastrar.";
			throw new TccException(mensagem);
		}
		
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "INSERT INTO pessoa (id_usuario, nome, cpf) VALUES (?, ?, ?)";
        
            pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, entidade.getUsuario().getId());
            pstm.setString(2, entidade.getNome());
            pstm.setString(3, entidade.getCpf());
            pstm.executeUpdate();
            
            //recuperar o ResultSet
    		ResultSet rs = pstm.getGeneratedKeys();
    		rs.next();
    		int id = rs.getInt(1);	// <= o valor do campo está aqui
            
    		entidade.setId(id);
    		
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoPostgres.closeConexao(conn, pstm);
        }
	}

	public void update(Pessoa entidade) throws TccException {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "UPDATE pessoa SET id_usuario = ?,"
					+ " nome = ?, cpf = ? WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, entidade.getUsuario().getId());
            pstm.setString(2, entidade.getNome());
            pstm.setString(3, entidade.getCpf());
            
            pstm.setInt(4, entidade.getId());
            pstm.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoPostgres.closeConexao(conn, pstm);
        }
	}

	public void delete(Pessoa entity) {
		// TODO Auto-generated method stub

	}

	public Pessoa findOne(Pessoa entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Pessoa> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Pessoa> findKeyValue(Pessoa entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
