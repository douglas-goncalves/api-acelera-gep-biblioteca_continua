package br.com.aceleragep.api_biblioteca.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aceleragep.api_biblioteca.entities.RedefenirSenhaEntity;
import br.com.aceleragep.api_biblioteca.entities.UsuarioEntity;

public interface RedefenirSenhaRepository extends JpaRepository<RedefenirSenhaEntity, String> {

	Optional<RedefenirSenhaEntity> findByUsuario(UsuarioEntity usuario);

	Optional<RedefenirSenhaEntity> findByHash(String hash);

}
