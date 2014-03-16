package br.ufpb.tcc.model;


public class Telefone {
	
	private Integer id;
	
	private String numero;
	
	private Pessoa titular;
	private Operadora operadora;
	
	public Telefone(){
	}
	
	public Telefone(Pessoa titular, Operadora operadora){
		this.titular = titular;
		this.operadora = operadora;
	}
	
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
	public Pessoa getTitular() {
		return titular;
	}
	public void setTitular(Pessoa titular) {
		this.titular = titular;
	}
	public Operadora getOperadora() {
		return operadora;
	}
	public void setOperadora(Operadora operadora) {
		this.operadora = operadora;
	}
}
