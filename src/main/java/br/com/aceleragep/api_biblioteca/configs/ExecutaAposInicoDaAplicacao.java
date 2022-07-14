package br.com.aceleragep.api_biblioteca.configs;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import br.com.aceleragep.api_biblioteca.entities.PermissaoEntity;
import br.com.aceleragep.api_biblioteca.entities.UsuarioEntity;
import br.com.aceleragep.api_biblioteca.services.PermissaoService;
import br.com.aceleragep.api_biblioteca.services.UsuarioService;

@Configuration
public class ExecutaAposInicoDaAplicacao implements ApplicationRunner {

	@Autowired
	PermissaoService permissaoService;
	@Autowired
	UsuarioService usuarioService;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		//Pre cadastro de todas as permissoes da API
		List<PermissaoEntity> permissoes = permissaoService.listarTodos();

		if (permissoes.size() <= 0) {
			permissoes = Arrays.asList(
					new PermissaoEntity("CADASTRA_AUTOR", "Pode cadastar um novo autor"),
					new PermissaoEntity("LISTA_AUTORES", "Pode listar todos os autores"),
					new PermissaoEntity("BUSCA_AUTOR", "Pode buscar um autor"),
					new PermissaoEntity("REMOVE_AUTOR", "Pode remover um determinado autor"),
					new PermissaoEntity("ATUALIZAR_AUTOR", "Pode atualizar autor"),
					new PermissaoEntity("LISTA_LIVROS_DO_AUTOR", "Pode listar os livro do autor"),
					new PermissaoEntity("CADASTRA_LIVRO", "Pode cadastar um novo livro"),
					new PermissaoEntity("LISTA_LIVROS", "Pode listar todos os livros"),
					new PermissaoEntity("BUSCA_LIVRO", "Pode buscar um livro"),
					new PermissaoEntity("REMOVE_LIVRO", "Pode remover um determinado livro"),
					new PermissaoEntity("ATUALIZAR_LIVRO", "Pode atualizar livro"),
					new PermissaoEntity("ACESSO_BASICO", "Pode acessar o basico"),
					new PermissaoEntity("CADASTRA_USUARIO", "Pode cadastar um novo usuario"),
					new PermissaoEntity("LISTA_USUARIOS", "Pode listar todos os usuarios"),
					new PermissaoEntity("BUSCA_USUARIO", "Pode buscar um usuario"),
					new PermissaoEntity("REMOVE_USUARIO", "Pode remover um determinado usuario"),
					new PermissaoEntity("ATUALIZAR_USUARIO", "Pode atualizar usuario"));
			permissoes.forEach(permissao -> permissaoService.cadastrar(permissao));
		}

		//Pre cadastro do usuario ADMIN da API
		List<UsuarioEntity> usuarios = usuarioService.listarTodos();
		if (usuarios.size() <= 0) {
			UsuarioEntity usuarioEntity = new UsuarioEntity();
			usuarioEntity.setNome("admin");
			usuarioEntity.setEmail("admin@email.com");
			usuarioEntity.setSenha("admin1234");
			usuarioEntity.setPermissoes(permissaoService.listarTodos());
			
			usuarioService.cadastrar(usuarioEntity);
		}
	}
}
