package br.com.aceleragep.api_biblioteca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.aceleragep.api_biblioteca.entities.UsuarioEntity;
import br.com.aceleragep.api_biblioteca.exceptions.BadRequestBussinessException;
import br.com.aceleragep.api_biblioteca.exceptions.NotFoundBussinessException;
import br.com.aceleragep.api_biblioteca.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Page<UsuarioEntity> listarTodos(Pageable paginacao) {
		return usuarioRepository.findAll(paginacao);
	}

	public UsuarioEntity cadastrar(UsuarioEntity usuarioNovo) {

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		usuarioNovo.setSenha(bCryptPasswordEncoder.encode(usuarioNovo.getSenha()));

		return usuarioRepository.save(usuarioNovo);
	}

	public UsuarioEntity buscarPeloId(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new NotFoundBussinessException(
				String.format("O usuario de id %s não foi encontrado", usuarioId)));
	}

	public void deletar(UsuarioEntity usuarioEncontrado) {
		usuarioRepository.delete(usuarioEncontrado);
	}

	public UsuarioEntity atualizar(UsuarioEntity usuarioEncontrado) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		usuarioEncontrado.setSenha(bCryptPasswordEncoder.encode(usuarioEncontrado.getSenha()));
		return usuarioRepository.save(usuarioEncontrado);
	}
	
	public UsuarioEntity atualizarPermissoes(UsuarioEntity usuarioEncontrado) {
		return usuarioRepository.save(usuarioEncontrado);
	}

	public void jaExiste(String email) {
		if (usuarioRepository.findByEmail(email).isPresent()) {
			throw new BadRequestBussinessException("Este email já esta cadastrado, mude o email de tente novamente");
		}
	}
}
