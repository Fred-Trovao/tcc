package br.ufpb.tcc.model;

import java.io.Serializable;
import java.util.Set;

public class Entidade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7866458593183440474L;
	
	private Integer id;
	private String nome;
	private Set<Propriedade> propriedades; 
	
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
	public Set<Propriedade> getPropriedades() {
		return propriedades;
	}
	public void setPropriedades(Set<Propriedade> propriedades) {
		this.propriedades = propriedades;
	}

}
