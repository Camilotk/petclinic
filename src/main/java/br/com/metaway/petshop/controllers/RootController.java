package br.com.metaway.petshop.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class RootController {
	
	@GetMapping("/")
	public ResponseEntity<Void> redirectToSwaggerUI() {
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/swagger-ui/index.html").build().toUri();
		return ResponseEntity.status(302).location(location).build();
	}
}