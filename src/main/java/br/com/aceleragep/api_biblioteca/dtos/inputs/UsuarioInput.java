package br.com.aceleragep.api_biblioteca.dtos.inputs;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {
	private String nome;
	private String email;
	private String senha;
	private List<Long> permissoes = new ArrayList<Long>();
}

