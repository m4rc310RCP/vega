package com.m4rc310.coamo.assistente.models;

import java.io.Serializable;

public class Telefone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
		
	private Integer ddd;
	
	private String numero;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDdd() {
		return ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	

}
