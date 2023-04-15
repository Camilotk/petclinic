package br.com.metaway.petshop.controllers;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

import br.com.metaway.petshop.models.Client;
import br.com.metaway.petshop.models.Contact;
import br.com.metaway.petshop.repositories.ClientRepository;
import br.com.metaway.petshop.repositories.ContactRepository;
import br.com.metaway.petshop.repositories.dtos.ContactData;
import br.com.metaway.petshop.services.ContactService;

@RestController
@RequestMapping("/contacts")
public class ContactController {
	
	@Autowired
	private ContactService service;
	
	@GetMapping
	public ResponseEntity<List<ContactData>> index() {
	    List<ContactData> contacts = service.getAll();
	    return ResponseEntity.ok(contacts);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ContactData> show(@PathVariable BigInteger id) {
	    ContactData contact = service.getById(id);
	    
	    if (contact == null) {
	    	return ResponseEntity.notFound().build();
	    }
	    
	    return ResponseEntity.ok(contact);
	}
	
	@PostMapping
	public ResponseEntity<ContactData> store(@RequestBody Contact contact) {
	    
		ContactData savedContact = service.create(contact);
		
		if (savedContact == null) {
			return ResponseEntity.badRequest().build();
		}
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ContactData> update(@PathVariable BigInteger id, @RequestBody Contact contact) {
		ContactData updatedContact = service.edit(id, contact);
		
		if (updatedContact == null) {
			return ResponseEntity.notFound().build();
		}
		
		return  ResponseEntity.ok(updatedContact);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> destroy(@PathVariable BigInteger id) {	
		Contact deletedContact = service.deleteById(id);
		
		if (deletedContact == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
