package br.ufpb.tcc.model;

public class ValorPropriedade {

	private Integer id;
	private Propriedade propriedade;
	private Instancia instancia;
	private String valor;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Propriedade getPropriedade() {
		return propriedade;
	}
	public void setPropriedade(Propriedade propriedade) {
		this.propriedade = propriedade;
	}
	public Instancia getInstancia() {
		return instancia;
	}
	public void setInstancia(Instancia instancia) {
		this.instancia = instancia;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
}
