package br.ufpb.tcc.conversores;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBList;

import br.ufpb.tcc.model.Documento;
import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.model.Telefone;

public class OperadoraConverter {
	public Map<String, Object> converterToMap(Operadora operadora) {
        Map<String, Object> mapOperadora = new HashMap<String, Object>();
        mapOperadora.put("razaoSocial", operadora.getRazaoSocial());
        
        BasicDBList documentoDbList = new BasicDBList();
        
        for(Documento doc : operadora.getDocumentos()){
        	documentoDbList.add(new DocumentoConverter().converterToMap(doc));	
        }
        
        mapOperadora.put("documentos", documentoDbList);
 
        return mapOperadora;
    }
 
    public Operadora converterToOperadora(HashMap<String, Object> hashMap) {
        Operadora operadora = new Operadora();
        operadora.setRazaoSocial((String) hashMap.get("razaoSocial"));
        
        BasicDBList basicDBList = (BasicDBList) hashMap.get("documentos");
        
        for (Object basicDBObject : basicDBList) {
            Documento documento = new DocumentoConverter()
            	.converterToDocumento((HashMap<String, Object>)basicDBObject);
 
            operadora.addDocumento(documento);
        }
        return operadora;
    }
}
