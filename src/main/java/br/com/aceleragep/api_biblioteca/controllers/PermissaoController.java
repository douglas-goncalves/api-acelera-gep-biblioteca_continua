package br.com.aceleragep.api_biblioteca.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.aceleragep.api_biblioteca.converties.PermissaoConvert;
import br.com.aceleragep.api_biblioteca.dtos.inputs.PermissaoInput;
import br.com.aceleragep.api_biblioteca.dtos.outputs.PermissaoOutput;
import br.com.aceleragep.api_biblioteca.entities.PermissaoEntity;
import br.com.aceleragep.api_biblioteca.services.PermissaoService;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "permissoes")
public class PermissaoController {

	@Autowired
	private PermissaoService permissaoService;
	@Autowired
	private PermissaoConvert permissaoConvert;

	// FindAll
	@GetMapping
	public List<PermissaoOutput> listarTodos() {
		List<PermissaoEntity> permissoesEncontrados = permissaoService.listarTodos();
		return permissaoConvert.pageEntityParaPageOutput(permissoesEncontrados);
	}

	// FindById
	@GetMapping("/{permissoesId}")
	public PermissaoOutput buscarPorId(@PathVariable Long permissoesId) {
		PermissaoEntity permissaoEncontrado = permissaoService.buscarPeloId(permissoesId);
		return permissaoConvert.entityParaOutput(permissaoEncontrado);
	}

	// Post
	@PostMapping
	public ResponseEntity<PermissaoOutput> cadastrar(@Valid @RequestBody PermissaoInput permissoesInput,
			UriComponentsBuilder uriBuild) {
		PermissaoEntity permissoesNovo = permissaoConvert.inputParaEntity(permissoesInput);
		PermissaoEntity permissoesSalvo = permissaoService.cadastrar(permissoesNovo);
		PermissaoOutput permissoesOutput = permissaoConvert.entityParaOutput(permissoesSalvo);

		URI uri = uriBuild.path(ControllerConfig.PRE_URL + "permissoes/{id}").buildAndExpand(permissoesSalvo.getId())
				.toUri();
		return ResponseEntity.created(uri).body(permissoesOutput);
	}

	// Delete
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{permissoesId}")
	public void deletar(@PathVariable Long permissoesId) {
		PermissaoEntity permissaoEncontrado = permissaoService.buscarPeloId(permissoesId);
		permissaoService.deletar(permissaoEncontrado);
	}

	// Put
	@PutMapping("/{permissoesId}")
	public PermissaoOutput atualizar(@PathVariable Long permissoesId, @Valid @RequestBody PermissaoInput permissoesInput) {
		PermissaoEntity permissaoEncontrado = permissaoService.buscarPeloId(permissoesId);
		permissaoConvert.copyInputParaEntity(permissaoEncontrado, permissoesInput);
		PermissaoEntity permissoesSalvo = permissaoService.atualizar(permissaoEncontrado);
		return permissaoConvert.entityParaOutput(permissoesSalvo);
	}
}
