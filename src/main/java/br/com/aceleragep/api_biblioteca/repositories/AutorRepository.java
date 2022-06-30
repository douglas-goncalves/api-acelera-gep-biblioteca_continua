package br.com.aceleragep.api_biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aceleragep.api_biblioteca.entities.AutorEntity;

public interface AutorRepository extends JpaRepository<AutorEntity, Long> {

}
