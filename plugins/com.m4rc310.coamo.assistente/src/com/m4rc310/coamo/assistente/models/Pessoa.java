package com.m4rc310.coamo.assistente.models;

import java.io.Serializable;
import java.util.List;

public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	
	private List<Telefone> telefone;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public List<Telefone> getTelefone() {
		return telefone;
	}


	public void setTelefone(List<Telefone> telefone) {
		this.telefone = telefone;
	}
	
	
	
}
