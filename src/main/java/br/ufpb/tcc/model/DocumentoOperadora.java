package br.ufpb.tcc.model;

import java.io.Serializable;

public class DocumentoOperadora implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7806848995424683214L;
	private String id;
	private Documento documento;
	private Operadora operadora;
	
	public DocumentoOperadora(Documento documento, Operadora operadora){
		this.documento = documento;
		this.operadora = operadora;
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
	
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public Operadora getOperadora() {
		return operadora;
	}
	public void setOperadora(Operadora operadora) {
		this.operadora = operadora;
	}
	
}
