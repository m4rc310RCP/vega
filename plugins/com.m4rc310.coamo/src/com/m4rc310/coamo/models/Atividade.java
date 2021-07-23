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
public class Atividade implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String descricao;
	
	private String epis;
	
	private List<EPI> listEpis;

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

	public String getEpis() {
		return epis;
	}

	public void setEpis(String epis) {
		this.epis = epis;
	}

	public List<EPI> getListEpis() {
		return listEpis;
	}

	public void setListEpis(List<EPI> listEpis) {
		this.listEpis = listEpis;
	}
	
	
	
}
