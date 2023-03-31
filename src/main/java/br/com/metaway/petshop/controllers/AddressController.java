package br.com.metaway.petshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.metaway.petshop.models.Address;
import br.com.metaway.petshop.repositories.AddressRepository;

@RestController
@RequestMapping("/addresses")
public class AddressController {
	
	@Autowired
	private AddressRepository repository;
	
	@PostMapping
	public ResponseEntity<Address> create(@RequestBody Address address) {
		Address newAddress = this.repository.save(address);
		return ResponseEntity.status(HttpStatus.CREATED).body(newAddress);
	}
	
	@GetMapping
	public ResponseEntity<List<Address>> getAll() {
		List<Address> add = this.repository.findAll();
		System.out.println(add);
		return ResponseEntity.ok(add);
	}
	
}
