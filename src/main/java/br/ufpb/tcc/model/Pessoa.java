package br.ufpb.tcc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7866458593183440474L;
	
	private Integer id;
	private String nome;
	private Date nascimento;
	
	private Set<Telefone> telefones;
	private Endereco endereco;
	
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
	public Set<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(Set<Telefone> telefones) {
		this.telefones = telefones;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Date getNascimento() {
		return nascimento;
	}
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
}
