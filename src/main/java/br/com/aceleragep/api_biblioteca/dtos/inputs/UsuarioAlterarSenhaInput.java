package br.com.aceleragep.api_biblioteca.dtos.inputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAlterarSenhaInput {
	private String novaSenha;
	private String confirmarSenha;
}

