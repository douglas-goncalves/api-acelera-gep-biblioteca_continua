package br.com.aceleragep.api_biblioteca.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@AllArgsConstructor()
@NoArgsConstructor()
@Table(name = "tb_permissoes")
public class PermissaoEntity implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;

	@Override
	public String getAuthority() {
		return this.nome; 
	}

	public PermissaoEntity(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}
	
}
