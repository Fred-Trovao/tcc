package br.ufpb.tcc.model;

public class DocumentoOperadora {
	
	private Integer id;
	private Documento documento;
	private Operadora operadora;
	
	public DocumentoOperadora(Documento documento, Operadora operadora){
		this.documento = documento;
		this.operadora = operadora;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
