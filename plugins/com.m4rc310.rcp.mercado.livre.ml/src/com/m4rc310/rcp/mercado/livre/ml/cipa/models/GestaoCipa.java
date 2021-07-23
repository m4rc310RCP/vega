package com.m4rc310.rcp.mercado.livre.ml.cipa.models;

import java.util.List;

public class GestaoCipa {
	private Long id;
	private String descricao;
	private String gestao;
	
	private List<Atribuicao> atribuicoes;

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

	public String getGestao() {
		return gestao;
	}

	public void setGestao(String gestao) {
		this.gestao = gestao;
	}

	public List<Atribuicao> getAtribuicoes() {
		return atribuicoes;
	}

	public void setAtribuicoes(List<Atribuicao> atribuicoes) {
		this.atribuicoes = atribuicoes;
	}
	
	
	
	
}
