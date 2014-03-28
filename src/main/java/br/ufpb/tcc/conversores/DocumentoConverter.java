package br.ufpb.tcc.conversores;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.ufpb.tcc.model.Documento;
import br.ufpb.tcc.util.TccException;

public class DocumentoConverter {
	public Map<String, Object> converterToMap(Documento documento) {
        Map<String, Object> mapDocumento = new HashMap<String, Object>();
        mapDocumento.put("numero", documento.getNumero());
        mapDocumento.put("tipo", documento.getTipo());
 
        return mapDocumento;
    }
	 
    public Documento converterToDocumento(HashMap<String, Object> hashMap) {
        Documento documento = new Documento();
        documento.setNumero((String) hashMap.get("numero"));
        int x = (Integer) hashMap.get("tipo");
        byte tipo = (byte) x;
        documento.setTipo(tipo);
 
        return documento;
    }
    
    public Map<Integer, String> converterSetToMapCassandra(Set<Documento> documentos) {
        
    	Map<Integer, String> mapDocumento = new HashMap<Integer, String>();
        
    	for(Documento doc : documentos){
    		mapDocumento.put((int)doc.getTipo(), doc.getNumero());
    	}
         
        return mapDocumento;
    }
    
    public Set<Documento> converterToSetDocumentoCassandra(HashMap<Integer, String> hashMap) throws TccException{
    	
    	if(hashMap == null){
    		throw new TccException("Mapa nulo");
    	}
    	
    	Set<Documento> documentos = new HashSet<Documento>();
    	
    	for(Integer chave : hashMap.keySet()){
    		Documento doc = new Documento();
    		doc.setTipo(chave.byteValue());
    		doc.setNumero(hashMap.get(chave));
    		
    		documentos.add(doc);
    	}
    	
    	return documentos;
    }
    
    public static void main(String[] args){
    	Map<Integer, Integer> mapDocumento = new HashMap<Integer, Integer>();
    	
    	mapDocumento.put(1, 13);
    	mapDocumento.put(6, 15);
    	
    	for(Integer x : mapDocumento.keySet()){
    		System.out.println(x);
    	}
    	
    }
}
