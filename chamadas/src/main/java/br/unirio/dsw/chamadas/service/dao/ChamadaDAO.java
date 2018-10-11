package br.unirio.dsw.chamadas.service.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.unirio.dsw.chamadas.modelo.chamada.CampoChamada;
import br.unirio.dsw.chamadas.modelo.chamada.Chamada;
import br.unirio.dsw.chamadas.modelo.unidade.GestorUnidade;
import br.unirio.dsw.chamadas.modelo.unidade.Unidade;
import br.unirio.dsw.chamadas.ultils.DateUtils;

public class ChamadaDAO extends AbstractDAO
{	
	/**
	 * Carrega os dados de uma chamada a partir do resultado de uma consulta
	 */
	private Chamada carregaChamada(ResultSet rs) throws SQLException
	{
		Chamada chamada = new Chamada();
		chamada.setId(rs.getInt("id"));
		chamada.setDataRegistro(DateUtils.toDateTime(rs.getTimestamp("dataRegistro")));
		chamada.setDataAtualizacao(DateUtils.toDateTime(rs.getTimestamp("dataAtualizacao")));
		chamada.setIdUnidade(rs.getInt("idUnidade"));
		chamada.setNome(rs.getString("nome"));
		chamada.setSigla(rs.getString("sigla"));
		chamada.setDataAbertura(DateUtils.toDateTime(rs.getTimestamp("dataAbertura")));
		chamada.setDataEncerramento(DateUtils.toDateTime(rs.getTimestamp("dataEncerramento")));
		chamada.setCancelada(rs.getInt("cancelada") != 0);
		chamada.setEncerrada(rs.getInt("encerrada") != 0);
		return chamada;
	}
	
	/**
	 * Carrega os dados de um campo da chamada a partir de uma consulta
	 */
	private CampoChamada carregaCampo(ResultSet rs) throws SQLException
	{
		int id = rs.getInt("id");
		String titulo = rs.getString("titulo");
		int tipo = rs.getInt("tipo");
		int decimais = rs.getInt("decimais");
		boolean opcional = rs.getInt("opcional") != 0;
		// TODO tratar as opçoes
		CampoChamada campo = new CampoChamada(id, titulo, tipo, decimais, opcional);
		return campo;
	}

	/**
	 * Carrega uma chamada, dado seu identificador
	 */
	public Chamada carregaChamadaId(int id)
	{
		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Chamada WHERE id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			Chamada item = null;
			
			if (rs.next())
			{
				item = carregaChamada(rs);
				carregaCampos(c, item);
			}
			
			c.close();
			return item;

		} catch (SQLException e)
		{
			log("ChamadaDAO.carregaChamadaId: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Carrega todos os campos de uma chamada
	 */
	private void carregaCampos(Connection c, Chamada chamada) throws SQLException
	{
		PreparedStatement ps = c.prepareStatement("SELECT * FROM CampoChamada WHERE idChamada = ?");
		ps.setLong(1, chamada.getId());
		ResultSet rs = ps.executeQuery();
		
		while (rs.next())
		{
			CampoChamada campo = carregaCampo(rs);
			chamada.adicionaCampoChamada(campo);
		}
	}

	/**
	 * Conta o número de chamadas segundo um filtro
	 */
	public int conta(String filtroNome, String filtroSigla)
	{
		String SQL = "SELECT COUNT(*) " +
					 "FROM Chamada " + 
					 "WHERE nome like ? " +
					 "AND sigla like ? ";
		
		Connection c = getConnection();
		
		if (c == null)
			return 0;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, "%" + filtroNome+ "%");
			ps.setString(2, "%" + filtroSigla + "%");
			ResultSet rs = ps.executeQuery();
			int contador = rs.next() ? rs.getInt(1) : null;
			c.close();
			return contador;

		} catch (SQLException e)
		{
			log("ChamadaDAO.conta: " + e.getMessage());
			return 0;
		}
	}

	/**
	 * Retorna uma lista de chamadas segundo um filtro
	 */
	public List<Chamada> lista(int pagina, int tamanhoPagina, String filtroNome, String filtroSigla)
	{
		String SQL = "SELECT * " +
					 "FROM UnidadeFuncional " + 
					 "WHERE nome like ? " +
					 "AND sigla like ? " + 
					 "LIMIT ? OFFSET ? ";
		
		Connection c = getConnection();
		List<Chamada> chamadas = new ArrayList<Chamada>();
		
		if (c == null)
			return chamadas;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, "%" + filtroNome + "%");
			ps.setString(2, "%" + filtroSigla + "%");
			ps.setInt(3, tamanhoPagina);
			ps.setInt(4, pagina * tamanhoPagina);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				Chamada chamada = carregaChamada(rs);
				carregaCampos(c, chamada);
				chamadas.add(chamada);
			}
			
			c.close();
			return chamadas;

		} catch (SQLException e)
		{
			log("ChamadaDAO.lista: " + e.getMessage());
			return chamadas;
		}
	}

	// PAREI AQUI 10/10
	/**
	 * Adiciona uma unidade no sistema
	 */
	public boolean cria(Unidade unidade)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UnidadeFuncionalInsere(?, ?, ?)}");
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
			log("UnidadeDAO.cria: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Atualiza uma unidade no sistema
	 */
	public boolean atualiza(Unidade unidade)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UnidadeFuncionalAtualiza(?, ?, ?)}");
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
			log("UnidadeDAO.atualiza: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Remove uma unidade no sistema
	 */
	public boolean remove(int idUnidade)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UnidadeFuncionalRemove(?)}");
			cs.setInt(1, idUnidade);
			cs.execute();
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("UnidadeDAO.remove: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Carrega os gestores de uma unidade
	 */
	public boolean carregaGestores(Unidade unidade)
	{
		String SQL = "SELECT u.id, u.nome " +
					 "FROM GestorUnidadeFuncional g " +
					 "INNER JOIN Usuario u ON g.idUsuario = u.id " +
					 "WHERE g.idUnidade = ?";
		
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
			log("UnidadeDAO.carregaGestores: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Adiciona os gestores em uma unidade
	 */
	private void adicionaGestores(Connection c, Unidade unidade) throws SQLException
	{
		for (GestorUnidade gestor : unidade.pegaGestores())
			adicionaGestor(c, unidade.getId(), gestor.getId());
	}

	/**
	 * Adiciona um gestor em uma unidade
	 */
	private void adicionaGestor(Connection c, int idUnidade, int idUsuario) throws SQLException
	{
		CallableStatement cs = c.prepareCall("{call UnidadeFuncionalAssociaGestor(?, ?)}");
		cs.setInt(1, idUnidade);
		cs.setInt(2, idUsuario);
		cs.execute();
		c.close();
	}

	/**
	 * Remove todos os gestores de uma unidade
	 */
	private void removeGestores(Connection c, int idUnidade) throws SQLException
	{
		CallableStatement cs = c.prepareCall("{call UnidadeFuncionalDesassociaGestores(?)}");
		cs.setInt(1, idUnidade);
		cs.execute();
		c.close();
	}
}