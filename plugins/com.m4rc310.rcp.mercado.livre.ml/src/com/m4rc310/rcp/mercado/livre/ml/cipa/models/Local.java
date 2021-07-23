package com.m4rc310.rcp.mercado.livre.ml.cipa.models;

import java.util.List;

public class Local extends ModelChangeSupport {
	private Long id;
	private String descricao;
	private int numeroPessoas;
	private List<Risco> riscos;
	
	
	
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

	public List<Risco> getRiscos() {
		return riscos;
	}

	public void setRiscos(List<Risco> riscos) {
		this.riscos = riscos;
	}

	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}
	
	@Override
	public int hashCode() {
		return String.format("%s %05d", getClass().getName(), id).hashCode();
	}
	
}
