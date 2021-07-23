package com.m4rc310.coamo.models;

import java.io.Serializable;

public class Funcionario extends MModel implements Serializable{
	
	private Long id;

	private static final long serialVersionUID = 1L;
	
	private String matricula;
	
	private PessoaFisica dados;
	
	private VinculoEmprego vinculo;
	
	private boolean isAdmin;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public PessoaFisica getDados() {
		return dados;
	}

	public void setDados(PessoaFisica dados) {
		this.dados = dados;
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			return hashCode() == obj.hashCode();
		} catch (Exception e) {
			return false;
		}
		
	}
	
	@Override
	public int hashCode() {
		String hash = String.format("%s: %04d", getClass().getName(), id);
		return hash.hashCode();
	}

	public VinculoEmprego getVinculo() {
		return vinculo;
	}

	public void setVinculo(VinculoEmprego vinculo) {
		this.vinculo = vinculo;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
