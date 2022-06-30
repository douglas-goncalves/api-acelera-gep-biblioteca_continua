package br.com.aceleragep.api_biblioteca.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroOutputSemAutor {
	private Long id;
	private String titulo;
	private Integer anoLancamento;
}
