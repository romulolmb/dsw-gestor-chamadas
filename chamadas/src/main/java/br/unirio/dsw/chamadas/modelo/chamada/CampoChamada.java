package br.unirio.dsw.chamadas.modelo.chamada;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;

public class CampoChamada {
	
	private @Getter @Setter int id;
	private @Getter @Setter int idChamada;
	private @Getter @Setter String titulo;
	private @Getter @Setter int tipo;
	private @Getter @Setter int decimais;
	private @Getter @Setter int opcional;
	private @Getter @Setter jsonOpcoes TEXT;
	
	
	public CampoChamada(int id, int idChamada, String titulo, int tipo, int decimais, int opcional, jsonOpcoes tEXT) {
		this.id = id;
		this.idChamada = idChamada;
		this.titulo = titulo;
		this.tipo = tipo;
		this.decimais = decimais;
		this.opcional = opcional;
		TEXT = tEXT;
	}
	
	
	
	

}
