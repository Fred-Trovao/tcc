package br.ufpb.tcc.model;

import java.util.Set;

public class Propriedade {
	
	private Integer id;
	private String nome;
	private Entidade entidade;
	private Set<ValorPropriedade> propriedades;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Entidade getEntidade() {
		return entidade;
	}
	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}
	public Set<ValorPropriedade> getPropriedades() {
		return propriedades;
	}
	public void setPropriedades(Set<ValorPropriedade> propriedades) {
		this.propriedades = propriedades;
	}
	
}
