/**
 * 
 */
package com.m4rc310.coamo.models;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author marcelo
 *
 */
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Formacao formacao;
	private Funcionario funcionario;
	private Calendar data;
	private Calendar vencimento;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Formacao getFormacao() {
		return formacao;
	}
	public void setFormacao(Formacao formacao) {
		this.formacao = formacao;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	public Calendar getVencimento() {
		return vencimento;
	}
	public void setVencimento(Calendar vencimento) {
		this.vencimento = vencimento;
	}
	
	
}
