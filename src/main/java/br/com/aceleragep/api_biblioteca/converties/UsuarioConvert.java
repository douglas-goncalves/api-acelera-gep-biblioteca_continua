package br.com.aceleragep.api_biblioteca.converties;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.aceleragep.api_biblioteca.dtos.inputs.UsuarioInput;
import br.com.aceleragep.api_biblioteca.dtos.outputs.UsuarioOutput;
import br.com.aceleragep.api_biblioteca.entities.UsuarioEntity;

@Component
public class UsuarioConvert {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PermissaoConvert permissoesConvert;

	public UsuarioEntity inputParaEntity(UsuarioInput usuarioInput) {
		UsuarioEntity usuarioEntity = modelMapper.map(usuarioInput, UsuarioEntity.class);
		usuarioEntity.setPermissoes(permissoesConvert.longParaEntity(usuarioInput.getPermissoes()));
		return usuarioEntity;
	}

	public UsuarioOutput entityParaOutput(UsuarioEntity usuarioEncontrado) {
		return modelMapper.map(usuarioEncontrado, UsuarioOutput.class);
	}

	public void copyInputParaEntity(UsuarioEntity usuarioEncontrado, UsuarioInput usuarioInput) {
		modelMapper.map(usuarioInput, usuarioEncontrado);
	}

	// Output
	public Page<UsuarioOutput> pageEntityParaPageOutput(Page<UsuarioEntity> usuariosEncontrados) {
		return usuariosEncontrados.map(this::entityParaOutput);
	}

}
