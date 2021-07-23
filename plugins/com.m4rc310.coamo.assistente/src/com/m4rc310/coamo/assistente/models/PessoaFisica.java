package com.m4rc310.coamo.assistente.models;

public class PessoaFisica extends Pessoa {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String sobrenome;
	private String apelido;
	private String phonetic;
	private String cpf;
	private String nomeCompleto;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getPhonetic() {
		return phonetic;
	}
	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getApelido() {
		return apelido;
	}
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	
	
}
