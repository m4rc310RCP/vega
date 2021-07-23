package com.m4rc310.coamo.models;

import java.io.Serializable;
import java.util.List;


public class Lotacao extends MModel implements Serializable{
	
	private Long id;

	private static final long serialVersionUID = 1L;
	
	private Integer numero;
	private String nome;
	
	private Unidade unidade;
	
	private List<Funcionario> funcionarios;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}
	
	@Override
	public int hashCode() {
		String hash = String.format("%s: %04d", getClass().getName(), id);
		return hash.hashCode();
	}

}
