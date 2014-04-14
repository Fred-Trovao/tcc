package br.ufpb.tcc.dao;

import java.util.List;

import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.TccException;

public interface ClienteDAO {
	
	public void save(Pessoa pessoa) throws TccException;
	
	public Pessoa findCliente (String documento, String telefone) throws TccException;
	
	public List<Pessoa> findTopN(String razaoSocial, int quantidade) throws TccException;
	
	public Pessoa updatePessoa(Pessoa pessoa) throws TccException;
	
	public Operadora updateOperadora(Operadora operadora) throws TccException;
	
	public Operadora findOperadora(String razaoSocial) throws TccException;
	
	public void deleteOne(String documento) throws TccException;
	
	public void deleteAllIdadeMenor(int anos) throws TccException;
}
