package br.ufpb.tcc.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import br.ufpb.tcc.dao.ClienteDAO;
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
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new TccException(e);
			} catch (SQLException e1) {
				throw new TccException(e);
			}
		} catch (TccException e) {
			try {
				conn.rollback();
				throw new TccException(e);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new TccException(e);
			}			// TODO Auto-generated catch block
		}finally{
			ConexaoPostgres.closeConexao(conn);
		}
	}
}