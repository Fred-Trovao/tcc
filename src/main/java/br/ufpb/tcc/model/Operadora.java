package br.ufpb.tcc.model;

import java.util.HashSet;
import java.util.Set;

public class Operadora {

	private String id;
	
	private String razaoSocial;
	
	private Set<Telefone> telefones;
	
	private Set<Documento> documentos;
	
	public Operadora(){
		this.documentos = new HashSet<Documento>();
		this.telefones = new HashSet<Telefone>();
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
	public Set<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(Set<Telefone> telefones) {
		this.telefones = telefones;
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
}
