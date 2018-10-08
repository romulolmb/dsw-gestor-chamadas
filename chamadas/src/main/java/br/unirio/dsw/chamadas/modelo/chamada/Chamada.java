package br.unirio.dsw.chamadas.modelo.chamada;

import java.awt.List;
import java.util.ArrayList;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;

public class Chamada {
	
	private @Setter @Getter int id;
	private @Setter @Getter DateTime dataRegistro; 
	private @Setter @Getter DateTime dataAtualizacao;
	private @Setter @Getter int idUnidade;
	private @Setter @Getter String nome; 
	private @Setter @Getter String sigla ;
	private @Setter @Getter DateTime dataAbertura;
	private @Setter @Getter DateTime dataEncerramento;
	private @Setter @Getter int cancelada; 
	private @Setter @Getter int encerrada;
	private List<CampoChamada> camposChamada;
	
	
	public Chamada(int id, DateTime dataRegistro, DateTime dataAtualizacao, int idUnidade, String nome, String sigla,
			DateTime dataAbertura, DateTime dataEncerramento, int cancelada, int encerrada) {
		
		this.id = id;
		this.dataRegistro = dataRegistro;
		this.dataAtualizacao = dataAtualizacao;
		this.idUnidade = idUnidade;
		this.nome = nome;
		this.sigla = sigla;
		this.dataAbertura = dataAbertura;
		this.dataEncerramento = dataEncerramento;
		this.cancelada = cancelada;
		this.encerrada = encerrada;
		this.camposChamada = new ArrayList<CampoChamada>();
	}
	

	
	
	

}
