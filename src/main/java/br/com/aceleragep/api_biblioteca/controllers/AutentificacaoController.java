package br.com.aceleragep.api_biblioteca.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.aceleragep.api_biblioteca.configs.ControllerConfig;
import br.com.aceleragep.api_biblioteca.dtos.inputs.LoginInput;
import br.com.aceleragep.api_biblioteca.dtos.outputs.TokenOutput;
import br.com.aceleragep.api_biblioteca.exceptions.BadRequestBussinessException;
import br.com.aceleragep.api_biblioteca.repositories.UsuarioRepository;
import br.com.aceleragep.api_biblioteca.services.TokenService;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "auth")
@CrossOrigin(origins = { "http://www.aceleragep.com.br", "https://www.aceleragep.com.br", "http://localhost:4200"})
public class AutentificacaoController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenOutput> autenticar(@RequestBody @Valid LoginInput login) {
		System.out.println("Entrou no login");
		UsernamePasswordAuthenticationToken dadosLogin = login.converter();
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication, login);
			
			return ResponseEntity.ok(new TokenOutput(token, "Bearer"));
		} catch (AuthenticationException ex) {
			throw new BadRequestBussinessException("Usuario ou senha inválida");
		}
	}
}
