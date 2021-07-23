package com.m4rc310.coamo.models;

import java.io.Serializable;
import java.util.Date;

public class RG implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String numero;
	private String emissor;
	private String uf;
	private Date dataEmissao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEmissor() {
		return emissor;
	}

	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
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
