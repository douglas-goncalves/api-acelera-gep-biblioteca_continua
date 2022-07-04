package br.com.aceleragep.api_biblioteca.dtos.outputs;

import java.util.ArrayList;
import java.util.List;

import br.com.aceleragep.api_biblioteca.entities.PermissaoEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioOutput {
	private Long id;
	private String nome;
	private String email;
	private List<PermissaoEntity> permissoes = new ArrayList<PermissaoEntity>();
}
