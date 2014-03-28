package br.ufpb.tcc.model;

import java.io.Serializable;

public class Documento implements Serializable{

	private static final long serialVersionUID = -5604567958149786910L;

	private String id;
	
	private String numero;
	private byte tipo;
		
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
	@Override
	public String toString() {
		return "Documento [numero=" + numero + ", tipo=" + tipo + "]";
	}
}
