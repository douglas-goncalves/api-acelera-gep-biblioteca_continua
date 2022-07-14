package br.com.aceleragep.api_biblioteca.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_redefinir_senha")
public class RedefenirSenhaEntity {
	@Id
	@Column(name = "hash")
	private String hash;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private UsuarioEntity usuario;
	@Column(name = "data_Expiracao")
	private LocalDateTime dataExpiracao;	
}
