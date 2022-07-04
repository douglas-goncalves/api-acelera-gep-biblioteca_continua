package br.com.aceleragep.api_biblioteca.dtos.inputs;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInput {
	
	@NotBlank(message = "Email é obrigatório")
	private String email;
	@NotBlank(message = "Senha é obrigatório")
	@Length(min=6, max = 32)
	private String senha;

	private Boolean conectado;

	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(this.email, this.senha);
	}

	@AssertTrue
	public boolean isDadosok() {
		if (this.conectado == null) {
			this.conectado = false;
		}
		return true;
	}

}
