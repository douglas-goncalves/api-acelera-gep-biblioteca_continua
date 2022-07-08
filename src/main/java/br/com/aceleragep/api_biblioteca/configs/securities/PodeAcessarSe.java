package br.com.aceleragep.api_biblioteca.configs.securities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface PodeAcessarSe {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessAuthenticated()")
	public @interface EstaAutenticado {

	}

	// LIVRO_________________________________________________________________

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('CADASTRA_LIVRO')")
	public @interface TemPermissaoCadastrarLivro {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('LISTA_LIVROS')")
	public @interface TemPermissaoListaLivros {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('BUSCA_LIVRO')")
	public @interface TemPermissaoBuscarLivro {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('REMOVE_LIVRO')")
	public @interface TemPermissaoRemoverLivro {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('ATUALIZA_LIVRO')")
	public @interface TemPermissaoAtualizaLivro {

	}

	// AUTOR_________________________________________________________________

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('CADASTRA_AUTOR')")
	public @interface TemPermissaoCadastrarAutor {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('LISTA_AUTORES')")
	public @interface TemPermissaoListaAutores {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('BUSCA_AUTOR')")
	public @interface TemPermissaoBuscarAutor {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('REMOVE_AUTOR')")
	public @interface TemPermissaoRemoverAutor {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('ATUALIZA_AUTOR')")
	public @interface TemPermissaoAtualizaAutor {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('LISTA_LIVROS_DO_AUTOR')")
	public @interface TemPermissaoListaLivrosDoAutor {

	}

	// USUARIO_________________________________________________________________

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('ACESSO_BASICO')")
	public @interface TemPermissaoAcessoBasico {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('CADASTRA_USUARIO')")
	public @interface TemPermissaoCadastrarUsuario {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('LISTA_USUARIOS')")
	public @interface TemPermissaoListaUsuarios {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('BUSCA_USUARIO')")
	public @interface TemPermissaoBuscarUsuario {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('REMOVE_USUARIO')")
	public @interface TemPermissaoRemoverUsuario {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@tokenService.canAccessByPermission('ATUALIZA_USUARIO')")
	public @interface TemPermissaoAtualizaUsuario {

	}

}
