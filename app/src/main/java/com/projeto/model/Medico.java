package com.projeto.model;

import java.io.Serializable;
import java.util.LinkedList;

public class Medico implements Serializable{
  
	private String user;
	private String password;
	private String nome;
	private String especialidade;
	private int QtdPacientesPorHora;
	private String agendaManha;
	private String agendaTarde;
	private LocalizacaoMedicos localizacao;
	private LinkedList<Agendamento> agendamentos;
	private String crm;
	

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public int getQtdPacientesPorHora() {
		return QtdPacientesPorHora;
	}

	public void setQtdPacientesPorHora(int qtdPacientesPorHora) {
		QtdPacientesPorHora = qtdPacientesPorHora;
	}

	public LocalizacaoMedicos getLocalizacao() {
		return localizacao;
	} 

	public void setLocalizacao(LocalizacaoMedicos localizacao) {
		this.localizacao = localizacao;
	}

	public LinkedList<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(LinkedList<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	public String getAgendaManha() {
		return agendaManha;
	}

	public void setAgendaManha(String agendaManha) {
		this.agendaManha = agendaManha;
	}

	public String getAgendaTarde() {
		return agendaTarde;
	}

	public void setAgendaTarde(String agendaTarde) {
		this.agendaTarde = agendaTarde;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	} 
	
	
	
	
	
	
	
	

}
