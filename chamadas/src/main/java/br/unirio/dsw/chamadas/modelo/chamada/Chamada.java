package br.unirio.dsw.chamadas.modelo.chamada;

import java.util.List;
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
	private @Setter @Getter String sigla;
	private @Setter @Getter DateTime dataAbertura;
	private @Setter @Getter DateTime dataEncerramento;
	private @Setter @Getter boolean cancelada; 
	private @Setter @Getter boolean encerrada;
	private List<CampoChamada> camposChamada;
	
	/**
	 *  Inicia uma chamada
	 */
	public Chamada(int id, DateTime dataRegistro, DateTime dataAtualizacao, int idUnidade, String nome, String sigla,
			DateTime dataAbertura, DateTime dataEncerramento, boolean cancelada, boolean encerrada) {
		
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
	
	public Chamada() {
		
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
	public void adicionaCampoChamada(int id, String titulo, int tipo, int decimais, boolean opcional, List<String> opcoes)
	{
		CampoChamada campo = new CampoChamada(id, titulo, tipo, decimais, opcional);
		
		for (String opcao : opcoes)
			campo.adicionaOpcao(opcao);
		
		camposChamada.add(campo);
	}

	/**
	 * Adiciona um campo na chamada
	 */
	public void adicionaCamposChamada(CampoChamada campo) 
	{
		camposChamada.add(campo);
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
