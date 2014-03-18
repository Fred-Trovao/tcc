package br.ufpb.tcc.conversores;

import java.util.HashMap;
import java.util.Map;

import br.ufpb.tcc.model.Telefone;

public class TelefoneConverter {

	public Map<String, Object> converterToMap(Telefone telefone) {
        Map<String, Object> mapTelefone = new HashMap<String, Object>();
        mapTelefone.put("numero", telefone.getNumero());
        mapTelefone.put("operadora", new OperadoraConverter()
        	.converterToMap(telefone.getOperadora()));
 
        return mapTelefone;
    }
 
    public Telefone converterToTelefone(HashMap<String, Object> hashMap) {
        Telefone telefone = new Telefone();
        telefone.setNumero((String) hashMap.get("numero"));
        telefone.setOperadora(new OperadoraConverter().converterToOperadora((HashMap<String, Object>) hashMap.get("operadora")));
        
        return telefone;
    }
}
