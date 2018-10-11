package br.unirio.dsw.chamadas.modelo.chamada;

import java.util.List;
import java.util.ArrayList;

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
	private @Setter @Getter boolean cancelada;
	private List<InscricaoCampoChamada> inscricoesCampoChamada;
	private List<ResultadoChamada> resultados;
	
	/**
	 * Inicia incricao chamada
	 */
	public InscricaoChamada(int id, DateTime dataRegistro, DateTime dataAtualizacao, 
			int idChamada, int idUsuario, DateTime dataInscricao, boolean cancelada) {
		this.id = id;
		this.dataRegistro = dataRegistro;
		this.dataAtualizacao = dataAtualizacao;
		this.idChamada = idChamada;
		this.idUsuario = idUsuario;
		this.dataInscricao = dataInscricao;
		this.cancelada = cancelada;
		this.inscricoesCampoChamada = new ArrayList<InscricaoCampoChamada>();
		this.resultados= new ArrayList<ResultadoChamada>();
	}
	
	/**
	 * Conta o número de incricoes campo chamada
	 */
	public int contaIncricoesCampoChamada()
	{
		return inscricoesCampoChamada.size();
	}
	
	/**
	 * Retorna uma incricao campos chamada, dado seu índice
	 */
	public InscricaoCampoChamada pegaIncricaoCampoChamada(int indice)
	{
		return inscricoesCampoChamada.get(indice);
	}
	
	/**
	 * Retorna todos as incricoes campo chamada
	 */
	public Iterable<InscricaoCampoChamada> pegaInscricaoCampoChamada()
	{
		return inscricoesCampoChamada;
		
	}
	
	/**
	 * Adiciona uma inscricao em campo chamada
	 */
	public void adicionaInscricaoCampoChamada(int id, String valor)
	{
		inscricoesCampoChamada.add(new InscricaoCampoChamada(id, valor));
	}

	/**
	 * Remove uma inscricao do campo chamada, dado seu ID
	 */
	public void removeInscricaoCamposChamada(int id)
	{
		inscricoesCampoChamada.removeIf(g -> g.getId() == id); 
	}
	
	/**
	 * Remove todas as inscricoes no campo chamada
	 */
	public void limpaInscricaoCampoChamada()
	{
		inscricoesCampoChamada.clear();
	}
	
	
	/**
	 * Conta o número de resultados da chamada
	 */
	public int contaResultadoChamada()
	{
		return resultados.size();
	}
	
	/**
	 * Retorna um resultado de chamada, dado seu índice
	 */
	public ResultadoChamada pegaResuldadoChamadaIndice(int indice)
	{
		return resultados.get(indice);
	}
	
	/**
	 * Retorna todos os resultados de chamada
	 */
	public Iterable<ResultadoChamada> pegaResultadoChamado()
	{
		return resultados;
	}
	
	/**
	 * Adiciona um resultado da chamada
	 */
	public void adicionaResultadaChamada(int id, String valor)
	{
		resultados.add(new ResultadoChamada(id, idChamada, valor));
	}

	/**
	 * Remove resultado da chamada, dado seu ID
	 */
	public void removeCampoChamada(int id)
	{
		resultados.removeIf(g -> g.getId() == id); 
	}
	
	/**
	 * Remove todos os resultados de chamada 
	 */
	public void limpaCampoChamada()
	{
		resultados.clear();
	}
}
