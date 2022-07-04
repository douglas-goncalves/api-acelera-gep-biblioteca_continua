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
import br.com.aceleragep.api_biblioteca.converties.UsuarioConvert;
import br.com.aceleragep.api_biblioteca.dtos.inputs.UsuarioInput;
import br.com.aceleragep.api_biblioteca.dtos.outputs.UsuarioOutput;
import br.com.aceleragep.api_biblioteca.entities.UsuarioEntity;
import br.com.aceleragep.api_biblioteca.services.UsuarioService;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioConvert usuarioConvert;

	// FindAll
	@GetMapping
	public Page<UsuarioOutput> listarTodos(
			@ParameterObject @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Page<UsuarioEntity> usuariosEncontrados = usuarioService.listarTodos(paginacao);
		return usuarioConvert.pageEntityParaPageOutput(usuariosEncontrados);
	}

	// FindById
	@GetMapping("/{usuarioId}")
	public UsuarioOutput buscarPorId(@PathVariable Long usuarioId) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscarPeloId(usuarioId);
		return usuarioConvert.entityParaOutput(usuarioEncontrado);
	}

	// Post
	@PostMapping
	public ResponseEntity<UsuarioOutput> cadastrar(@Valid @RequestBody UsuarioInput usuarioInput,
			UriComponentsBuilder uriBuild) {
		UsuarioEntity usuariosNovo = usuarioConvert.inputParaEntity(usuarioInput);
		UsuarioEntity usuariosSalvo = usuarioService.cadastrar(usuariosNovo);
		UsuarioOutput usuariosOutput = usuarioConvert.entityParaOutput(usuariosSalvo);

		URI uri = uriBuild.path(ControllerConfig.PRE_URL + "usuarios/{id}").buildAndExpand(usuariosSalvo.getId())
				.toUri();
		return ResponseEntity.created(uri).body(usuariosOutput);
	}

	// Delete
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{usuarioId}")
	public void deletar(@PathVariable Long usuarioId) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscarPeloId(usuarioId);
		usuarioService.deletar(usuarioEncontrado);
	}

	// Put
	@PutMapping("/{usuarioId}")
	public UsuarioOutput atualizar(@PathVariable Long usuarioId, @Valid @RequestBody UsuarioInput usuarioInput) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscarPeloId(usuarioId);
		usuarioConvert.copyInputParaEntity(usuarioEncontrado, usuarioInput);
		UsuarioEntity usuarioSalvo = usuarioService.atualizar(usuarioEncontrado);
		return usuarioConvert.entityParaOutput(usuarioSalvo);
	}
}
