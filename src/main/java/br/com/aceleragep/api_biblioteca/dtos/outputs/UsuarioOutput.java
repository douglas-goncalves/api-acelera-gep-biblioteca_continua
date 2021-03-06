package br.com.aceleragep.api_biblioteca.dtos.outputs;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioOutput {
	private Long id;
	private String nome;
	private String email;
	private List<PermissaoOutput> permissoes = new ArrayList<PermissaoOutput>();
}
