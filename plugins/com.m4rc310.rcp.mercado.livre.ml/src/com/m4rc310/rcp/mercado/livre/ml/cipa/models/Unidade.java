package com.m4rc310.rcp.mercado.livre.ml.cipa.models;

import java.util.List;

public class Unidade {
	private Long id;
	private int numero;
	private String nome;
	private String endereco;

	private Cipa cipa;

	private List<Setor> setores;

	private List<GrupoLocais> grupos;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Cipa getCipa() {
		return cipa;
	}

	public void setCipa(Cipa cipa) {
		this.cipa = cipa;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public List<GrupoLocais> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoLocais> grupos) {
		this.grupos = grupos;
	}

	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}

	@Override
	public int hashCode() {
		String hash = String.format("%s: %04d", getClass().getName(), numero);
		return hash.hashCode();
	}

}
