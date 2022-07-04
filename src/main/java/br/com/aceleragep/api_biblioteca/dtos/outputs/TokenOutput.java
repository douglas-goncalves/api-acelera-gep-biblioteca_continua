package br.com.aceleragep.api_biblioteca.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenOutput {
	private String token;
	private String tipo;
	public TokenOutput(String token, String tipo) {
		
		this.token = token;
		this.tipo = tipo;
	}
}
