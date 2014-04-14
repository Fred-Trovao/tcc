package br.ufpb.tcc.conversores;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.model.Telefone;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class PessoaConverter {
	public Map<String, Object> converterToMap(Pessoa pessoa) {
        Map<String, Object> mapPessoa = new HashMap<String, Object>();
        mapPessoa.put("_id", pessoa.getDocumento().getNumero());
        mapPessoa.put("nome", pessoa.getNome());
        mapPessoa.put("nascimento", pessoa.getNascimento());
        mapPessoa.put("documento", 
        		new DocumentoConverter().converterToMap(pessoa.getDocumento()));
                
        BasicDBList dbList = new BasicDBList();
        
        for(Telefone telefone : pessoa.getTelefones()){
        	dbList.add(new TelefoneConverter().converterToMap(telefone));	
        }
        
        mapPessoa.put("telefones", dbList);
       
        return mapPessoa;
    }
 
    public Pessoa converterToPessoa(DBObject dbo) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(dbo.get("_id").toString());
        pessoa.setNome((String) dbo.get("nome"));
        pessoa.setNascimento((Date) dbo.get("nascimento"));
        
        pessoa.setDocumento(new DocumentoConverter().converterToDocumento((HashMap<String, Object>) dbo.get("documento")));
        
        BasicDBList basicDBList = (BasicDBList) dbo.get("telefones");
        
        for (Object basicDBObject : basicDBList) {
            Telefone telefone = new TelefoneConverter().converterToTelefone((HashMap<String, Object>)basicDBObject);
            telefone.setTitular(pessoa);            
            
            pessoa.addTelefone(telefone);
        }
        
        return pessoa;
    }
}
