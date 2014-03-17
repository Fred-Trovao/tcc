package br.ufpb.tcc.conversores;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.ufpb.tcc.model.Pessoa;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class PessoaConverter {
	public Map<String, Object> converterToMap(Pessoa pessoa) {
        Map<String, Object> mapPessoa = new HashMap<String, Object>();
        mapPessoa.put("nome", pessoa.getNome());
        mapPessoa.put("nascimento", pessoa.getNascimento());
        mapPessoa.put("documento", pessoa.getDocumento());
  //      mapPessoa.put("telefones", )
        
 
        return mapPessoa;
    }
 
    public Pessoa converterToPessoa(DBObject dbo) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(Integer.parseInt(dbo.get("_id").toString()));
        pessoa.setNome((String) dbo.get("firstName"));
        pessoa.setNascimento(new Date(Date.parse((String) dbo.get("lastName"))));
//        pessoa.setDocumento(((Documento) dbo.get("Documento"));
       
        return pessoa;
    }
}
