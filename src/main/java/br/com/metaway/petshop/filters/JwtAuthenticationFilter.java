package br.com.metaway.petshop.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.metaway.petshop.services.JwtService;
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
	private final UserDetailsService userDetailsService;

	// Execute Filter
	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		// Constants
		final String authHeader = request.getHeader("Authorization");
		final String jwtToken;
		final String userEmail;
		
		// If authHeader not exist OR authHeader is not a JWT return error
		if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
		    return;
		}
		
		// get Header JWT info skipping "Bearer "
		jwtToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(jwtToken);
		
		
		// If JWT Token extracted email exists AND user is not authenticated
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails user = this.userDetailsService.loadUserByUsername(userEmail);
			
			// If Token is valid
			if (jwtService.isJwtTokenValid(jwtToken, user)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
