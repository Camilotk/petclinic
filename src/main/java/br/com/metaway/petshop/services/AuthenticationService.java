package br.com.metaway.petshop.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.metaway.petshop.repositories.UserRepository;
import br.com.metaway.petshop.web.AuthenticationRequest;
import br.com.metaway.petshop.web.AuthenticationResponse;
import br.com.metaway.petshop.web.RegisterRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository repository;
	private final PasswordEncoder passEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager manager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
