package br.com.aceleragep.api_biblioteca.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.aceleragep.api_biblioteca.configs.ControllerConfig;
import br.com.aceleragep.api_biblioteca.converties.LivroConvert;
import br.com.aceleragep.api_biblioteca.dtos.inputs.LivroInput;
import br.com.aceleragep.api_biblioteca.dtos.outputs.LivroOutput;
import br.com.aceleragep.api_biblioteca.entities.LivroEntity;
import br.com.aceleragep.api_biblioteca.services.LivroService;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "livros")
public class LivroController {

	@Autowired
	LivroService livroService;
	@Autowired
	LivroConvert livroConvert;

	// FindAll
	@GetMapping
	public Page<LivroOutput> listarTodos(
			@ParameterObject @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Page<LivroEntity> livrosEncontrados = livroService.listarTodos(paginacao);
		return livroConvert.pageEntityParaPageOutput(livrosEncontrados);
	}

	// FindById
	@GetMapping("/{livroId}")
	public LivroOutput buscarPorId(@PathVariable Long livroId) {
		LivroEntity livroEncontrado = livroService.buscarPeloId(livroId);
		return livroConvert.entityParaOutput(livroEncontrado);
	}

	// Post
	@PostMapping
	public ResponseEntity<LivroEntity> cadastrar(@Valid @RequestBody LivroInput livroInput,
			UriComponentsBuilder uriBuild) {
		LivroEntity livroNovo = livroConvert.inputParaEntity(livroInput);
		LivroEntity livroSalvo = livroService.cadastrar(livroNovo);

		URI uri = uriBuild.path(ControllerConfig.PRE_URL + "livros/{id}").buildAndExpand(livroSalvo.getId()).toUri();
		return ResponseEntity.created(uri).body(livroSalvo);
	}

	// Delete
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{livroId}")
	public void deletar(@PathVariable Long livroId) {
		LivroEntity livroEncontrado = livroService.buscarPeloId(livroId);
		livroService.deletar(livroEncontrado);
	}

	// Put
	@PutMapping("/{livroId}")
	public LivroOutput atualizar(@PathVariable Long livroId, @Valid @RequestBody LivroInput livroInput) {
		LivroEntity livroEncontrado = livroService.buscarPeloId(livroId);
		livroConvert.copyInputParaEntity(livroEncontrado, livroInput);
		LivroEntity livroSalvo = livroService.atualizar(livroEncontrado);
		return livroConvert.entityParaOutput(livroSalvo);
	}
}
