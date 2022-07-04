package br.com.aceleragep.api_biblioteca.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoOutput{
	private Long id;
	private String nome;
	private String descricao;
}
