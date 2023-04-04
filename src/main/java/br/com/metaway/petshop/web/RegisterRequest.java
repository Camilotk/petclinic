package br.com.metaway.petshop.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private String cpf;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
}
