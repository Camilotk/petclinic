package br.com.metaway.petshop.controllers;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.metaway.petshop.models.Client;
import br.com.metaway.petshop.models.Contact;
import br.com.metaway.petshop.repositories.ClientRepository;
import br.com.metaway.petshop.repositories.ContactRepository;

@RestController
@RequestMapping("/contacts")
public class ContactController {
	
	@Autowired
	private ContactRepository repository;

	@Autowired
	private ClientRepository clients;
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@RequestBody Contact contact) {
	    String cpf = contact.getClient().getCpf();
	    System.out.println(cpf);
	    Optional<Client> optionalClient = clients.findById(cpf);
	
	    if (optionalClient.isEmpty()) {
	        return ResponseEntity.badRequest().build();
	    }
	
	    Client client = optionalClient.get();
	    contact.setClient(client);
	
	    Contact newContact = repository.save(contact);

		Map<String, Object> responseBody = new LinkedHashMap<>();
	    responseBody.put("id", newContact.getId());
	    responseBody.put("client_cpf", newContact.getClient().getCpf());
	    responseBody.put("type", newContact.getType());
	    responseBody.put("value", newContact.getValue());
	    responseBody.put("currency", newContact.getCurrency());
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
	}

	@GetMapping
	public ResponseEntity<List<Map<String, Object>>> getContacts() {
	    List<Contact> contacts = repository.findAll();
	    List<Map<String, Object>> responseBody = contacts.stream()
	        .map(contact -> {
	            Map<String, Object> contactInfo = new LinkedHashMap<>();
	            contactInfo.put("id", contact.getId());
	            contactInfo.put("client_cpf", contact.getClient().getCpf());
	            contactInfo.put("type", contact.getType());
	            contactInfo.put("value", contact.getValue());
	            contactInfo.put("currency", contact.getCurrency());
	            return contactInfo;
	        })
	        .collect(Collectors.toList());
	    return ResponseEntity.ok(responseBody);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getContactById(@PathVariable BigInteger id) {
	    Optional<Contact> optionalContact = repository.findById(id);
	    if (optionalContact.isPresent()) {
	        Contact contact = optionalContact.get();
	        Map<String, Object> responseBody = new LinkedHashMap<>();
	        responseBody.put("id", contact.getId());
	        responseBody.put("client_cpf", contact.getClient().getCpf());
	        responseBody.put("type", contact.getType());
	        responseBody.put("value", contact.getValue());
	        responseBody.put("currency", contact.getCurrency());
	        return ResponseEntity.ok(responseBody);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}


}
