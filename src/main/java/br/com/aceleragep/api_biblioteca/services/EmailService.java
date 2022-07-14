package br.com.aceleragep.api_biblioteca.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.aceleragep.api_biblioteca.entities.RedefenirSenhaEntity;
import br.com.aceleragep.api_biblioteca.exceptions.BusinessException;
import br.com.aceleragep.api_biblioteca.exceptions.NotFoundBussinessException;
import br.com.aceleragep.api_biblioteca.repositories.RedefenirSenhaRepository;
import lombok.Getter;
import lombok.Setter;

@Service
public class EmailService {
	@Value("${site.urlSite}")
	private String urlSite;
	@Value("${mail.host}")
	private String host;
	@Value("${mail.port}")
	private int port;
	@Value("${mail.username}")
	private String username;
	@Value("${mail.password}")
	private String password;
	@Value("${mail.smtp.auth}")
	private String auth;
	@Value("${mail.smtp.starttls.enable}")
	private String starttls;
	@Value("${mail.smtp.startlls_required}")
	private String startllsRequired;

	@Autowired
	private RedefenirSenhaRepository redefenirSenhaRepository;

	public void sendEmail(RedefenirSenhaEntity emailEntity) {
		HtmlBody htmlEmail = htmlEmailCadastro(emailEntity);

		emailEntity.setDataExpiracao(LocalDateTime.now().plusMinutes(30));

		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", this.auth);
		mailProperties.put("mail.smtp.starttls", this.starttls);
		mailProperties.put("mail.smtp.starttls.required", this.startllsRequired);
		mailProperties.put("mail.smtp.host", this.host);
		mailProperties.put("mail.smtp.port", this.port);

		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setUsername(this.username);
		sender.setPassword(this.password);
		sender.setJavaMailProperties(mailProperties);

		try {
			MimeMessage mimeMessage = sender.createMimeMessage();
			mimeMessage.setHeader("Content-Type", "text/html");

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setSubject(htmlEmail.getMsg());
			helper.setText(htmlEmail.getBody(), true);
			helper.setFrom(this.username);
			helper.setTo(emailEntity.getUsuario().getEmail());
			
		
			this.salvar(emailEntity);
			sender.send(mimeMessage);

		} catch (MessagingException ex) {
			throw new RuntimeException("Erro ao enviar Email");
		}
	}
	
	//Verifica se o usuario tem alguma redefinicao pendente e salva redefinicao de senha
	public RedefenirSenhaEntity salvar(RedefenirSenhaEntity emailEntity) {
		Optional<RedefenirSenhaEntity> redefinicao = redefenirSenhaRepository.findByUsuario(emailEntity.getUsuario());
		if(redefinicao.isPresent()) {
			redefenirSenhaRepository.delete(redefinicao.get());	
		}
		return redefenirSenhaRepository.save(emailEntity);
	}
	
	//Busca Por Hash
	public RedefenirSenhaEntity buscaPorHash(String hash) {
		return redefenirSenhaRepository.findByHash(hash).orElseThrow(()-> new NotFoundBussinessException("Hash Inválido"));
	}
	
	// deletar
	public void deletar(RedefenirSenhaEntity redefinicaoEncontrada) {
		redefenirSenhaRepository.delete(redefinicaoEncontrada);
	}
	//Valida data de expiracao
	public void validarRedefinicao(RedefenirSenhaEntity redefinicaoEncontrada) {
		if(LocalDateTime.now().isAfter(redefinicaoEncontrada.getDataExpiracao())) {
			throw new BusinessException("Hash Expirado");
		}
	}
	

	public HtmlBody htmlEmailCadastro(RedefenirSenhaEntity emailEntity) {

		HtmlBody htmlBody = new HtmlBody();

		htmlBody.setMsg("Redefinição de Senha");
		htmlBody.setBody(String.format(
				 "<div style='padding:32px; text-align:center; border: 1px rgba(0,0,0,.3); border-radius:32px ; background-color:#cccc55'>"
							+"<style type='text/css'> "
								+ "a:active { color:#000000; background-color:#ffffff} "
								+ "a:hover { font-weight: bold }"
							+ "</style>"
							+"<div>"
								+"<img src='https://www.aceleragep.com.br/src/img/acelera-logo-redimensionado.png'>"
							+"</div>"
								
							+"<div>"
							   //Mensagem de Bom Dia
								+"<p style='color: #ffffff; font-size:14pt; font-weight: bolder; '>"
									+ "%s senhor(a) %s."
								+ "</p>"
							+"<div>"
								
							+"<div>"
								+"<p style='font-weight: bolder;'>"
									+"Clique no botão abaixo e siga as instruções para redefinir sua senha."
								+ "</p>"
								//Link de redefinicao de senha
								+ "<p>"
								+ 	"<a href= %s>"
								+ 		"<input type='button'value='Redefir Senha'>"
								+ 	"</a>"
								+ "</p>"
							+"</div>"
								
							+"<div>"
								+"<p>Atenciosamente Equipe Acelera G&P, acelerando junto com você.</p>"
							+"</div>"
								
						+"</div>"

				, this.qualParteDiaAgora(), emailEntity.getUsuario().getNome(), this.urlSite + emailEntity.getHash()));
		return htmlBody;
	}

	public String qualParteDiaAgora() {
		LocalTime localTime = LocalTime.now();
		int hours = localTime.getHour();

		if (hours <= 11)return ("Bom Dia, ");
		else if (hours <= 19)return ("Boa Tarde, ");
		else if (hours <= 23)return ("Boa Noite, ");
		else return "";
	}




}

@Getter
@Setter
class HtmlBody {
	private String msg;
	private String body;
}
