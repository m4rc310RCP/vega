/**
 * 
 */
package com.m4rc310.coamo.models;

import java.io.Serializable;

/**
 * @author marcelo
 *
 */
public class Sexo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String sigla;
	private String descricao;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
