package br.ufpb.tcc.dao;

import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.TccException;

public interface ClienteDAO {
	
	public void save(Pessoa pessoa) throws TccException;
	
}
