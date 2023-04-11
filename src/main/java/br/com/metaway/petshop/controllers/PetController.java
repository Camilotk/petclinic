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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.metaway.petshop.models.Client;
import br.com.metaway.petshop.models.Pet;
import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.repositories.ClientRepository;
import br.com.metaway.petshop.repositories.PetRepository;
import br.com.metaway.petshop.repositories.RaceRepository;
import br.com.metaway.petshop.repositories.dtos.PetData;
import br.com.metaway.petshop.services.PetService;


@RestController
@RequestMapping("/pets")
public class PetController {
	@Autowired
	private PetService service;
	
	@GetMapping
	public ResponseEntity<List<PetData>> index() {
		List<PetData> pets = service.getAll();
		return ResponseEntity.ok(pets);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PetData> show(@PathVariable BigInteger id) {
		PetData pet = service.getById(id);
		
		if (pet == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(pet);
	}
	
	@PostMapping
    public ResponseEntity<PetData> store(@RequestBody Pet pet) {
		PetData newPet = service.create(pet);
		
		if (newPet == null) {
			return ResponseEntity.badRequest().build();
		}
        
        return ResponseEntity.status(HttpStatus.CREATED).body(newPet);
    }

	@PutMapping("/{id}")
	public ResponseEntity<PetData> update(@PathVariable BigInteger id, @RequestBody Pet pet) {
		PetData editedPet = service.edit(id, pet);
		
		if (editedPet == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(editedPet);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> destroy(@PathVariable BigInteger id) {
		Pet pet = service.delete(id);

		if (pet == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

}
