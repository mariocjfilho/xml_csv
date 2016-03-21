package com.mario.engenharia2;

public class Metrica {
	
	private String id;
	private String descricao;
	private String tipo;
	private Double valorMedio;
	private Double max;
	private Double stddev;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getValorMedio() {
		return valorMedio;
	}

	public void setValorMedio(Double valorMedio) {
		this.valorMedio = valorMedio;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getStddev() {
		return stddev;
	}

	public void setStddev(Double stddev) {
		this.stddev = stddev;
	}

	public Metrica(String id, String descricao, String tipo, Double valorMedio, Double max, Double stddev) {
		this.id = id;
		this.descricao = descricao;
		this.tipo = tipo;
		this.valorMedio = valorMedio;
		this.max = max;
		this.stddev = stddev;
	}


}
