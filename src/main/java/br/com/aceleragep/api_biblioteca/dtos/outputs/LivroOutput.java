package br.com.aceleragep.api_biblioteca.dtos.outputs;

import java.util.List;

import br.com.aceleragep.api_biblioteca.entities.AutorEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroOutput {
	private Long id;
	private String titulo;
	private Integer anoLancamento;
	private List<AutorEntity> autores;
}
