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

import br.com.metaway.petshop.models.Contact;
import br.com.metaway.petshop.repositories.ContactRepository;

@RestController
@RequestMapping("/contacts")
public class ContactController {
	
	@Autowired
	private ContactRepository repository;

	@PostMapping
    public ResponseEntity<Contact> create(@RequestBody Contact contact) {
        Contact newContact = repository.save(contact);
		return ResponseEntity.status(HttpStatus.CREATED).body(newContact);
    }

	@GetMapping
    public ResponseEntity<List<Contact>> getAll() {
        List<Contact> contacts = repository.findAll();
        return ResponseEntity.ok(contacts);
    }

}
