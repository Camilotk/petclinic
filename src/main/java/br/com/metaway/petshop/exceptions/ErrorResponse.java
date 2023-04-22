package br.com.metaway.petshop.exceptions;

import java.util.List;

public record ErrorResponse(String userMessage, String devMessage, List<String> messages) {
	
}
