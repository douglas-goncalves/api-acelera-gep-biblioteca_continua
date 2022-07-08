package br.com.aceleragep.api_biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.aceleragep.api_biblioteca.entities.PermissaoEntity;
import br.com.aceleragep.api_biblioteca.exceptions.NotFoundBussinessException;
import br.com.aceleragep.api_biblioteca.repositories.PermissaoRepository;

@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;

	public List<PermissaoEntity> listarTodos() {
		return permissaoRepository.findAll();
	}

	public PermissaoEntity cadastrar(PermissaoEntity permissaoNovo) {
		return permissaoRepository.save(permissaoNovo);
	}

	public PermissaoEntity buscarPeloId(Long permissaoId) {
		return permissaoRepository.findById(permissaoId).orElseThrow(
				() -> new NotFoundBussinessException(String.format("O permissao de id %s não foi encontrado", permissaoId)));
	}

	public void deletar(PermissaoEntity permissaosEncontrados) {
		permissaoRepository.delete(permissaosEncontrados);
	}

	public PermissaoEntity atualizar(PermissaoEntity permissaoEncontrado) {
		return permissaoRepository.save(permissaoEncontrado);
	}

	public PermissaoEntity buscaPeloNome(String nome) {
		
		return permissaoRepository.findByNome(nome);
	}

	public List<PermissaoEntity> findAllListaBuscaLivroAutor() {
		return permissaoRepository.findAllListaBuscaLivroAutor();
	}

}
