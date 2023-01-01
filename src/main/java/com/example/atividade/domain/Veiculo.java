package com.example.atividade.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Veiculo implements Serializable {
	private static final long serialVersionUID = -2060602448177960220L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomeCliente;
	private String marcaModeloVeiculo;
	private int  anoModelo;
	private double valorVenda;
	private Date dataPublicacao;
	
	public Veiculo() {
		this.dataPublicacao = new Date();
	}
	
	public Veiculo(String nomeCliente, String marcaModeloVeiculo, int anoModelo, double valorVenda) {
		this.nomeCliente = nomeCliente;
		this.marcaModeloVeiculo = marcaModeloVeiculo;
		this.anoModelo = anoModelo;
		this.valorVenda = valorVenda;
		this.dataPublicacao = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}
	
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
	public String getMarcaModeloVeiculo() {
		return marcaModeloVeiculo;
	}
	
	public void setMarcaModeloVeiculo(String marcaModeloVeiculo) {
		this.marcaModeloVeiculo = marcaModeloVeiculo;
	}
	
	public int getAnoModelo() {
		return anoModelo;
	}
	
	public void setAnoModelo(int anoModelo) {
		this.anoModelo = anoModelo;
	}
	
	public double getValorVenda() {
		return valorVenda;
	}
	
	public void setValorVenda(double valorVenda) {
		this.valorVenda = valorVenda;
	}
	
	public Date getDataPublicacao() {
		return dataPublicacao;
	}
	
	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
}
