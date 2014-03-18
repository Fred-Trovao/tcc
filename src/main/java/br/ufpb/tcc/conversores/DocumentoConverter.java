package br.ufpb.tcc.conversores;

import java.util.HashMap;
import java.util.Map;

import br.ufpb.tcc.model.Documento;

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
        documento.setTipo((Byte) hashMap.get("tipo"));
 
        return documento;
    }
}
