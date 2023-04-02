package br.com.metaway.petshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.metaway.petshop.services.AuthenticationService;
import br.com.metaway.petshop.web.AuthenticationResponse;
import br.com.metaway.petshop.web.RegisterRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	public final AuthenticationService service;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			 @RequestBody RegisterRequest request) {
		return ResponseEntity.ok(service);
	}
	
	
}
