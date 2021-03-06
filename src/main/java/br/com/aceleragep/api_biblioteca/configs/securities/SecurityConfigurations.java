package br.com.aceleragep.api_biblioteca.configs.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.aceleragep.api_biblioteca.services.AutenticacaoService;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	 @Autowired
	 private AutenticacaoService autenticacaoService;
	 
	 @Override
	 @Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	// Configuracoes de Autentificacao
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	// Configuracao de Autorizacao
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();
	}

	@Override
	// Configuracao para Recursos estaticos(js, css, imagens, etc.)
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**",
				"/swagger-resources/**");
	}

//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("aceleragep"));
//	}
	
}
