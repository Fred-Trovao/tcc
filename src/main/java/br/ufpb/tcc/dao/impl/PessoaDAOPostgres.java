package br.ufpb.tcc.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufpb.tcc.dao.PessoaDAO;
import br.ufpb.tcc.model.Documento;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.ConexaoPostgres;
import br.ufpb.tcc.util.TccException;

public class PessoaDAOPostgres implements PessoaDAO {
	
	private Connection conn;
	
	public PessoaDAOPostgres(Connection conn){
		this.conn = conn;
	}
		
	public void save(Pessoa entidade) throws TccException {
		
		if (entidade == null) {
			String mensagem = "Não foi informado a pessoa a cadastrar.";
			throw new TccException(mensagem);
		}
		
		if(entidade.getDocumento() == null){
			String mensagem = "Não foi informado o documento da pessoa a cadastrar.";
			throw new TccException(mensagem);
		}
		
		if(entidade.getDocumento().getId() == null){
			DocumentoDAOPostgres ddp = new DocumentoDAOPostgres(conn);
			
			Documento doc = entidade.getDocumento();
			
			ddp.save(doc);
		}
		
		PreparedStatement pstm = null;
		try {
			String sql = "INSERT INTO pessoa (id_documento, nome, nascimento) VALUES (?, ?, ?)";
        
            pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, entidade.getDocumento().getId());
            pstm.setString(2, entidade.getNome());
            pstm.setDate(3, new Date(entidade.getNascimento().getTime()));
            pstm.executeUpdate();
            
            //recuperar o ResultSet
    		ResultSet rs = pstm.getGeneratedKeys();
    		rs.next();
    		int id = rs.getInt(1);	// <= o valor do campo está aqui
            
    		entidade.setId(id);
    		
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
            ConexaoPostgres.closeConexao(pstm);
        }
	}

	public void update(Pessoa entidade) throws TccException {
		PreparedStatement pstm = null;
		try {
			String sql = "UPDATE pessoa SET id_documento = ?,"
					+ " nome = ?, nascimento = ? WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, entidade.getDocumento().getId());
            pstm.setString(2, entidade.getNome());
            pstm.setDate(3, new Date(entidade.getNascimento().getTime()));
            
            pstm.setInt(4, entidade.getId());
            pstm.executeUpdate();            
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
            ConexaoPostgres.closeConexao(pstm);
        }
	}

	public void delete(Pessoa entidade) throws TccException {
		PreparedStatement pstm = null;
		try {
			String sql = "DELETE FROM pessoa WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, entidade.getId());
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
            ConexaoPostgres.closeConexao(pstm);
        }
	}

	public Pessoa findOne(Pessoa entidade) throws TccException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		Pessoa pessoa = null;
		
		try {
        
			String sql = "SELECT * FROM pessoa WHERE id = ?";
			            
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, entidade.getId());
            
			rs = pstm.executeQuery();
            if(rs.next()) {
                pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setNascimento(rs.getDate("nascimento"));
            }
        } catch (SQLException e) {
            throw new TccException(e);
        } finally {
        	ConexaoPostgres.closeConexao(pstm, rs);
        }
		
		return pessoa;
	}

	public List<Pessoa> findAll() throws TccException {
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		try {
        
			String sql = "SELECT * FROM pessoa";
			Pessoa pessoa = null;
            
			pstm = conn.prepareStatement(sql);
            
			rs = pstm.executeQuery();
            while (rs.next()) {
                pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setNascimento(rs.getDate("nascimento"));
 
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
        	ConexaoPostgres.closeConexao(pstm, rs);
        }
        return pessoas;
	}

	public void deleteAll() throws TccException {
		PreparedStatement pstm = null;
		try {
			String sql = "DELETE FROM pessoa";
        
            pstm = conn.prepareStatement(sql);
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
            ConexaoPostgres.closeConexao(pstm);
        }		
	}

}
