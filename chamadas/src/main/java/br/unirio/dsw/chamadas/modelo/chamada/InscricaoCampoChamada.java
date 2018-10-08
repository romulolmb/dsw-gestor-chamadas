package br.unirio.dsw.chamadas.modelo.chamada;

import lombok.Getter;
import lombok.Setter;

public class InscricaoCampoChamada {
	private @Setter @Getter int id;
	private @Setter @Getter int idInscricao;
	private @Setter @Getter int idCampoChamada;
	private @Setter @Getter String valor;
	
	public InscricaoCampoChamada(int id, int idInscricao, int idCampoChamada, String valor) {
		this.id = id;
		this.idInscricao = idInscricao;
		this.idCampoChamada = idCampoChamada;
		this.valor = valor;
	}
}
