package com.m4rc310.financas.models;

public class Funcionario extends PessoaFisica {

	private static final long serialVersionUID = 1L;
	

//	@Transient
//	private CTPS ctps;
//	
//	
//	@Transient
//	private Matricula matricula;
	
	private Acesso acesso;


	public Acesso getAcesso() {
		return acesso;
	}


	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}
	
//	@ManyToOne
//	private Setor setor;
	
}
