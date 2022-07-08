package br.com.aceleragep.api_biblioteca.converties;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.aceleragep.api_biblioteca.dtos.inputs.UsuarioCadastroInput;
import br.com.aceleragep.api_biblioteca.dtos.inputs.UsuarioPermissoesInput;
import br.com.aceleragep.api_biblioteca.dtos.outputs.UsuarioOutput;
import br.com.aceleragep.api_biblioteca.entities.UsuarioEntity;
import br.com.aceleragep.api_biblioteca.services.PermissaoService;

@Component
public class UsuarioConvert {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PermissaoConvert permissaoConvert;
	
	@Autowired
	PermissaoService permissaoService;

	public UsuarioEntity cadastroInputParaEntity(UsuarioCadastroInput usuarioCadastroInput) {
		UsuarioEntity usuarioEntity = modelMapper.map(usuarioCadastroInput, UsuarioEntity.class);
		usuarioEntity.getPermissoes().add(permissaoService.buscaPeloNome("ACESSO_BASICO"));
		return usuarioEntity;
	}

	public UsuarioOutput entityParaOutput(UsuarioEntity usuarioEncontrado) {
		return modelMapper.map(usuarioEncontrado, UsuarioOutput.class);
	}

	public void copyAtualizacaoInputParaEntity(UsuarioEntity usuarioEncontrado, UsuarioCadastroInput usuarioAtualizacaoInput) {
		modelMapper.map(usuarioAtualizacaoInput, usuarioEncontrado);
		usuarioEncontrado.setPermissoes(permissaoService.findAllListaBuscaLivroAutor());

	}

	// Output
	public Page<UsuarioOutput> pageEntityParaPageOutput(Page<UsuarioEntity> usuariosEncontrados) {
		return usuariosEncontrados.map(this::entityParaOutput);
	}

	public void copyUsuarioPermissoesInputParaEntity(UsuarioEntity usuarioEncontrado,
			UsuarioPermissoesInput usuarioPermissoesInput) {
		usuarioEncontrado.setPermissoes(permissaoConvert.longParaEntity(usuarioPermissoesInput.getPermissoes()));
		
	}

}
