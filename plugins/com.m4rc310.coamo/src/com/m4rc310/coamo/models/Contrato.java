package com.m4rc310.coamo.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Contrato implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private VinculoEmprego vinculo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VinculoEmprego getVinculo() {
		return vinculo;
	}

	public void setVinculo(VinculoEmprego vinculo) {
		this.vinculo = vinculo;
	}

	public Calendar getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getLocalContrato() {
		return localContrato;
	}

	public void setLocalContrato(String localContrato) {
		this.localContrato = localContrato;
	}

	public String getParagrafoEmpregador() {
		return paragrafoEmpregador;
	}

	public void setParagrafoEmpregador(String paragrafoEmpregador) {
		this.paragrafoEmpregador = paragrafoEmpregador;
	}

	public String getParagrafoEmpregado() {
		return paragrafoEmpregado;
	}

	public void setParagrafoEmpregado(String paragrafoEmpregado) {
		this.paragrafoEmpregado = paragrafoEmpregado;
	}

	public String getParagrafoClausulas() {
		return paragrafoClausulas;
	}

	public void setParagrafoClausulas(String paragrafoClausulas) {
		this.paragrafoClausulas = paragrafoClausulas;
	}

	public List<String> getClausulas() {
		return clausulas;
	}

	public void setClausulas(List<String> clausulas) {
		this.clausulas = clausulas;
	}

	public String getFinalizacao() {
		return finalizacao;
	}

	public void setFinalizacao(String finalizacao) {
		this.finalizacao = finalizacao;
	}

	private Calendar dataInicio;
	
	private String localContrato;
	
	private String paragrafoEmpregador;
	private String paragrafoEmpregado;
	
	private String paragrafoClausulas;

	private List<String> clausulas; 
	
	private String finalizacao;
	
}
