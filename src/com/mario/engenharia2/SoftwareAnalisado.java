package com.mario.engenharia2;

import java.util.ArrayList;
import java.util.List;

public class SoftwareAnalisado {
	
	private String nome;
	private int coleta;
	private List<Metrica> metricas;
	
	public SoftwareAnalisado(String nome){
		this.nome = nome;
		this.metricas = new ArrayList<Metrica>();
	}
	
	public void addMetrica(Metrica metrica){
		metricas.add(metrica);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Metrica> getMetricas() {
		return metricas;
	}

	public void setMetricas(List<Metrica> metricas) {
		this.metricas = metricas;
	}

	public int getColeta() {
		return coleta;
	}

	public void setColeta(int coleta) {
		this.coleta = coleta;
	}

}
