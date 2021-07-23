package com.m4rc310.rcp.mercado.livre.ml.cipa.models;

import java.util.List;

public class Setor {
	private Long id;
	private int lotacao;
	private String descricao;
	
	private List<Local> locais;
	
	private List<GrupoLocais> grupos;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getLotacao() {
		return lotacao;
	}

	public void setLotacao(int lotacao) {
		this.lotacao = lotacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Local> getLocais() {
		return locais;
	}

	public void setLocais(List<Local> locais) {
		this.locais = locais;
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
		String hash = String.format("%s: %04d", getClass().getName(), lotacao);
		return hash.hashCode();
	}
	
}
