package br.com.aceleragep.api_biblioteca.services;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.aceleragep.api_biblioteca.dtos.inputs.LoginInput;
import br.com.aceleragep.api_biblioteca.entities.UsuarioEntity;
import br.com.aceleragep.api_biblioteca.exceptions.BadRequestBussinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	//@Value("${biblioteca.jwt.expirationQuizeDias}")
	private String expiraQuizeDias = "1296000000";
	
	//@Value("${biblioteca.jwt.expirationUmDia}")
	private String expiraUmDia = "86400000";
	
	//@Value("${biblioteca.jwt.secret}")
	private String secretKey = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY1NjYzNDYxOSwiaWF0IjoxNjU2NjM0NjE5fQ.CCtsxpEwbkHcp-VobtnBr67xq4I9kkXLjVsh1PpEcAw";
	
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	private HttpServletRequest resquest;

	public String gerarToken(Authentication authentication, LoginInput login) {
		UsuarioEntity logado = (UsuarioEntity) authentication.getPrincipal();
		Boolean conectado = login.getConectado() == null ? false : login.getConectado();
				
		Date hoje = new Date();
		Date dataExpiracao = new Date();
		
		if(conectado) {
			dataExpiracao = new Date(hoje.getTime() + Long.parseLong(this.expiraQuizeDias));
		}else {
			dataExpiracao = new Date(hoje.getTime() + Long.parseLong(this.expiraUmDia));
		}
		
		JwtBuilder	build = Jwts.builder()
		.claim("id", logado.getId())
		.claim("nome", logado.getId())
		.claim("email", logado.getId())
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

}
