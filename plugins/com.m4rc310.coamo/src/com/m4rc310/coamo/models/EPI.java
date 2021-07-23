/**
 * 
 */
package com.m4rc310.coamo.models;

import java.io.Serializable;
import java.util.List;

/**
 * @author marcelo
 *
 */
public class EPI implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String descricao;
	
	private String resumo;
	
	private String ca;
	
	private EPI generico;
	
	private List<EPI> especificos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCa() {
		return ca;
	}

	public void setCa(String ca) {
		this.ca = ca;
	}

	public EPI getGenerico() {
		return generico;
	}

	public void setGenerico(EPI generico) {
		this.generico = generico;
	}

	public List<EPI> getEspecificos() {
		return especificos;
	}

	public void setEspecificos(List<EPI> especificos) {
		this.especificos = especificos;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	
	
	
	
}
