package com.m4rc310.coamo.models;

import java.io.Serializable;

//@Getter @Setter
public class TipoLogradouro implements Serializable{
	public String getSigla() {
		return sigla;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	private static final long serialVersionUID = 1L;
	private String sigla;
	private String descricao;
}
