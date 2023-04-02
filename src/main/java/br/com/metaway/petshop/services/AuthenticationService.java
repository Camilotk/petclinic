package br.com.metaway.petshop.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.metaway.petshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	// properties
	private final UserRepository repository;
	private final PasswordEncoder passEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager manager;
	
}
