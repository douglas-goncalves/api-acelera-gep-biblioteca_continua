package br.com.aceleragep.api_biblioteca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.aceleragep.api_biblioteca.entities.PermissaoEntity;

public interface PermissaoRepository extends JpaRepository<PermissaoEntity, Long> {
	
	PermissaoEntity findByNome(String nome);
	
	@Query("SELECT p FROM PermissaoEntity p where (((p.nome LIKE 'LISTA%') OR (p.nome LIKE 'BUSCA%'))"
			+ "AND ((p.nome LIKE '%LIVRO%' OR (p.nome LIKE '%AUTOR%')))) OR (p.nome = 'ACESSO_BASICO')")
	List<PermissaoEntity> findAllListaBuscaLivroAutor();

}

/*
'1',  'CADASTRA_AUTOR'
'2',  'LISTA_AUTORES'
'3',  'BUSCA_AUTOR'
'4',  'REMOVE_AUTOR'
'5',  'ATUALIZAR_AUTOR'
'6',  'LISTA_LIVROS_DO_AUTOR'
'7',  'CADASTRA_LIVRO'
'8',  'LISTA_LIVROS'
'9',  'BUSCA_LIVRO'
'10', 'REMOVE_LIVRO'
'11', 'ATUALIZAR_LIVRO'
'12', 'ACESSO_BASICO'
'13', 'CADASTRA_USUARIO'
'14', 'LISTA_USUARIOS'
'15', 'BUSCA_USUARIO'
'16', 'REMOVE_USUARIO'
'17', 'ATUALIZAR_USUARIO'
*/