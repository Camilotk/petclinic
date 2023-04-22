package br.com.metaway.petshop.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SpringDocConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(new Info().title("Petshop API Docs")
											.version("v1")
											.description("API em Spring Boot de um Sistema de Controle de atendimentos de petshops."))
				            .tags(Arrays.asList(
				            		new Tag().name("Authentication").description("Authentication endpoints for get JWT"),
				            		new Tag().name("Address").description("Address information endpoints"),
				            		new Tag().name("Contact").description("Contact information endpoints"),
				            		new Tag().name("Pet").description("Pet information endpoints"),
				            		new Tag().name("Race").description("Race information endpoints"),
				            		new Tag().name("Visit").description("Visit information endpoints"))); 
	}
}
