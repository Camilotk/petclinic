package br.com.metaway.petshop.controllers;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
	
	@GetMapping("/")
	public Map<String, String> hello() {
		Map<String, String> message = new HashMap<>();
		message.put("message", "Hello World!");
		message.put("date", Instant.now().toString());
		
		return message;
	}
}
