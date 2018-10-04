package br.unirio.dsw.chamadas.modelo.usuario;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um usuário do sistema
 * 
 * @author marciobarros
 */
public class Usuario
{
	private @Setter @Getter int id;
	private @Setter @Getter String nome;
	private @Setter @Getter String email;
	private @Setter @Getter String senha;
	private @Setter @Getter String tokenLogin;
	private @Setter @Getter DateTime dataTokenLogin;
	private @Setter @Getter int contadorFalhasLogin;
	private @Setter @Getter DateTime dataUltimoLogin;
	private @Setter @Getter boolean bloqueado;
	private @Setter @Getter boolean administrador;
	private @Setter @Getter String providerId;
	private @Setter @Getter String providerUserId;
	private @Setter @Getter String profileUrl;
	private @Setter @Getter String imageUrl;
	private @Setter @Getter String accessToken;
	private @Setter @Getter String secret;
	private @Setter @Getter String refreshToken;
	private @Setter @Getter long expireTime;

	/**
	 * Inicializa um usuário
	 */
	public Usuario(String nome, String email, String senha, boolean bloqueado)
	{
		this.email = email;
		this.senha = senha;
		this.id = -1;
		this.nome = nome;
		this.tokenLogin = "";
		this.dataTokenLogin = null;
		this.contadorFalhasLogin = 0;
		this.dataUltimoLogin = null;
		this.bloqueado = bloqueado;
		this.administrador = false;
		this.providerId = "";
		this.providerUserId = "";
		this.profileUrl = "";
		this.imageUrl = "";
		this.accessToken = "";
		this.secret = "";
		this.refreshToken = "";
		this.expireTime = 0;
	}
}