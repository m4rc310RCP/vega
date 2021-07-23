package com.m4rc310.financas.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class Produto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private Long codigo;
	private String descricao;
	private BigDecimal valor;
	
	

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	
	
}
