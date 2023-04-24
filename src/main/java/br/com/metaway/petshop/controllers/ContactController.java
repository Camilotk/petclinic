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

import br.com.metaway.petshop.config.docs.ContactDocumentation;
import br.com.metaway.petshop.models.Contact;
import br.com.metaway.petshop.repositories.dtos.ContactData;
import br.com.metaway.petshop.services.ContactService;

@RestController
@RequestMapping("/v1/contacts")
public class ContactController implements ContactDocumentation {
	
	@Autowired
	private ContactService service;
	
	@GetMapping
	public ResponseEntity<Page<ContactData>> index(Pageable pageable) {
	    Page<ContactData> contacts = service.getAll(pageable);
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
