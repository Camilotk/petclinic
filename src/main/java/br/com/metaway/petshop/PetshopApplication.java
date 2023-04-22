package br.com.metaway.petshop;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PetshopApplication {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt", "BR"));
		SpringApplication.run(PetshopApplication.class, args);
	}

}
