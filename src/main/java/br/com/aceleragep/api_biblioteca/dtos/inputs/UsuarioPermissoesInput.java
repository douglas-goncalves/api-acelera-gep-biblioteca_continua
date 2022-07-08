package br.com.aceleragep.api_biblioteca.dtos.inputs;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioPermissoesInput {
	private List<Long> permissoes;
}
