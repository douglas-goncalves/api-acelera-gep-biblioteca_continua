package br.com.aceleragep.api_biblioteca.converties;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.aceleragep.api_biblioteca.dtos.inputs.AutorInput;
import br.com.aceleragep.api_biblioteca.dtos.outputs.AutorOutput;
import br.com.aceleragep.api_biblioteca.entities.AutorEntity;
import br.com.aceleragep.api_biblioteca.services.AutorService;

@Component
public class AutorConvert {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AutorService autorService;

	public AutorEntity inputParaEntity(AutorInput autorInput) {
		return modelMapper.map(autorInput, AutorEntity.class);
	}

	public AutorOutput entityParaOutput(AutorEntity autorEncontrado) {
		return modelMapper.map(autorEncontrado, AutorOutput.class);
	}

	public void copyInputToEntity(AutorEntity autorEncontrado, AutorInput autorInput) {
		modelMapper.map(autorInput, autorEncontrado);
	}

	public Page<AutorOutput> pageEntityParaPageOutput(Page<AutorEntity> autoresEncontrados) {
		return autoresEncontrados.map(this::entityParaOutput);
	}

	public List<AutorEntity> longParaEntity(List<Long> listLong) {
		
		return listLong.stream().map(autorService::buscarPeloId).collect(Collectors.toList());
		
	}
}
