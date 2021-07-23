package com.m4rc310.rcp.mercado.livre.ml.cipa.models;

import java.util.List;

public class GrupoLocais extends ModelChangeSupport {
	private Long id;
	private String descricao;
	
	private int numeroPessoas;
	private List<Local> locais;
	
	private int numero;
	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getNumeroPessoas() {
		return numeroPessoas;
	}

	public void setNumeroPessoas(int numeroPessoas) {
		this.numeroPessoas = numeroPessoas;
	}

	public List<Local> getLocais() {
		return locais;
	}

	public void setLocais(List<Local> locais) {
		this.locais = locais;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}
	
	@Override
	public int hashCode() {
		return String.format("%s %03d", getClass().getName(), id).hashCode();
	}
	
}
