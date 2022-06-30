package br.com.aceleragep.api_biblioteca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.aceleragep.api_biblioteca.entities.LivroEntity;
import br.com.aceleragep.api_biblioteca.exceptions.NotFoundBussinessException;
import br.com.aceleragep.api_biblioteca.repositories.LivroRepository;

@Service
public class LivroService {

	@Autowired
	LivroRepository livroRepository;

	public Page<LivroEntity> listarTodos(Pageable paginacao) {
		return livroRepository.findAll(paginacao);
	}

	public LivroEntity cadastrar(LivroEntity livroNovo) {
		return livroRepository.save(livroNovo);
	}

	public LivroEntity buscarPeloId(Long livroId) {
		return livroRepository.findById(livroId).orElseThrow(
				() -> new NotFoundBussinessException(String.format("O livro de id %s não foi encontrado", livroId)));
	}

	public void deletar(LivroEntity livrosEncontrados) {
		livroRepository.delete(livrosEncontrados);
	}

	public LivroEntity atualizar(LivroEntity livroEncontrado) {
		return livroRepository.save(livroEncontrado);
	}

	public Page<LivroEntity> listarLivrosPeloIdAutor(Long autorId, Pageable paginacao) {
		return livroRepository.findAllByAutoresId(autorId, paginacao);
	}

}
