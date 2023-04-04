package br.com.metaway.petshop.services;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.metaway.petshop.models.Client;
import br.com.metaway.petshop.models.Role;
import br.com.metaway.petshop.models.User;
import br.com.metaway.petshop.repositories.ClientRepository;
import br.com.metaway.petshop.web.AuthenticationRequest;
import br.com.metaway.petshop.web.AuthenticationResponse;
import br.com.metaway.petshop.web.RegisterRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final ClientRepository repository;
	private final PasswordEncoder passEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager manager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		Client client = Client.builder()
						.cpf(request.getCpf())
						.firstname(request.getFirstname())
						.lastname(request.getLastname())
						.email(request.getEmail())
						.password(passEncoder.encode(request.getPassword()))
						.registrationDate(new Date())
						.role(Role.USER)
						.build();
		 repository.save(client);
		 String jwtToken = jwtService.generateToken(client);
		 
		 return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		manager.authenticate(new UsernamePasswordAuthenticationToken(request.getCpf(), request.getPassword()));
		User user = repository.findByCpf(request.getCpf()).orElseThrow();
		
		String jwtToken = jwtService.generateToken(user);
		 
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
	
}
