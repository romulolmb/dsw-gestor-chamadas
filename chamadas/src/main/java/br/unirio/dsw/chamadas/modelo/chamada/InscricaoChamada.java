package br.unirio.dsw.chamadas.modelo.chamada;

import org.joda.time.DateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author romulolmb
 *
 */
public class InscricaoChamada {
	private @Setter @Getter int id;
	private @Setter @Getter DateTime dataRegistro;
	private @Setter @Getter DateTime dataAtualizacao;
	private @Setter @Getter int idChamada;
	private @Setter @Getter int idUsuario;
	private @Setter @Getter DateTime dataInscricao;
	private @Setter @Getter int cancelada;
	
	public InscricaoChamada(int id, DateTime dataRegistro, DateTime dataAtualizacao, 
			int idChamada, int idUsuario, DateTime dataInscricao, int cancelada) {
		this.id = id;
		this.dataRegistro = dataRegistro;
		this.dataAtualizacao = dataAtualizacao;
		this.idChamada = idChamada;
		this.idUsuario = idUsuario;
		this.dataInscricao = dataInscricao;
		this.cancelada = cancelada;
	}
}
