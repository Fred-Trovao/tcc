package br.ufpb.tcc.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Operadora {

	private String id;
	private UUID uuid;
	
	private String razaoSocial;
		
	private Set<Documento> documentos;
	
	public Operadora(){
		this.documentos = new HashSet<Documento>();
	}
	
	public Integer getId() {
		if(this.id == null){
			return null;
		}
		return Integer.parseInt(id);
	}
	public void setId(Integer id) {
		this.id = "" + id;
	}
	
	public String getIdString() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public Set<Documento> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}
	
	public void addDocumento(Documento documento){
		this.documentos.add(documento);
	}
	
	public void removeDocumento(Documento documento){
		this.documentos.remove(documento);
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "Operadora [razaoSocial=" + razaoSocial + ", documentos="
				+ documentos + "]";
	}
}
