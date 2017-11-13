package br.com.money.business.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.money.auth.usuario.Usuario;
import br.com.money.business.exception.CampoObrigatorioException;
import br.com.money.business.repository.UsuarioRepository;

@Service
public class UsuarioService implements UsuarioServiceInterface {
	private final Logger logger = Logger.getLogger(UsuarioService.class);

	@Autowired
	UsuarioRepository usuarioRepository;

	
	@Override
	public void incluir(Usuario usuario) {
		logger.info("Incluindo usuario ...");
		/*
		 * Pega o CEP elaborado na função buscaCep, caso o CEP não seja encontrado de
		 * primeira ira atribuir o primeiro CEP encontrado no algorítimo.
		 */
		
		String senha = usuario.getPassword();
		
		String hashedPassword = criptografar(senha);
		
		usuario.setPassword(hashedPassword);
		
		usuarioRepository.save(usuario);
	}

	private String criptografar(String senha) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(senha);
		return hashedPassword;
	}

	@Override
	public void deletar(String login) throws CampoObrigatorioException {
		logger.info("Deletando usuario...");
		Usuario usuario = usuarioRepository.findByLoginStartsWithIgnoreCase(login);
		if(usuario == null){
			logger.error("Erro ao deletar Usuario!");
			throw new CampoObrigatorioException("Usuario Nao encontrado para ser excluido!");
		}
		
		usuarioRepository.delete(usuario);
	}

	@Override
	public void atualizar(Usuario usuarioAtual) throws CampoObrigatorioException {
		logger.info("Atualizando usuario...");
		/* Pega o CEP elaborado na função buscaCep,
		 * caso o CEP não seja encontrado de primeira
		 * ira atribuir o primeiro CEP encontrado no algorítimo.
		 */
		Usuario usuario = usuarioRepository.findByLoginStartsWithIgnoreCase(usuarioAtual.getLogin());
		if(usuario == null){
			logger.error("Erro ao atualizar Usuario!");
			throw new CampoObrigatorioException("Usuario Nao encontrado para ser excluido!");
		}
		
		String hashedPassword = criptografar(usuarioAtual.getPassword());
		usuarioAtual.setPassword(hashedPassword);
		
		usuarioRepository.save(usuarioAtual);
	}

	public List<Usuario> buscarUsuarios() {
		List<Usuario> usuarios = usuarioRepository.buscarUsuarios();
		return usuarios;
	}
	
	/*
	
	public Endereco buscarCep(String cep) throws EnderecoException {
		Endereco result = new Endereco();
		int count = cep.length();
		StringBuilder cepBuilder = new StringBuilder(cep);
		String uri = "http://api.postmon.com.br/v1/cep/{cep}";
		RestTemplate restTemplate = new RestTemplate();

		while (result.getCep() == null) {
			if (count == 0) {
				throw new EnderecoException("CEP nao encontrado!");
			}
			logger.info("Buscando Cep: " + cepBuilder.toString());
			Map<String, String> params = new HashMap<String, String>();
			params.put("cep", cepBuilder.toString());
			try {
				result = restTemplate.getForObject(uri, Endereco.class, params);
			} catch (HttpClientErrorException error) {
				if (error.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
					logger.error("Cep não encontrado!");
					cepBuilder.setCharAt(--count, '0');
				}
			}
		}

		return result;
	}

	@Override
	public Endereco consultar(Long id) throws CampoObrigatorioException {
		logger.info("Consultando endereco...");
		Endereco result = repository.findById(id);
		if (result == null) {
			logger.error("Erro ao consultar Endereco!");
			throw new CampoObrigatorioException("Endereco Nao encontrado!");
		}
		return repository.findById(id);
	}



	@Override
	public void atualizar(Endereco endereco) throws CampoObrigatorioException, EnderecoException {
		logger.info("Atualizando endereco...");
		/*
		 * Pega o CEP elaborado na função buscaCep, caso o CEP não seja encontrado de
		 * primeira ira atribuir o primeiro CEP encontrado no algorítimo.
		 
		Endereco buscaCep = buscarCep(endereco.getCep());
		endereco.setCep(buscaCep.getCep());
		Endereco result = repository.findOne(endereco.getId());
		if (result == null) {
			logger.error("Erro ao atualizar endereco!");
			throw new CampoObrigatorioException("Endereco Nao encontrado para ser atualizado!");
		}
		repository.save(result);
	}

	@Override
	public void deletar(Long id) throws CampoObrigatorioException {
		logger.info("Deletando endereco...");
		Endereco result = repository.findOne(id);
		if (result == null) {
			logger.error("Erro ao deletar Endereco!");
			throw new CampoObrigatorioException("Endereco Nao encontrado para ser excluido!");
		}
		repository.delete(id);
	}

	*/
}
