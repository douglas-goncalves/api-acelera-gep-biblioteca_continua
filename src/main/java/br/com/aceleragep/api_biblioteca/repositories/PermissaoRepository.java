package br.com.aceleragep.api_biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aceleragep.api_biblioteca.entities.PermissaoEntity;

public interface PermissaoRepository extends JpaRepository<PermissaoEntity, Long> {

}
