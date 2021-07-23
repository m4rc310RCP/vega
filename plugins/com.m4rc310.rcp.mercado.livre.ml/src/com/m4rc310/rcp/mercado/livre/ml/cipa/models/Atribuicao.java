package com.m4rc310.rcp.mercado.livre.ml.cipa.models;

import java.util.List;

public class Atribuicao {
	private Long id;
	private String nome;
	private String descricao;
	
	private List<Funcionario> membros;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Funcionario> getMembros() {
		return membros;
	}

	public void setMembros(List<Funcionario> membros) {
		this.membros = membros;
	}
	
	
	
	
	
}
