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

import br.unirio.dsw.chamadas.modelo.chamada.InscricaoChamada;

/**
 * Classe responsavel pela persistencia de unidades funcionais
 * 
 * @author Marcio Barros
 */
public class InscricaoChamadaDAO extends AbstractDAO
{
	/**
	 * Carrega os dados de uma unidade a partir do resultado de uma consulta
	 */
	private InscricaoChamada carrega(ResultSet rs) throws SQLException
	{
		int id = rs.getInt("id");
		DateTime dataRegistro = rs.getDate("dataRegistro");
		int id = rs.getInt("dataAtualizacao");
		int id = rs.getInt("idChamada");
		int id = rs.getInt("idUsuario");
		int id = rs.getInt("dataInscricao");
		int id = rs.getInt("cancelada");
		InscricaoChamada inscricaoChamada = new InscricaoChamada();
		return inscricaoChamada;
	}

	/**
	 * Carrega uma unidade, dado seu identificador
	 */
	public InscricaoChamada carregaInscricaoChamadaId(int id)
	{
		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM InscricaoChamadaFuncional WHERE id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			InscricaoChamada item = rs.next() ? carrega(rs) : null;
			c.close();
			return item;

		} catch (SQLException e)
		{
			log("InscricaoChamadaDAO.carregaInscricaoChamadaId: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Conta o número de unidades segundo um filtro
	 */
	public int conta(String sigla, String nome)
	{
		String SQL = "SELECT COUNT(*) " +
					 "FROM InscricaoChamadaFuncional " + 
					 "WHERE sigla like ? " +
					 "AND nome like ? ";
		
		Connection c = getConnection();
		
		if (c == null)
			return 0;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, "%" + sigla + "%");
			ps.setString(2, "%" + nome + "%");
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
	 * Retorna uma lista de unidades segundo um filtro
	 */
	public List<InscricaoChamada> lista(int pagina, int tamanhoPagina, String sigla, String nome)
	{
		String SQL = "SELECT * " +
					 "FROM InscricaoChamadaFuncional " + 
					 "WHERE sigla like ? " +
					 "AND nome like ? " + 
					 "LIMIT ? OFFSET ? ";
		
		Connection c = getConnection();
		List<InscricaoChamada> unidades = new ArrayList<InscricaoChamada>();
		
		if (c == null)
			return unidades;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, "%" + sigla + "%");
			ps.setString(2, "%" + nome + "%");
			ps.setInt(3, tamanhoPagina);
			ps.setInt(4, pagina * tamanhoPagina);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				InscricaoChamada unidade = carrega(rs);
				unidades.add(unidade);
			}
			
			c.close();
			return unidades;

		} catch (SQLException e)
		{
			log("InscricaoChamadaDAO.listaInscricaoChamadas: " + e.getMessage());
			return unidades;
		}
	}
	
	/**
	 * Adiciona uma unidade no sistema
	 */
	public boolean cria(InscricaoChamada unidade)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call InscricaoChamadaFuncionalInsere(?, ?, ?)}");
			cs.setString(1, unidade.getNome());
			cs.setString(2, unidade.getSigla());
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			
			adicionaGestores(c, unidade);
			unidade.setId(cs.getInt(3));
			
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("InscricaoChamadaDAO.cria: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Atualiza uma unidade no sistema
	 */
	public boolean atualiza(InscricaoChamada unidade)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call InscricaoChamadaFuncionalAtualiza(?, ?, ?)}");
			cs.setInt(1, unidade.getId());
			cs.setString(2, unidade.getNome());
			cs.setString(3, unidade.getSigla());
			cs.execute();
			
			removeGestores(c, unidade.getId());
			adicionaGestores(c, unidade);

			c.close();
			return true;

		} catch (SQLException e)
		{
			log("InscricaoChamadaDAO.atualiza: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Remove uma unidade no sistema
	 */
	public boolean remove(int idInscricaoChamada)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call InscricaoChamadaFuncionalRemove(?)}");
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
	 * Carrega os gestores de uma unidade
	 */
	public boolean carregaGestores(InscricaoChamada unidade)
	{
		String SQL = "SELECT u.id, u.nome " +
					 "FROM GestorInscricaoChamadaFuncional g " +
					 "INNER JOIN Usuario u ON g.idUsuario = u.id " +
					 "WHERE g.idInscricaoChamada = ?";
		
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setInt(1, unidade.getId());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt(1);
				String nome = rs.getString(2);
				unidade.adicionaGestor(id, nome);
			}
			
			c.close();
			return true;
	
		} catch (SQLException e)
		{
			log("InscricaoChamadaDAO.carregaGestores: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Adiciona os gestores em uma unidade
	 */
	private void adicionaGestores(Connection c, InscricaoChamada unidade) throws SQLException
	{
		for (GestorInscricaoChamada gestor : unidade.pegaGestores())
			adicionaGestor(c, unidade.getId(), gestor.getId());
	}

	/**
	 * Adiciona um gestor em uma unidade
	 */
	private void adicionaGestor(Connection c, int idInscricaoChamada, int idUsuario) throws SQLException
	{
		CallableStatement cs = c.prepareCall("{call InscricaoChamadaFuncionalAssociaGestor(?, ?)}");
		cs.setInt(1, idInscricaoChamada);
		cs.setInt(2, idUsuario);
		cs.execute();
		c.close();
	}

	/**
	 * Remove todos os gestores de uma unidade
	 */
	private void removeGestores(Connection c, int idInscricaoChamada) throws SQLException
	{
		CallableStatement cs = c.prepareCall("{call InscricaoChamadaFuncionalDesassociaGestores(?)}");
		cs.setInt(1, idInscricaoChamada);
		cs.execute();
		c.close();
	}
}