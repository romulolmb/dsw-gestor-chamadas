package br.unirio.dsw.chamadas.service.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.joda.time.DateTime;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import br.unirio.dsw.chamadas.modelo.chamada.Chamada;
import br.unirio.dsw.utils.DateUtils;
import lombok.Data;


public class ChamadaDAO extends AbstractDAO
{
	
	private Chamada carrega(ResultSet rs) throws SQLException
	{
		Chamada user  = new Chamada();
		user.setId(rs.getInt("id"));
		/*user.setDataRegistro(rs.getString("dataRegistro"));
		user.setDataAtualizacao(rs.getString("dataAtualizacao"));*/
		user.setIdUnidade(rs.getInt("idUnidade"));
		user.setNome(rs.getString("nome"));
		user.setSigla(rs.getString("sigla"));
		/*user.setDataAbertura(rs.getString("dataAbertura"));
		user.setDataEncerramento(rs.getString("dataEncerramento"));*/
		user.setCancelada(rs.getInt("cancelada"));
		user.setEncerrada(rs.getInt("encerrada"));
		return user;
	
	}
	
	public Chamada  carregaChamadaId(int id)
	{	
		Connection c = (Connection) getConnection();
		
		if(c == null) 
		{
			return null;
		}
		try {
			
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT * FROM Chamada WHERE id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			Chamada item = rs.next() ? carrega(rs) : null;
			c.close();
			return item;
			
			
		} catch (SQLException e) {
			log("ChamadaDAO.carregaChamadaId: " + e.getMessage());
			return null;
		}
		
	}
	private boolean criaChamada(Chamada chamada) 
	{
		Connection c = (Connection) getConnection();
		
		if(c == null) {
			return false;
		}
		try {
			CallableStatement cs = (CallableStatement) c.prepareCall("{call chamadaInsere(?,?,)}");
			cs.setString(1, chamada.getNome());
			cs.setString(2, chamada.getSigla());
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			chamada.setId(cs.getInt(3));
			c.close();
			return true;
			
		} catch (SQLException e) {
			log("ChamadaDAO.criaChamada: " + e.getMessage());
			return false;
		}
	}
	
	
}
