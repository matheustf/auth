package br.com.money.business.service;

import br.com.money.auth.usuario.Usuario;
import br.com.money.business.exception.CampoObrigatorioException;

public interface UsuarioServiceInterface {
	
	public void incluir(Usuario usuario);

	void deletar(String login) throws CampoObrigatorioException;

	void atualizar(Usuario usuario) throws CampoObrigatorioException;
}
