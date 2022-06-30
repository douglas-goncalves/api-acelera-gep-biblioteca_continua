package br.com.aceleragep.api_biblioteca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.aceleragep.api_biblioteca.entities.AutorEntity;
import br.com.aceleragep.api_biblioteca.exceptions.NotFoundBussinessException;
import br.com.aceleragep.api_biblioteca.repositories.AutorRepository;

@Service
public class AutorService {

	@Autowired
	AutorRepository autorRepository;

	public Page<AutorEntity> listarTodos(Pageable paginacao) {
		return autorRepository.findAll(paginacao);
	}

	public AutorEntity cadastrar(AutorEntity autorNovo) {
		return autorRepository.save(autorNovo);
	}

	public AutorEntity buscarPeloId(Long autorId) {
		return autorRepository.findById(autorId).orElseThrow(
				() -> new NotFoundBussinessException(String.format("O autor de id %s não foi encontrado", autorId)));
	}

	public AutorEntity atualizar(AutorEntity autorEncontrado) {
		return autorRepository.save(autorEncontrado);
	}
}
