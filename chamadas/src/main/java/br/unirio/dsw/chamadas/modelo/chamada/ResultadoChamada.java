package br.unirio.dsw.chamadas.modelo.chamada;

import lombok.Getter;
import lombok.Setter;

public class ResultadoChamada {
	private @Setter @Getter int id;
	private @Setter @Getter int idChamada;
	private @Setter @Getter String valor;
	
	public ResultadoChamada(int id, int idChamada, String valor) {
		this.id = id;
		this.idChamada = idChamada;
		this.valor = valor;
	}
}
