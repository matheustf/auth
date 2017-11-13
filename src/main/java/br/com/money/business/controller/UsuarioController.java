package br.com.money.business.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.money.auth.usuario.Greeting;
import br.com.money.auth.usuario.Usuario;
import br.com.money.business.exception.CampoObrigatorioException;
import br.com.money.business.exception.EnderecoException;
import br.com.money.business.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	private final Logger logger = Logger.getLogger(UsuarioService.class);

	
	//private final Logger logger = Logger.getLogger(EnderecoService.class);

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscar() throws EnderecoException, CampoObrigatorioException{
		logger.info("Rest buscar usuarios");
		//Util.validar(endereco.getCep());
		List<Usuario> usuarios = usuarioService.buscarUsuarios();
		
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<Usuario> incluir(@RequestBody @Valid Usuario usuario, BindingResult bindingResult) throws EnderecoException, CampoObrigatorioException{
		logger.info("Rest incluir usuario");
		if(bindingResult.hasErrors()){
			throw new CampoObrigatorioException(bindingResult);
		}
		//Util.validar(endereco.getCep());
		usuarioService.incluir(usuario);
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{login}")
	@ResponseBody
	public ResponseEntity<Usuario> deletar(@RequestParam("login") String login) throws EnderecoException, CampoObrigatorioException{
		logger.info("Rest deletar usuario");
		if(login == null){
			throw new CampoObrigatorioException("Campo ID e obrigatorio para exclusao!");
		}
		usuarioService.deletar(login);
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}
	
	@PutMapping("/{login}")
	@ResponseBody
	public ResponseEntity<Usuario> atualizar(@RequestBody @Valid Usuario usuario, BindingResult bindingResult) throws EnderecoException, CampoObrigatorioException{
		logger.info("Rest atualizar usuario");
		if(bindingResult.hasErrors()){
			throw new CampoObrigatorioException(bindingResult);
		}else if(usuario.getLogin() == null){
			throw new CampoObrigatorioException("Campo ID e obrigatorio para atualizar!");
		}
		//Util.validar(endereco.getCep());
		usuarioService.atualizar(usuario);
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}
	
/*
	@RequestMapping("/usuarios")
	public String listaConvidados() {

		Iterable<Usuario> usuarios = usuarioRepository.findAll();

		return usuarios.toString();
	}

	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public ResponseEntity<?> salvar(@RequestParam("name") String name, @RequestParam("login") String login,
			@RequestParam("passward") String passward, UriComponentsBuilder ucBuilder) {

		Usuario novoUsuario = new Usuario(login, passward, name);

		usuarioRepository.save(novoUsuario);

		// Iterable<Usuario> convidados = usuarioRepository.findAll();

		// convidados.set
		
		
		
		
	        if (userService.isUserExist(user)) {
	            logger.error("Unable to create. A User with name {} already exist", user.getName());
	            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
	            user.getName() + " already exist."),HttpStatus.CONFLICT);
	        }
	    
	 
	        HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(novoUsuario.getLogin()).toUri());
	        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		
	}
	@RequestMapping(value = "/name" , produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
	public Greeting getContacts(@RequestParam(value = "name", defaultValue = "World") String name) {

		return new Greeting(name);
	}
	
	
	*/
}
