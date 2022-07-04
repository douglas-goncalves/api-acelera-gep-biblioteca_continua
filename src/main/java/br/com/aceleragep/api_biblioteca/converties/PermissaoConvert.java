package br.com.aceleragep.api_biblioteca.converties;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.aceleragep.api_biblioteca.dtos.inputs.PermissaoInput;
import br.com.aceleragep.api_biblioteca.dtos.outputs.PermissaoOutput;
import br.com.aceleragep.api_biblioteca.entities.PermissaoEntity;
import br.com.aceleragep.api_biblioteca.services.PermissaoService;



@Component
public class PermissaoConvert {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PermissaoService permissaoService;

	public PermissaoEntity inputParaEntity(PermissaoInput permissaoInput) {
		return modelMapper.map(permissaoInput, PermissaoEntity.class);
	}

	public PermissaoOutput entityParaOutput(PermissaoEntity permissaoeEncontrado) {
		return modelMapper.map(permissaoeEncontrado, PermissaoOutput.class);
	}

	public void copyInputParaEntity(PermissaoEntity permissaoEncontrado, PermissaoInput permissaoInput) {
		modelMapper.map(permissaoInput, permissaoEncontrado);
	}

	public List<PermissaoOutput> pageEntityParaPageOutput(List<PermissaoEntity> permissaoesEncontrados) {
		return permissaoesEncontrados.stream().map(this::entityParaOutput).toList();
	}

	public List<PermissaoEntity> longParaEntity(List<Long> listLong) {
		return listLong.stream().map(permissaoService::buscarPeloId).toList();
	}
}
