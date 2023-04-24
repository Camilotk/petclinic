package br.com.metaway.petshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.metaway.petshop.config.docs.AuthenticationDocumentation;
import br.com.metaway.petshop.services.AuthenticationService;
import br.com.metaway.petshop.web.AuthenticationRequest;
import br.com.metaway.petshop.web.AuthenticationResponse;
import br.com.metaway.petshop.web.RegisterRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationDocumentation {
	
	public final AuthenticationService service;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			 @RequestBody RegisterRequest request) {
		return ResponseEntity.ok(service.register(request));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate (
			 @RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(service.authenticate(request));
	}
	
}
