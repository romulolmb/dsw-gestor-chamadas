package br.unirio.dsw.chamadas.service.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import br.unirio.dsw.chamadas.modelo.chamada.CampoChamada;
import br.unirio.dsw.chamadas.modelo.chamada.InscricaoCampoChamada;
import br.unirio.dsw.chamadas.modelo.chamada.InscricaoChamada;
import br.unirio.dsw.chamadas.ultils.DateUtils;

/**
 * Classe responsável pela persistência das Inscrições nas Chamadas
 * 
 * @author Rômulo Brito
 */
public class InscricaoChamadaDAO extends AbstractDAO
{
	/**
	 * Carrega os dados de uma inscrição a partir do resultado de uma consulta
	 */
	private InscricaoChamada carrega(ResultSet rs) throws SQLException
	{
		int id = rs.getInt("id");
		DateTime dataRegistro = DateUtils.toDateTime(rs.getTimestamp("dataRegistro"));
		DateTime dataAtualizacao = DateUtils.toDateTime(rs.getTimestamp("dataAtualizacao"));
		int idChamada = rs.getInt("idChamada");
		int idUsuario = rs.getInt("idUsuario");
		DateTime dataInscricao= DateUtils.toDateTime(rs.getTimestamp("dataInscricao"));
		boolean cancelada = rs.getInt("cancelada") != 0;
		InscricaoChamada inscricaoChamada = new InscricaoChamada(id, dataRegistro, dataAtualizacao, idChamada, idUsuario, dataInscricao, cancelada);
		return inscricaoChamada;
	}
	
	/**
	 * Carrega as InscriçõesCampoChamada de um Inscrição
	 */
	private InscricaoCampoChamada carregaInscricaoCampoChamada(ResultSet rs) throws SQLException
	{
		int id = rs.getInt("id");
		int idInscricao = rs.getInt("idInscricao");
		int idCampoChamada = rs.getInt("idCampoChamada");
		String valor = rs.getString("valor");
		InscricaoCampoChamada inscricaoCampoChamada = new InscricaoCampoChamada(id, valor);
		inscricaoCampoChamada.setIdCampoChamada(idCampoChamada);
		inscricaoCampoChamada.setIdInscricao(idInscricao);
		return inscricaoCampoChamada;
	}

	/**
	 * Carrega uma incrição de chamada, dado seu identificador
	 */
	public InscricaoChamada carregaInscricaoChamadaId(int id)
	{
		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM InscricaoChamada WHERE id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			InscricaoChamada inscricao = rs.next() ? carrega(rs) : null;
			c.close();
			return inscricao;

		} catch (SQLException e)
		{
			log("InscricaoChamadaDAO.carregaInscricaoChamadaId: " + e.getMessage());
			return null;
		}
	}
	
	private void carregaIncricoesCampoChamada(Connection c, InscricaoChamada inscricaoChamada) throws SQLException
	{
		PreparedStatement ps = c.prepareStatement("SELECT * FROM InscricaoCampoChamada WHERE idInscricao = ? AND idCampoChamada = ?");
		ps.setLong(1, inscricaoChamada.getIdChamada());
		ps.setLong(1, inscricaoChamada.getIdUsuario());
		ResultSet rs = ps.executeQuery();
		
		while (rs.next())
		{
			InscricaoCampoChamada inscricaoCampoChamada = carregaInscricaoCampoChamada(rs);
			inscricaoChamada.adicionaInscricaoCampoChamada(inscricaoCampoChamada);
		}
	}

	/**
	 * Conta o número de inscrições segundo um filtro de chamada
	 */
	public int conta(int idChamada)
	{
		String SQL = "SELECT COUNT(*) " +
					 "FROM InscricaoChamada" + 
					 "WHERE idChamada = ? ";
		
		Connection c = getConnection();
		
		if (c == null)
			return 0;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setInt(1, idChamada);
			ResultSet rs = ps.executeQuery();
			int contador = rs.next() ? rs.getInt(1) : null;
			c.close();
			return contador;

		} catch (SQLException e)
		{
			log("InscricaoChamadaDAO.contaInscricaoChamadas: " + e.getMessage());
			return 0;
		}
	}

	/**
	 * Retorna uma lista de Inscrições de uma Chamada
	 */
	public List<InscricaoChamada> lista(int pagina, int tamanhoPagina, int idChamada)
	{
		String SQL = "SELECT * " +
					 "FROM InscricaoChamada " + 
					 "WHERE idChamada = ? " +
					 "LIMIT ? OFFSET ? ";
		
		Connection c = getConnection();
		List<InscricaoChamada> inscricoes = new ArrayList<InscricaoChamada>();
		
		if (c == null)
			return inscricoes;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setInt(1, idChamada);
			ps.setInt(2, tamanhoPagina);
			ps.setInt(3, pagina * tamanhoPagina);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				InscricaoChamada inscricao = carrega(rs);
				inscricoes.add(inscricao);
			}
			
			c.close();
			return inscricoes;

		} catch (SQLException e)
		{
			log("InscricaoChamadaDAO.listaInscricaoChamadas: " + e.getMessage());
			return inscricoes;
		}
	}
	
	/**
	 * Realiza uma inscrição no sistema
	 */
	public boolean cria(InscricaoChamada inscricao)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call InscricaoChamadaInsere(?, ?)}");
			cs.setInt(1, inscricao.getIdChamada());
			cs.setInt(2, inscricao.getIdUsuario());
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			
			inscricao.setId(cs.getInt(3));
			
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("InscricaoChamadaDAO.cria: " + e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * Cancela uma inscrição no sistema
	 */
	public boolean remove(int idInscricaoChamada)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call InscricaoChamadaCancela(?)}");
			cs.setInt(1, idInscricaoChamada);
			cs.execute();
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("InscricaoChamadaDAO.remove: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Insere um campo numa inscrição de chamada
	 */
	public boolean insereInscricaoCampoChamada(InscricaoCampoChamada inscricaoCampoChamada)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call InscricaoChamadaInsereValorCampo(?, ?, ?)}");
			
			cs.setInt(1, inscricaoCampoChamada.getIdInscricao());
			cs.setInt(2, inscricaoCampoChamada.getIdCampoChamada());
			cs.setString(3, inscricaoCampoChamada.getValor());
			cs.registerOutParameter(3, Types.INTEGER);			
			cs.execute();
			
			inscricaoCampoChamada.setId(cs.getInt(3));
			
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("InscricaoChamadaDAO.insereInscricaoCampoChamada: " + e.getMessage());
			return false;
		}
	}
}