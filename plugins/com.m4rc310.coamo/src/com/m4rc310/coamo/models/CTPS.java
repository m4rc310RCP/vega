package com.m4rc310.coamo.models;

import java.io.Serializable;
import java.util.Calendar;

public class CTPS implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private Boolean digital;
	private String numero;
	private String serie;
	private String uf;
	private Calendar dataCadastro;
	private Calendar dataExpedicao;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getDigital() {
		return digital;
	}
	public void setDigital(Boolean digital) {
		this.digital = digital;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public Calendar getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Calendar getDataExpedicao() {
		return dataExpedicao;
	}
	public void setDataExpedicao(Calendar dataExpedicao) {
		this.dataExpedicao = dataExpedicao;
	}
	
	
}
