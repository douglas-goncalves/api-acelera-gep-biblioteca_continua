package br.com.aceleragep.api_biblioteca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.aceleragep.api_biblioteca.exceptions.BadRequestBussinessException;
import br.com.aceleragep.api_biblioteca.repositories.UsuarioRepository;

@Service															
public class AutenticacaoService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username){
		return usuarioRepository.findByEmail(username).orElseThrow(() ->
		new BadRequestBussinessException(String.format("Dados Inválidos") ));
	}
}
