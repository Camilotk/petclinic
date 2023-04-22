package br.com.metaway.petshop.controllers;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.metaway.petshop.models.Address;
import br.com.metaway.petshop.services.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {
	
	@Autowired
	private AddressService service;
	
	@GetMapping
	public ResponseEntity<Page<Address>> index(Pageable pageable) {
		Page<Address> addresses = service.getAll(pageable);
		return ResponseEntity.ok(addresses);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Address> show(@PathVariable BigInteger id) {
	    Address address = service.getById(id);
	    
	    if(address == null) {
	    	return ResponseEntity.notFound().build();
	    }
	    
	    return ResponseEntity.ok(address);
	}
	
	@PostMapping
	public ResponseEntity<Address> store(@RequestBody Address address) {
		Address newAddress = service.create(address);
		return ResponseEntity.status(HttpStatus.CREATED).body(newAddress);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Address> update(@PathVariable BigInteger id, @RequestBody Address address) {
		Address updatedAddress = service.edit(id, address);
		
		if (updatedAddress == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(updatedAddress);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> destroy(@PathVariable BigInteger id) {
		Address address = service.delete(id);
		
		if (address == null) {
			return ResponseEntity.notFound().build();
		}
		
	    return ResponseEntity.noContent().build();
	}
	
}
