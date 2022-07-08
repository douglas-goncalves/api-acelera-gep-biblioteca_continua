package br.com.aceleragep.api_biblioteca.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.aceleragep.api_biblioteca.configs.ControllerConfig;
import br.com.aceleragep.api_biblioteca.configs.securities.PodeAcessarSe;
import br.com.aceleragep.api_biblioteca.converties.AutorConvert;
import br.com.aceleragep.api_biblioteca.converties.LivroConvert;
import br.com.aceleragep.api_biblioteca.dtos.inputs.AutorInput;
import br.com.aceleragep.api_biblioteca.dtos.outputs.AutorOutput;
import br.com.aceleragep.api_biblioteca.dtos.outputs.LivroSemAutorOutput;
import br.com.aceleragep.api_biblioteca.entities.AutorEntity;
import br.com.aceleragep.api_biblioteca.entities.LivroEntity;
import br.com.aceleragep.api_biblioteca.services.AutorService;
import br.com.aceleragep.api_biblioteca.services.LivroService;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "autores")
public class AutorController {

	@Autowired
	private AutorService autorService;
	@Autowired
	private AutorConvert autorConvert;
	@Autowired
	private LivroService livroService;
	@Autowired
	private LivroConvert livroConvert;

	// FindAll
	@GetMapping
	@PodeAcessarSe.TemPermissaoListaAutores
	public Page<AutorOutput> listarTodos(
			@ParameterObject @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Page<AutorEntity> autoresEncontrados = autorService.listarTodos(paginacao);
		return autorConvert.pageEntityParaPageOutput(autoresEncontrados);
	}

	// FindById
	@GetMapping("/{autorId}")
	@PodeAcessarSe.TemPermissaoBuscarAutor
	public AutorOutput buscarPorId(@PathVariable Long autorId) {
		AutorEntity autorEncontrado = autorService.buscarPeloId(autorId);
		return autorConvert.entityParaOutput(autorEncontrado);
	}

	// Post
	@PostMapping
	@PodeAcessarSe.TemPermissaoCadastrarAutor
	public ResponseEntity<AutorOutput> cadastrar(@Valid @RequestBody AutorInput autorInput,
			UriComponentsBuilder uriBuild) {
		AutorEntity autorNovo = autorConvert.inputParaEntity(autorInput);
		AutorEntity autorSalvo = autorService.cadastrar(autorNovo);
		AutorOutput autorOutput = autorConvert.entityParaOutput(autorSalvo);

		URI uri = uriBuild.path(ControllerConfig.PRE_URL + "autores/{id}").buildAndExpand(autorSalvo.getId()).toUri();
		return ResponseEntity.created(uri).body(autorOutput);
	}

	// Put
	@PutMapping("/{autorId}")
	@PodeAcessarSe.TemPermissaoAtualizaAutor
	public AutorOutput atualizar(@PathVariable Long autorId, @Valid @RequestBody AutorInput autorInput) {
		AutorEntity autorEncontrado = autorService.buscarPeloId(autorId);
		autorConvert.copyInputToEntity(autorEncontrado, autorInput);
		AutorEntity autorSalvo = autorService.atualizar(autorEncontrado);
		return autorConvert.entityParaOutput(autorSalvo);
	}

	// Retorna Todos os Livro pelo ID do Autor
	@GetMapping("/{autorId}/livros")
	@PodeAcessarSe.TemPermissaoListaLivrosDoAutor
	public Page<LivroSemAutorOutput> listarLivrosPeloIdAutor(@PathVariable Long autorId,
			@ParameterObject @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable paginacao) {

		autorService.buscarPeloId(autorId);
		Page<LivroEntity> livrosAutor = livroService.listarLivrosPeloIdAutor(autorId, paginacao);
		return livroConvert.pageEntityParaPageOutputSemAutor(livrosAutor);
	}
}
