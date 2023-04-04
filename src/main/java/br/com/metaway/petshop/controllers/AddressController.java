package br.com.metaway.petshop.controllers;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.repositories.AddressRepository;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

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
		List<Address> address = this.repository.findAll();
		return ResponseEntity.ok(address);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Address> getById(@PathVariable BigInteger id) {
	    Optional<Address> address = this.repository.findById(id);
	    if (address.isPresent()) {
	        return ResponseEntity.ok(address.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Address> update(@PathVariable BigInteger id, @RequestBody Address updatedAddress) {
	    Optional<Address> address = this.repository.findById(id);
	    if (address.isPresent()) {
	        
	    	Address existingAddress = address.get();
	        
	        existingAddress.setAddressLine(updatedAddress.getAddressLine());
	        existingAddress.setAvenue(updatedAddress.getAvenue());
	        existingAddress.setCity(updatedAddress.getCity());
	        existingAddress.setState(updatedAddress.getState());
	        existingAddress.setCountry(updatedAddress.getCountry());
	        existingAddress.setZipCode(updatedAddress.getZipCode());
	        existingAddress.setAdditionalInformation(updatedAddress.getAdditionalInformation());
	        
	        Address savedAddress = this.repository.save(existingAddress);
	        
	        return ResponseEntity.ok(savedAddress);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable BigInteger id) {
	    Optional<Address> address = this.repository.findById(id);
	    if (address.isPresent()) {
	        this.repository.delete(address.get());
	        return ResponseEntity.noContent().build();
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
}
