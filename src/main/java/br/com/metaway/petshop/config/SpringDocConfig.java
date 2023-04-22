package br.com.metaway.petshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(new Info().title("Petshop API Docs")
											.version("v1")
											.description("API em Spring Boot de um Sistema de Controle de atendimentos de petshops.")); 
	}
}
