package br.com.aceleragep.api_biblioteca.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_livros")
public class LivroEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "titulo", length = 200, nullable = false)
	private String titulo;

	@Column(name = "ano_lancamento", nullable = false, columnDefinition = "int(4)")
	private Integer anoLancamento;

	@ManyToMany
	@JoinTable(name = "tb_livros_autores", joinColumns = @JoinColumn(name = "livro_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
	private List<AutorEntity> autores;
}
