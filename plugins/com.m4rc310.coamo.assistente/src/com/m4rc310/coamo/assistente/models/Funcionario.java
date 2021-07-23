package com.m4rc310.coamo.assistente.models;

public class Funcionario extends PessoaFisica {

	private static final long serialVersionUID = 1L;
	

	private CTPS ctps;
	
	private Matricula matricula;
	
	private Setor setor;

	public CTPS getCtps() {
		return ctps;
	}

	public void setCtps(CTPS ctps) {
		this.ctps = ctps;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	
	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}
	
	@Override
	public int hashCode() {
		String hash = String.format("%s: %s", getClass().getName(), getId());
		return hash.hashCode();
	}
	
	
}
