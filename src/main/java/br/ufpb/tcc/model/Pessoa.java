package br.ufpb.tcc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7866458593183440474L;
	
	private String id;
	private String nome;
	private Date nascimento;
	
	private Set<Telefone> telefones;
	private Documento documento;
	
	public Pessoa(){
		this.telefones = new HashSet<Telefone>();
	}
	
	public Integer getId() {
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
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public Date getNascimento() {
		return nascimento;
	}
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
	
	public void addTelefone(Telefone telefone){
		if(telefone != null){
			telefone.setTitular(this);
			this.telefones.add(telefone);
		}
	}
	public void removeTelefone(Telefone telefone){
		if(telefone != null){
			telefone.setTitular(null);
			this.telefones.remove(telefone);
		}
	}
}
