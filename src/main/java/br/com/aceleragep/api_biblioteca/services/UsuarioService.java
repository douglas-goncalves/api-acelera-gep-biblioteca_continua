package br.com.aceleragep.api_biblioteca.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.aceleragep.api_biblioteca.dtos.inputs.UsuarioAlterarSenhaInput;
import br.com.aceleragep.api_biblioteca.entities.PermissaoEntity;
import br.com.aceleragep.api_biblioteca.entities.RedefenirSenhaEntity;
import br.com.aceleragep.api_biblioteca.entities.UsuarioEntity;
import br.com.aceleragep.api_biblioteca.exceptions.BadRequestBussinessException;
import br.com.aceleragep.api_biblioteca.exceptions.BusinessException;
import br.com.aceleragep.api_biblioteca.exceptions.NotFoundBussinessException;
import br.com.aceleragep.api_biblioteca.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	private String senhaPadrao="aceleragep";
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EmailService emailService;

	//PAGE DE TODOS OS USUARIOS
	public Page<UsuarioEntity> listarTodos(Pageable paginacao) {
		return usuarioRepository.findAll(paginacao);
	}

	//CADASTRAR
	public UsuarioEntity cadastrar(UsuarioEntity usuarioNovo) {

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		usuarioNovo.setSenha(bCryptPasswordEncoder.encode(usuarioNovo.getSenha()));

		return usuarioRepository.save(usuarioNovo);
	}

	//BUSCA PELO ID
	public UsuarioEntity buscarPeloId(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new NotFoundBussinessException(
				String.format("O usuario de id %s não foi encontrado", usuarioId)));
	}

	//DELETAR
	public void deletar(UsuarioEntity usuarioEncontrado) {
		usuarioRepository.delete(usuarioEncontrado);
	}

	//ATUALIZAR
	public UsuarioEntity atualizar(UsuarioEntity usuarioEncontrado) {
		return usuarioRepository.save(usuarioEncontrado);
	}
	
	//REDEFINIR SENHA
	public UsuarioEntity redefinirSenha(UsuarioEntity usuarioEncontrado, UsuarioAlterarSenhaInput usuarioSenhaInput) {
		usuarioEncontrado.setSenha(usuarioSenhaInput.getNovaSenha());
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		usuarioEncontrado.setSenha(bCryptPasswordEncoder.encode(usuarioEncontrado.getSenha()));
		return usuarioRepository.save(usuarioEncontrado);
	}
	
	//ATUALIZAR PERMISSOES DO USUARIO
	public UsuarioEntity atualizarPermissoes(UsuarioEntity usuarioEncontrado) {
		List<PermissaoEntity> permissoes = new ArrayList<>();
		usuarioEncontrado.setPermissoes(permissoes);
		this.cadastrar(usuarioEncontrado);
		return usuarioRepository.save(usuarioEncontrado);
	}

	//USUARIO JA ESTA CADASTRADO
	public void jaExiste(String email) {
		if (usuarioRepository.findByEmail(email).isPresent()) {
			throw new BadRequestBussinessException("Este email já esta cadastrado, mude o email de tente novamente");
		}
	}

	//LISTAR TODOS OS USUARIOS
	public List<UsuarioEntity> listarTodos() {
		return usuarioRepository.findAll();
	}

	//BUSCA USUARIO PELO EMAIL
	public UsuarioEntity buscarPeloEmail(String email) {
		return usuarioRepository.findByEmail(email).orElseThrow(() -> new NotFoundBussinessException("Email Não Cadastrado"));
	}

	//RECUPERAR SENHA
	public void recuperarSenha(UsuarioEntity usuarioEncontrado) {
		RedefenirSenhaEntity redefenirSenhaEntity = new RedefenirSenhaEntity();
		redefenirSenhaEntity.setUsuario(usuarioEncontrado);
		redefenirSenhaEntity.setHash(UUID.randomUUID().toString());
		emailService.sendEmail(redefenirSenhaEntity);
	}

	//COMPARA NOVASENHA COM CONFIRMARSENHA
	public void compararSenhas(@Valid UsuarioAlterarSenhaInput usuarioSenhaInput) {	
		
		if(usuarioSenhaInput.getNovaSenha().equals(senhaPadrao)){
			throw new BusinessException("Esta senha não pode ser usada para redefinição");
		}		
		if(!usuarioSenhaInput.getNovaSenha().equals(usuarioSenhaInput.getConfirmarSenha())) {
			throw new BusinessException("Os campos senha e confimar senha devem ser iguais");
		}
	}
}
