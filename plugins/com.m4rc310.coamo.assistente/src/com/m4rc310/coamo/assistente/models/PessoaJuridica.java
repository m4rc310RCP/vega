package com.m4rc310.coamo.assistente.models;

public class PessoaJuridica extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	private String cnpj;
	private String nomeFantasia;
	private String razaoSocial;
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	
}
