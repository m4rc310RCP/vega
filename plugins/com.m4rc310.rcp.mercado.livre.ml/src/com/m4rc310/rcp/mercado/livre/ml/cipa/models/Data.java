package com.m4rc310.rcp.mercado.livre.ml.cipa.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data extends ModelChangeSupport {

	@JsonProperty("unidade")
	private Unidade unidade;
	
	@JsonProperty("removerGrupoLocal")
	private List<Unidade> unidadeResp;
	
	@JsonProperty("grupoLocal")
	private List<Unidade> unidadeGrupoLocal;
	
	@JsonProperty("addLocal")
	private List<Unidade> addLocal;
	
	@JsonProperty("risco")
	private List<Unidade> unidadesFromRisco;
	
	@JsonProperty("removerRisco")
	private List<Unidade> removerRisco;

	@JsonProperty("removerLocal")
	private List<Unidade> removerLocal;
	
	@JsonProperty("unidades")
	private List<Unidade> unidades;
	
	@JsonProperty("setores")
	private List<Setor> setores;
	
	@JsonProperty("tiposRisco")
	private List<TipoRisco> tiposRisco;
	
	@JsonProperty("gruposLocais")
	private List<GrupoLocais> gruposLocais;
	
	@JsonProperty("grupoLocalFromId")
	private GrupoLocais grupoLocal;
	
	@JsonProperty("grupoLocalFromNumero")
	private GrupoLocais grupoLocalFromNUmero;
	
	@JsonProperty("locais")
	private List<Local> locais;

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public List<Unidade> getUnidadeResp() {
		return unidadeResp;
	}

	public void setUnidadeResp(List<Unidade> unidadeResp) {
		this.unidadeResp = unidadeResp;
	}

	public List<Unidade> getUnidadeGrupoLocal() {
		return unidadeGrupoLocal;
	}

	public void setUnidadeGrupoLocal(List<Unidade> unidadeGrupoLocal) {
		this.unidadeGrupoLocal = unidadeGrupoLocal;
	}

	public List<Unidade> getAddLocal() {
		return addLocal;
	}

	public void setAddLocal(List<Unidade> addLocal) {
		this.addLocal = addLocal;
	}

	public List<Unidade> getUnidadesFromRisco() {
		return unidadesFromRisco;
	}

	public void setUnidadesFromRisco(List<Unidade> unidadesFromRisco) {
		this.unidadesFromRisco = unidadesFromRisco;
	}

	public List<Unidade> getRemoverRisco() {
		return removerRisco;
	}

	public void setRemoverRisco(List<Unidade> removerRisco) {
		this.removerRisco = removerRisco;
	}

	public List<Unidade> getRemoverLocal() {
		return removerLocal;
	}

	public void setRemoverLocal(List<Unidade> removerLocal) {
		this.removerLocal = removerLocal;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public List<TipoRisco> getTiposRisco() {
		return tiposRisco;
	}

	public void setTiposRisco(List<TipoRisco> tiposRisco) {
		this.tiposRisco = tiposRisco;
	}

	public List<GrupoLocais> getGruposLocais() {
		return gruposLocais;
	}

	public void setGruposLocais(List<GrupoLocais> gruposLocais) {
		this.gruposLocais = gruposLocais;
	}

	public GrupoLocais getGrupoLocal() {
		return grupoLocal;
	}

	public void setGrupoLocal(GrupoLocais grupoLocal) {
		this.grupoLocal = grupoLocal;
	}

	public GrupoLocais getGrupoLocalFromNUmero() {
		return grupoLocalFromNUmero;
	}

	public void setGrupoLocalFromNUmero(GrupoLocais grupoLocalFromNUmero) {
		this.grupoLocalFromNUmero = grupoLocalFromNUmero;
	}

	public List<Local> getLocais() {
		return locais;
	}

	public void setLocais(List<Local> locais) {
		this.locais = locais;
	}
	
	
	
}
