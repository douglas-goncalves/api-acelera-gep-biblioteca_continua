package br.com.aceleragep.api_biblioteca.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aceleragep.api_biblioteca.entities.RedefenirSenhaEntity;
import br.com.aceleragep.api_biblioteca.entities.UsuarioEntity;
import br.com.aceleragep.api_biblioteca.exceptions.BusinessException;
import br.com.aceleragep.api_biblioteca.exceptions.NotFoundBussinessException;
import br.com.aceleragep.api_biblioteca.repositories.RedefenirSenhaRepository;

@Service
public class RedefinirSenhaService {

	@Autowired
	private RedefenirSenhaRepository redefenirSenhaRepository;

	@Autowired
	EmailService emailService;

	// Verifica se o usuario tem alguma redefinicao pendente e sobrepor a
	// redefinicao antiga
	public RedefenirSenhaEntity salvar(RedefenirSenhaEntity emailEntity) {
		Optional<RedefenirSenhaEntity> redefinicao = redefenirSenhaRepository.findByUsuario(emailEntity.getUsuario());
		if (redefinicao.isPresent()) {
			redefenirSenhaRepository.delete(redefinicao.get());
		}
		return redefenirSenhaRepository.save(emailEntity);
	}

	// Cadastra e envia email de redefinição da senha
	public void cadastrarRedefinicao(UsuarioEntity usuario) {

		RedefenirSenhaEntity redefenirSenhaEntity = new RedefenirSenhaEntity();
		redefenirSenhaEntity.setUsuario(usuario);
		redefenirSenhaEntity.setHash(UUID.randomUUID().toString());
		
		emailService.sendEmail(redefenirSenhaEntity);

		redefenirSenhaRepository.save(redefenirSenhaEntity);
	}

	// Busca Por Hash
	public RedefenirSenhaEntity buscaPorHash(String hash) {
		return redefenirSenhaRepository.findByHash(hash)
				.orElseThrow(() -> new NotFoundBussinessException("Hash Inválido"));
	}

	// deletar
	public void deletar(RedefenirSenhaEntity redefinicaoEncontrada) {
		redefenirSenhaRepository.delete(redefinicaoEncontrada);
	}

	// Valida data de expiracao
	public void validarRedefinicao(RedefenirSenhaEntity redefinicaoEncontrada) {
		if (LocalDateTime.now().isAfter(redefinicaoEncontrada.getDataExpiracao())) {
			redefenirSenhaRepository.delete(redefinicaoEncontrada);
			throw new BusinessException("Hash Expirado");
		}
	}

}
