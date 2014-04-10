package br.ufpb.tcc.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.ufpb.tcc.conversores.PessoaConverter;
import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.ConexaoMongo;
import br.ufpb.tcc.util.TccException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ClienteDAOMongoDB implements ClienteDAO {
	
    private DBCollection dbCollection;
 
    public ClienteDAOMongoDB() {
        
        this.dbCollection =
                ConexaoMongo.getInstance()
                    .getDB().getCollection("cliente");
    }
	
	public void save(Pessoa pessoa) throws TccException {
		
		Map<String, Object> mapPessoa =
                new PessoaConverter().converterToMap(pessoa);
		
		BasicDBObject document = new BasicDBObject(mapPessoa);
		 
        dbCollection.save(document);
	}

	public Pessoa findCliente(String documento, String telefone)
			throws TccException {
		
		BasicDBObject search = new BasicDBObject();
		search.put("telefones.numero", telefone);
		search.put("documento.numero", documento);
		
		DBCursor cursor = this.dbCollection.find(search);
		
		Pessoa pessoa = null;
		
		while(cursor.hasNext()){
			pessoa = new PessoaConverter().converterToPessoa(cursor.next());
		}
		
		return pessoa;
	}

	@Override
	public List<Pessoa> findTopN(String razaoSocial, int quantidade) throws TccException {
		BasicDBObject search = new BasicDBObject();
		search.put("telefones.operadora.razaoSocial", razaoSocial);
		
		BasicDBObject orderBy = new BasicDBObject("nome", 1);
		
		DBCursor cursor = this.dbCollection.find(search).sort(orderBy).limit(quantidade);
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		Pessoa pessoa = null;
		
		while(cursor.hasNext()){
			pessoa = new PessoaConverter().converterToPessoa(cursor.next());
			pessoas.add(pessoa);
		}
		
		return pessoas;
	}

	public Pessoa updatePessoa(Pessoa pessoa) throws TccException {
		save(pessoa);
		return pessoa;
	}

}
