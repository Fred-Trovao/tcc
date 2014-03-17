package br.ufpb.tcc.model;

public class Documento {

	private Integer id;
	
	private String numero;
	private byte tipo;
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public byte getTipo() {
		return tipo;
	}
	public void setTipo(byte tipo) {
		this.tipo = tipo;
	}
}
