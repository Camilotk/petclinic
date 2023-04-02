package br.com.metaway.petshop.config;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;

	// Execute Filter
	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		// Constants
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		
		// If authHeader not exist OR authHeader is not a JWT return error
		if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
		    return;
		}
		
		// get Header JWT info skipping "Bearer "
		jwt = authHeader.substring(7);
		userEmail = jwtService.extractUsername(jwt);
		
		
		// If JWT Token extracted email exists AND user is not authenticated
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// TODO: Validate Token
		}
		
	}

}
