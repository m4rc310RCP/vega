package com.m4rc310.coamo.models;

import java.io.Serializable;

public class Localidade implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String codigo;
	private String codigoIBGE;
	private String codigoDistrito;
	private String codigoUF;
	private String phonetc;
	private String municipio;
	private String distrito;
	private String subdistrito;
	private String uf;
	private String xuf;
	public String getCodigo() {
		return codigo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCodigoIBGE() {
		return codigoIBGE;
	}
	public void setCodigoIBGE(String codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}
	public String getCodigoDistrito() {
		return codigoDistrito;
	}
	public void setCodigoDistrito(String codigoDistrito) {
		this.codigoDistrito = codigoDistrito;
	}
	public String getCodigoUF() {
		return codigoUF;
	}
	public void setCodigoUF(String codigoUF) {
		this.codigoUF = codigoUF;
	}
	public String getPhonetc() {
		return phonetc;
	}
	public void setPhonetc(String phonetc) {
		this.phonetc = phonetc;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public String getSubdistrito() {
		return subdistrito;
	}
	public void setSubdistrito(String subdistrito) {
		this.subdistrito = subdistrito;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getXuf() {
		return xuf;
	}
	public void setXuf(String xuf) {
		this.xuf = xuf;
	}

	
}
