package br.ufpb.tcc.model;


public class Telefone {
	
	private String id;
	
	private String numero;
	
	private Pessoa titular;
	private Operadora operadora;
	
	public Telefone(){
	}
	
	public Telefone(Operadora operadora){
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

	@Override
	public String toString() {
		return "Telefone [numero=" + numero + ", operadora=" + operadora + "]";
	}
}
