package com.m4rc310.coamo.models;

import java.io.Serializable;
import java.util.Calendar;

public class CNH implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String categoria;
	private String numero;
	private Calendar validade;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Calendar getValidade() {
		return validade;
	}
	public void setValidade(Calendar validade) {
		this.validade = validade;
	}
	
}
