package br.unirio.dsw.chamadas.modelo.chamada;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
/**
 * Classe que representa um campo dentro de uma chamada
 * 
 * @author Mlandrini
 *
 */
public class CampoChamada {
	
	private @Getter @Setter int id;
	private @Getter @Setter String titulo;
	private @Getter @Setter int tipo;
	private @Getter @Setter int decimais;
	private @Getter @Setter boolean opcional;
	private List<String> opcoes;
	
	/**
	 * Inicializa um campo da chamada 
	 */
	public CampoChamada(int id, String titulo, int tipo, int decimais, boolean opcional) {
		this.id = id;
		this.titulo = titulo;
		this.tipo = tipo;
		this.decimais = decimais;
		this.opcional = opcional;
		this.opcoes = new ArrayList<String>();
	}
	
	
	/**
	 * Conta o número de opcoes do campo
	 */
	public int contaOpcoes()
	{
		return opcoes.size();
	}
	
	/**
	 * Retorna uma opcao, dado seu índice
	 */
	public String pegaOpcaoIndice(int indice)
	{
		return opcoes.get(indice);
	}
	
	/**
	 * Retorna todas as opcoes do campo
	 */
	public Iterable<String> pegaOpcoes()
	{
		return opcoes;
	}
	
	/**
	 * Adiciona uma opcao
	 */
	public void adicionaOpcao(String opcao)
	{
		opcoes.add(opcao);
	}
	
	/**
	 * Remove uma opcao
	 */
	public void removeOpcao(String opcao)
	{
		opcoes.removeIf(s -> s.equals(opcao));
	}
	
	/**
	 * Remove todas as opcoes
	 */
	public void limpaOpcoes()
	{
		opcoes.clear();
	}
}

