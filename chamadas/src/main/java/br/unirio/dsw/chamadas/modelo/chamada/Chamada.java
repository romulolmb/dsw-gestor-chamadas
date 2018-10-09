package br.unirio.dsw.chamadas.modelo.chamada;

import java.util.List;
import java.util.ArrayList;

import org.joda.time.DateTime;

import br.unirio.dsw.chamadas.modelo.unidade.GestorUnidade;
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
	
	/**
	 *  Inicia uma chamada
	 */
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
	
	/**
	 * Conta o número de campos de chamada 
	 */
	public int contaCamposChamada()
	{
		return camposChamada.size();
	}
	
	/**
	 * Retorna um campo da chamada, dado seu índice
	 */
	public CampoChamada pegaCampoChamdaIndice(int indice)
	{
		return camposChamada.get(indice);
	}
	
	/**
	 * Retorna todos os gestores da unidade
	 */
	public Iterable<CampoChamada> pegaCamposChamada()
	{
		return camposChamada;
	}
	
	/**
	 * Adiciona um campo na chamada
	 */
	public void adicionaCampoChamada(int id, String titulo, int tipo, int decimais, int opcional, String jsonOpcoes)
	{
		camposChamada.add(new CampoChamada(id, titulo, tipo, decimais, opcional, jsonOpcoes));
	}

	/**
	 * Remove um campo, dado seu ID
	 */
	public void removeCampoChamada(int id)
	{
		camposChamada.removeIf(g -> g.getId() == id); 
	}
	
	/**
	 * Remove todos os campos da chamada
	 */
	public void limpaCampoChamada()
	{
		camposChamada.clear();
	}
	

	
	
	

}
