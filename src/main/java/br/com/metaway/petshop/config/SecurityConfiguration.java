package br.com.metaway.petshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import br.com.metaway.petshop.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeHttpRequests()
				.requestMatchers(new OrRequestMatcher(
						new AntPathRequestMatcher("/"),
						new AntPathRequestMatcher("/v1/auth/**"),
						new AntPathRequestMatcher("/swagger-ui/**"),
						new AntPathRequestMatcher("/v3/api-docs/**")
				)).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/v1/pets/**")).hasAnyAuthority("USER", "ADMIN")
				.anyRequest().hasAnyAuthority("ADMIN")
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // not hold state
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}