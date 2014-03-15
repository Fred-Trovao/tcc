package br.ufpb.tcc.model;

import java.io.Serializable;
import java.util.Set;

public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7866458593183440474L;
	
	private Integer id;
	private String nome;
	private String cpf;
	
	private Set<Telefone> telefones;
	private Usuario usuario;
	
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
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
