package com.m4rc310.coamo.models;

import java.util.Calendar;


public class PessoaFisica extends Pessoa {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String cpf;
	private Calendar nascimento;
	private Boolean ativo;
	private boolean funcionario;
	private boolean bloqueado;
	private Sexo sexo;
	
	private RG rg;
	private CNH cnh;
	private CTPS ctps;
	

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	


	public boolean isFuncionario() {
		return funcionario;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public Calendar getNascimento() {
		return nascimento;
	}

	public void setNascimento(Calendar nascimento) {
		this.nascimento = nascimento;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public RG getRg() {
		return rg;
	}

	public void setRg(RG rg) {
		this.rg = rg;
	}

	public CNH getCnh() {
		return cnh;
	}

	public void setCnh(CNH cnh) {
		this.cnh = cnh;
	}

	public CTPS getCtps() {
		return ctps;
	}

	public void setCtps(CTPS ctps) {
		this.ctps = ctps;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
}
