package com.Evento.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Convidado {

	@Id
	@NotEmpty
	private String rg;
	@NotEmpty
	private String nomeConvidado;
	@ManyToOne // muitos convidados para um evento
	private Evento eventos;

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNomeConvidado() {
		return nomeConvidado;
	}

	public void setNomeConvidado(String nomeConvidado) {
		this.nomeConvidado = nomeConvidado;
	}

	public Evento getEventos() {
		return eventos;
	}

	public void setEventos(Evento eventos) {
		this.eventos = eventos;
	}

}