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
