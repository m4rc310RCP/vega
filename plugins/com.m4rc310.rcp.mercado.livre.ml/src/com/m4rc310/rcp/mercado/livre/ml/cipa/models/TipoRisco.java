package com.m4rc310.rcp.mercado.livre.ml.cipa.models;

public class TipoRisco extends ModelChangeSupport {
	private Long id;

	private int numero;
	private String descricao;
	private String grupoRisco;
	private String cor;
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getGrupoRisco() {
		return grupoRisco;
	}
	public void setGrupoRisco(String grupoRisco) {
		this.grupoRisco = grupoRisco;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	
	

}
