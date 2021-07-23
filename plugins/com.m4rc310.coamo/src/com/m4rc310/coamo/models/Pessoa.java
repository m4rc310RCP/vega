package com.m4rc310.coamo.models;

public class Pessoa extends MModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long id;
	public String nome;
	public String phonetic;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPhonetic() {
		return phonetic;
	}
	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}
	
	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}
	
	@Override
	public int hashCode() {
		String hash = String.format("%s: %s", getClass().getName(), id);
		return hash.hashCode();
	}
}
