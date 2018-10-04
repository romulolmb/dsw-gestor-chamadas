package br.unirio.dsw.chamadas.modelo.unidade;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um gestor em uma unidade
 * 
 * @author Marcio
 */
public class GestorUnidade
{
	private @Getter @Setter int id;
	private @Getter @Setter String nome;

	public GestorUnidade(int id, String nome)
	{
		this.id = id;
		this.nome = nome;
	}
}