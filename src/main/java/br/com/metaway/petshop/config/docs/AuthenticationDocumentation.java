package br.com.metaway.petshop.config.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.metaway.petshop.web.AuthenticationRequest;
import br.com.metaway.petshop.web.AuthenticationResponse;
import br.com.metaway.petshop.web.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication")
public interface AuthenticationDocumentation {
	@Operation(summary = "Registers a new client and generates a JWT token")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request);
	
	@Operation(summary = "Authenticates a client and generates a JWT token")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);
}
