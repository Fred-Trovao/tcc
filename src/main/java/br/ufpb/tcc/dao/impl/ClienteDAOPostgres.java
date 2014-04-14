package br.ufpb.tcc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.dao.OperadoraDAO;
import br.ufpb.tcc.model.Documento;
import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.model.Telefone;
import br.ufpb.tcc.util.ConexaoPostgres;
import br.ufpb.tcc.util.TccException;

public class ClienteDAOPostgres implements ClienteDAO {

	
	private Connection conn;
	
	public ClienteDAOPostgres(Connection conn){
		this.conn = conn;
	}
	
	public void save(Pessoa pessoa) throws TccException{
		
		try {
		
			conn.setAutoCommit(false);
			
			PessoaDAOPostgres pdp = new PessoaDAOPostgres(conn);
			
			pdp.save(pessoa);
			
			TelefoneDAOPostgres tdp = new TelefoneDAOPostgres(conn);
			for(Telefone t : pessoa.getTelefones()){
				
				if(t.getOperadora() != null && t.getOperadora().getId() == null){
					OperadoraDAOPostgres odp = new OperadoraDAOPostgres(conn);
					
					odp.save(t.getOperadora());
					
				}
				
				t.setTitular(pessoa);
				tdp.save(t);
			}
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				throw new TccException(e);
			} catch (SQLException e1) {
				throw new TccException(e1);
			}
		}finally{
			ConexaoPostgres.closeConexao(conn);
		}
	}

	public Pessoa findCliente(String documento, String telefone) 
			throws TccException	 {
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		Pessoa pessoa = null;
		
		try {
        
			String sql = "SELECT d.id as d_id, d.numero as d_numero, "
					+ "tipo, p.id as p_id, id_documento, "
					+ "nome, nascimento, t.id as t_id, id_pessoa, "
					+ "id_operadora, t.numero as t_numero FROM documento d "
					+ "inner join pessoa p "
					+ "on d.id = p.id_documento "
					+ "inner join telefone t "
					+ "on p.id = t.id_pessoa "
					+ "WHERE d.numero = ? AND t.numero = ?";
			            
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, documento);
			pstm.setString(2, telefone);
            
			rs = pstm.executeQuery();
			
            if(rs.next()) {
            	Documento doc = new Documento();
            	doc.setId(rs.getInt("d_id"));
    			doc.setNumero(rs.getString("d_numero"));
    			doc.setTipo((byte) rs.getInt("tipo"));
    			
    			pessoa = new Pessoa();
    			
    			pessoa.setId(rs.getInt("p_id"));
    			pessoa.setNome(rs.getString("nome"));
    			pessoa.setNascimento(rs.getDate("nascimento"));
    			pessoa.setDocumento(doc);
    			
    			Telefone tel = new Telefone();
    			tel.setId(rs.getInt("t_id"));
    			tel.setNumero(rs.getString("t_numero"));
    			
    			pessoa.addTelefone(tel);
    			
    			OperadoraDAOPostgres odp = new OperadoraDAOPostgres(conn);
    			Operadora operadora =odp.findOneComDocumento(rs.getInt("id_operadora"));
    			
    			tel.setOperadora(operadora);
            }
            
        } catch (Exception e) {
            throw new TccException(e);
		} finally {
        	ConexaoPostgres.closeConexao(conn, pstm, rs);
        }
		return pessoa;
	}
	
	public List<Pessoa> findTopN(Operadora operadora, int quantidade) throws TccException{
		
		if(operadora == null || operadora.getId() == null){
			throw new TccException("Parametros invalidos");
		}
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		try {
        
			String sql = "SELECT d.id as d_id, d.numero as d_numero, "
					+ "tipo, p.id as p_id, id_documento, "
					+ "nome, nascimento, t.id as t_id, id_pessoa, "
					+ "id_operadora, t.numero as t_numero FROM documento d "
					+ "inner join pessoa p "
					+ "on d.id = p.id_documento "
					+ "inner join telefone t "
					+ "on p.id = t.id_pessoa "
					+ "WHERE id_operadora = ? "
					+ "ORDER BY nome "
					+ "LIMIT ?";
			            
			pstm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstm.setInt(1, operadora.getId());
			pstm.setInt(2, quantidade);
			
			rs = pstm.executeQuery();
			
			boolean proximo = rs.next();
			
            while(proximo) {
            	Documento doc = new Documento();
            	doc.setId(rs.getInt("d_id"));
    			doc.setNumero(rs.getString("d_numero"));
    			doc.setTipo((byte) rs.getInt("tipo"));
    			
    			Pessoa pessoa = new Pessoa();
    			
    			pessoa.setId(rs.getInt("p_id"));
    			pessoa.setNome(rs.getString("nome"));
    			pessoa.setNascimento(rs.getDate("nascimento"));
    			pessoa.setDocumento(doc);
    			
    			
    			do{
	    			Telefone tel = new Telefone();
	    			tel.setId(rs.getInt("t_id"));
	    			tel.setNumero(rs.getString("t_numero"));
	    			
	    			pessoa.addTelefone(tel);
	    			
	    			tel.setOperadora(operadora);
	    			
	    			if(!rs.isLast())
	    				rs.next();
	    			else{
	    				proximo = false;
	    			}
    			}while(rs.getInt("d_id") == doc.getId() && !rs.isLast());
    			
    			pessoas.add(pessoa);
            }
            
        } catch (Exception e) {
            throw new TccException(e);
		} finally {
        	ConexaoPostgres.closeConexao(conn, pstm, rs);
        }
		return pessoas;
	}
	
	public List<Pessoa> findTopN(String razaoSocial, int quantidade) throws TccException{
		OperadoraDAO opd = new OperadoraDAOPostgres(conn);
		Operadora operadora = opd.findOneComDocumento(razaoSocial);
		
		return findTopN(operadora, quantidade);
	}

	public Pessoa updatePessoa(Pessoa pessoa)
			throws TccException {
		
		if(pessoa == null || pessoa.getId() == null){
			throw new TccException("Pessoa invalida");
		}
		
		try{						
			PessoaDAOPostgres pdp = new PessoaDAOPostgres(conn);
			pdp.update(pessoa);
		}catch(TccException e){
			throw e;
        } finally {
            ConexaoPostgres.closeConexao(conn);
        }
		return pessoa;
	}

	public Operadora updateOperadora(Operadora operadora)
			throws TccException {
		
		try {
			OperadoraDAOPostgres odp = new OperadoraDAOPostgres(conn);
			odp.update(operadora);
		} catch (TccException e) {
			throw e;
		}finally{
			ConexaoPostgres.closeConexao(conn);
		}
		return operadora;
	}

	public Operadora findOperadora(String razaoSocial) throws TccException {
		Operadora operadora = null;
		try {
			OperadoraDAOPostgres odp = new OperadoraDAOPostgres(conn);
			operadora = odp.findOne(razaoSocial);
		} catch (TccException e) {
			throw e;
		}finally{
			ConexaoPostgres.closeConexao(conn);
		}
		return operadora;
	}

	public void deleteOne(String documento) throws TccException {
		try{
			DocumentoDAOPostgres ddp = new DocumentoDAOPostgres(conn);
			ddp.delete(documento);			
		}catch(TccException e){
			throw e;
		}finally{
			ConexaoPostgres.closeConexao(conn);
		}
	}

	public void deleteAllIdadeMenor(int anos) throws TccException {
		PreparedStatement pstm = null;

		try {
			String sql = "DELETE FROM documento where id IN "
					+ "(select d.id from pessoa p "
					+ "inner join documento d "
					+ "on p.id_documento = d.id "
					+ "WHERE nascimento > ?)";
			
			Calendar c = Calendar.getInstance();
			c.add(Calendar.YEAR, anos*-1);
			
			pstm = conn.prepareStatement(sql);
			pstm.setDate(1, new java.sql.Date(c.getTime().getTime()));
			pstm.executeUpdate();
			
		} catch (Exception e) {
			throw new TccException(e);
		} finally {
			ConexaoPostgres.closeConexao(conn, pstm);
		}
	}
}