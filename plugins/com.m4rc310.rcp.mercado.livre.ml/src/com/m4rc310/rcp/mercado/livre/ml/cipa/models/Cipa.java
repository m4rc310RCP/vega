package com.m4rc310.rcp.mercado.livre.ml.cipa.models;

public class Cipa {
	private Long id;
	private String descricao;
	
	private GestaoCipa gestao;

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

	public GestaoCipa getGestao() {
		return gestao;
	}

	public void setGestao(GestaoCipa gestao) {
		this.gestao = gestao;
	}
	
	
}
