package com.m4rc310.financas.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private int item;
	private BigDecimal quantidade;
	
	private Produto produto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}
	
	
	
	
}
