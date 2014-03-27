package br.ufpb.tcc.model;

import java.util.UUID;

public class Documento {

	private String id;
	private UUID uuid;
	
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
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
