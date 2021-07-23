package com.m4rc310.coamo.assistente.models;

import java.io.Serializable;

public class Matricula implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
		
	private Integer dv;
	
//	@Id
	private Integer numero;
	
	private String matricula;
	
	private Funcionario funcionario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDv() {
		return dv;
	}

	public void setDv(Integer dv) {
		this.dv = dv;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	
}
