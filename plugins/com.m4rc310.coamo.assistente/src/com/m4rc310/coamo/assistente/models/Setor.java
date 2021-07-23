package com.m4rc310.coamo.assistente.models;

import java.io.Serializable;
import java.util.List;

public class Setor implements Serializable{
	private static final long serialVersionUID = 1L;
	
private Long id;
	
	private Integer lotacao;
	private String nome;
	private List<Funcionario> funcionarios;
	private long numeroFuncionarios;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getLotacao() {
		return lotacao;
	}
	public void setLotacao(Integer lotacao) {
		this.lotacao = lotacao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
		String hash = String.format("%s: %s", getClass().getName(), id);
		return hash.hashCode();
	}
	public long getNumeroFuncionarios() {
		return numeroFuncionarios;
	}
	public void setNumeroFuncionarios(long numeroFuncionarios) {
		this.numeroFuncionarios = numeroFuncionarios;
	}
}
