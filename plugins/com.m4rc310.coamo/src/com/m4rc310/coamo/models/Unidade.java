package com.m4rc310.coamo.models;

import java.io.Serializable;
import java.util.List;

public class Unidade implements Serializable{
	
	private Long id;

	private static final long serialVersionUID = 1L;
	
	private Integer numero;
	private Integer quantidadeFuncionarios;
	private String nome;
	
	
	
	private List<Sexo> listSexo;
	private Iterable<Lotacao> lotacoes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Iterable<Lotacao> getLotacoes() {
		return lotacoes;
	}

	public void setLotacoes(Iterable<Lotacao> lotacoes) {
		this.lotacoes = lotacoes;
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

	public Integer getQuantidadeFuncionarios() {
		return quantidadeFuncionarios;
	}

	public void setQuantidadeFuncionarios(Integer quantidadeFuncionarios) {
		this.quantidadeFuncionarios = quantidadeFuncionarios;
	}

	public List<Sexo> getListSexo() {
		return listSexo;
	}

	public void setListSexo(List<Sexo> listSexo) {
		this.listSexo = listSexo;
	}

}
