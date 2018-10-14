package br.unirio.dsw.chamadas.modelo.chamada;

import lombok.Getter;
import lombok.Setter;


/**
 * Classe que representa o valor de um campo numa inscrição de chamada
 * @author Rômulo Brito
 *
 */
public class InscricaoCampoChamada 
{
	private @Setter @Getter int id;
	private @Setter @Getter int idInscricao;
	private @Setter @Getter int idCampoChamada;
	private @Setter @Getter String valor;
	
	public InscricaoCampoChamada(int id, String valor) {
		this.id = id;
		this.valor = valor;
	}
	
	public InscricaoCampoChamada() 
	{
		
	}
	
	
}
