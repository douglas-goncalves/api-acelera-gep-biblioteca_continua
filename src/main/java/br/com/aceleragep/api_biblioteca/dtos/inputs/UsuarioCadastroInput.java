package br.com.aceleragep.api_biblioteca.dtos.inputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCadastroInput {
	private String nome;
	private String email;
	private String senha;
}

