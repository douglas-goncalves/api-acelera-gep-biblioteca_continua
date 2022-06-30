package br.com.aceleragep.api_biblioteca.configs.swagger;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfigurations {
	@Bean
	public GroupedOpenApi aceleraBibliotecaApi() {
		return GroupedOpenApi.builder().group("br.com.aceleragep.api_biblioteca").pathsToMatch("/**").build();
	}

	@Bean
	public OpenAPI forumAluraOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("AceleraGepBibliotecaApi").description("Projeto de Api AceleraGep Biblioteca")
						.version("v0.0.1").license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.components(new Components().addSecuritySchemes("bearer-key",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
				.externalDocs(new ExternalDocumentation().description("SpringShop Wiki Documentation")
						.url("https://springshop.wiki.github.org/docs"))
				.components(new Components().addSecuritySchemes("bearer-key",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
								.in(SecurityScheme.In.HEADER).bearerFormat("JWT")));
	}
}
