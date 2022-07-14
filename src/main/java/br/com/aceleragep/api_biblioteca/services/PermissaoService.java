package br.com.aceleragep.api_biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	//CADASTRA
	public PermissaoEntity cadastrar(PermissaoEntity permissaoNovo) {
		return permissaoRepository.save(permissaoNovo);
	}

	//BUSCA PELO ID
	public PermissaoEntity buscarPeloId(Long permissaoId) {
		return permissaoRepository.findById(permissaoId).orElseThrow(
				() -> new NotFoundBussinessException(String.format("O permissao de id %s não foi encontrado", permissaoId)));
	}

	//DELETAR
	public void deletar(PermissaoEntity permissaosEncontrados) {
		permissaoRepository.delete(permissaosEncontrados);
	}

	//ATUALIZAR
	public PermissaoEntity atualizar(PermissaoEntity permissaoEncontrado) {
		return permissaoRepository.save(permissaoEncontrado);
	}

	//BUSCA POR NOME
	public PermissaoEntity buscaPeloNome(String nome) {
		return permissaoRepository.findByNome(nome);
	}

	//PERMISSES DE LISTA OU BUSCAR OS LIVROS OU AUTORES
	public List<PermissaoEntity> findAllListaBuscaLivroAutor() {
		return permissaoRepository.findAllListaBuscaLivroAutor();
	}

}
