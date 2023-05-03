package br.com.metaway.petshop.controllers;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.metaway.petshop.config.docs.PetDocumentation;
import br.com.metaway.petshop.models.Pet;
import br.com.metaway.petshop.repositories.dtos.PetData;
import br.com.metaway.petshop.services.PetService;


@RestController
@RequestMapping("/v1/pets")
public class PetController implements PetDocumentation {
	@Autowired
	private PetService service;
	
	@GetMapping("/daba")
	public GrantedAuthority getDabadaou(Authentication auth) {
		GrantedAuthority role = auth.getAuthorities().iterator().next();
		System.out.println(role);
		return role;
	}
	
	@GetMapping
	public ResponseEntity<Page<PetData>> index(Pageable pageable) {
		if (service.containRole("USER")) {
			Page<PetData> petsOfUser = service.getPetsByClientCpf(SecurityContextHolder.getContext().getAuthentication().getName(), pageable);
			return ResponseEntity.ok(petsOfUser);
		}

		Page<PetData> pets = service.getAll(pageable);
		return ResponseEntity.ok(pets);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PetData> show(@PathVariable BigInteger id) {
		PetData pet = service.getById(id);
		
		if (pet == null) {
			return ResponseEntity.notFound().build();
		}
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (service.containRole("USER") && pet.clientCpf() != username) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		return ResponseEntity.ok(pet);
	}
	
	@PostMapping
    public ResponseEntity<PetData> store(@RequestBody Pet pet) {
		PetData search = service.getById(pet.getId());
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		if (service.containRole("USER") && search.clientCpf() != username) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
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
		if (service.containRole("USER")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Pet pet = service.delete(id);

		if (pet == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

}
