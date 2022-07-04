package br.com.aceleragep.api_biblioteca.converties;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.aceleragep.api_biblioteca.dtos.inputs.LivroInput;
import br.com.aceleragep.api_biblioteca.dtos.outputs.LivroOutput;
import br.com.aceleragep.api_biblioteca.dtos.outputs.LivroSemAutorOutput;
import br.com.aceleragep.api_biblioteca.entities.LivroEntity;

@Component
public class LivroConvert {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AutorConvert autorConvert;

	public LivroEntity inputParaEntity(LivroInput livroInput) {
		LivroEntity livroEntity = modelMapper.map(livroInput, LivroEntity.class);
		livroEntity.setAutores(autorConvert.longParaEntity(livroInput.getAutores()));
		return livroEntity;
	}

	public LivroOutput entityParaOutput(LivroEntity livroEncontrado) {
		return modelMapper.map(livroEncontrado, LivroOutput.class);
	}

	public LivroSemAutorOutput entityParaOutputSemAutor(LivroEntity livroEncontrado) {
		return modelMapper.map(livroEncontrado, LivroSemAutorOutput.class);
	}

	public void copyInputParaEntity(LivroEntity livroEncontrado, LivroInput livroInput) {
		modelMapper.map(livroInput, livroEncontrado);
		livroEncontrado.setAutores(autorConvert.longParaEntity(livroInput.getAutores()));
	}

	// OutputSemAutor
	public Page<LivroSemAutorOutput> pageEntityParaPageOutputSemAutor(Page<LivroEntity> livrosEncontrados) {
		return livrosEncontrados.map(this::entityParaOutputSemAutor);
	}

	// Output
	public Page<LivroOutput> pageEntityParaPageOutput(Page<LivroEntity> livrosEncontrados) {
		return livrosEncontrados.map(this::entityParaOutput);
	}

}
