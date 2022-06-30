package br.com.aceleragep.api_biblioteca.dtos.inputs;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutorInput {
	@NotBlank
	@Length(min = 1, max = 100)
	private String nome;
	@NotBlank
	@Length(min = 1, max = 1000)
	private String bibliografia;
}
