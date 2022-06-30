package br.com.aceleragep.api_biblioteca.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_autores")
public class AutorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	@Column(name = "bibliografia", length = 1000, nullable = false)
	private String bibliografia;
}
