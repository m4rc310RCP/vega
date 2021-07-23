package com.m4rc310.rcp.mercado.livre.ml.cipa.models;

public class Risco extends ModelChangeSupport {
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getGrupoRisco() {
		return grupoRisco;
	}

	public void setGrupoRisco(int grupoRisco) {
		this.grupoRisco = grupoRisco;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	private Long id;

	private Local local;
	private String descricao;

	private int grupoRisco;
	private int tamanho;
	

	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}
	
	@Override
	public int hashCode() {
		return String.format("%s %05d", getClass().getName(), id).hashCode();
	}

}
