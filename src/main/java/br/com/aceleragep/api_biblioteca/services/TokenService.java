package br.com.aceleragep.api_biblioteca.services;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.aceleragep.api_biblioteca.dtos.inputs.LoginInput;
import br.com.aceleragep.api_biblioteca.entities.PermissaoEntity;
import br.com.aceleragep.api_biblioteca.entities.UsuarioEntity;
import br.com.aceleragep.api_biblioteca.exceptions.BadRequestBussinessException;
import br.com.aceleragep.api_biblioteca.exceptions.UnauthorizedAccessBussinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${biblioteca.jwt.expirationQuizeDias}")
	private String expiraQuizeDias;

	@Value("${biblioteca.jwt.expirationUmDia}")
	private String expiraUmDia;

	@Value("${biblioteca.jwt.secret}")
	private String secretKey;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	private HttpServletRequest resquest;

	public String gerarToken(Authentication authentication, LoginInput login) {
		UsuarioEntity logado = (UsuarioEntity) authentication.getPrincipal();
		Boolean conectado = login.getConectado() == null ? false : login.getConectado();

		Date hoje = new Date();
		Date dataExpiracao = new Date();

		if (conectado) {
			dataExpiracao = new Date(hoje.getTime() + Long.parseLong(this.expiraQuizeDias));
		} else {
			dataExpiracao = new Date(hoje.getTime() + Long.parseLong(this.expiraUmDia));
		}

		JwtBuilder build = Jwts.builder()
				.claim("id", logado.getId())
				.claim("nome", logado.getNome())
				.claim("email", logado.getEmail())
				.claim("permissoes", logado.getPermissoes())
				.setIssuer("Api AceleraGep Biblioteca")
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, this.secretKey);

		return build.compact();
	}

	public UsuarioEntity getUserByToken() {
		Claims claims = this.extractClaims();
		long id = Long.valueOf(claims.get("id").toString());
		return usuarioService.buscarPeloId(id);
	}

	private Claims extractClaims() {
		String token = resquest.getHeader("Authorization");
		token = token.substring(7);
		try {
			return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			throw new BadRequestBussinessException("Token inválido!");
		}
	}

	public boolean canAccessAuthenticated() {

		Claims claims = extractClaims();
		if (claims != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean canAccessByPermission(String permissionRecived) {

		UsuarioEntity usuario = this.getUserByToken();

		for (PermissaoEntity permissao : usuario.getPermissoes()) {
			if (permissao.getNome().equals(permissionRecived)) {
				return true;
			}
		}

		throw new UnauthorizedAccessBussinessException("Usuario Não Autorizado");
	}
}
