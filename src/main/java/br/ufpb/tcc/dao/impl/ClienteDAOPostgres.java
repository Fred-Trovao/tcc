package br.ufpb.tcc.dao.impl;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.model.Telefone;
import br.ufpb.tcc.util.TccException;

public class ClienteDAOPostgres implements ClienteDAO {

	@Override
	public void save(Pessoa pessoa) throws TccException {
		
		PessoaDAOPostgres pdp = new PessoaDAOPostgres();
		
		pdp.save(pessoa);
		
		TelefoneDAOPostgres tdp = new TelefoneDAOPostgres();
		for(Telefone t : pessoa.getTelefones()){
			
			if(t.getOperadora() != null && t.getOperadora().getId() == null){
				OperadoraDAOPostgres odp = new OperadoraDAOPostgres();
				
				odp.save(t.getOperadora());
				
			}
			
			t.setTitular(pessoa);
			tdp.save(t);
		}		
	}
}
