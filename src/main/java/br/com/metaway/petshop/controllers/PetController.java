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
import br.com.metaway.petshop.models.Pet;
import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.repositories.ClientRepository;
import br.com.metaway.petshop.repositories.PetRepository;
import br.com.metaway.petshop.repositories.RaceRepository;


@RestController
@RequestMapping("/pets")
public class PetController {
	
	@Autowired
	private PetRepository repository;
	
	@Autowired
	private RaceRepository races;
	
	@Autowired
	private ClientRepository clients;
	
	@PostMapping
    public ResponseEntity<Map<String, Object>>  create(@RequestBody Pet pet) {
		
		// Check the Race in Database
		BigInteger raceId = pet.getRace().getId();
		Optional<Race> optionalRace = races.findById(raceId);
		
		if (optionalRace.isEmpty()) {
	        return ResponseEntity.badRequest().build();
	    }
		
		// Check the Client in Database
		String cpf = pet.getClient().getCpf();
	    Optional<Client> optionalClient = clients.findByCpf(cpf);

	    if (optionalClient.isEmpty()) {
	        return ResponseEntity.badRequest().build();
	    }
	    
	    // Get the Data
	    Client client = optionalClient.get();
	    Race race = optionalRace.get();
	    
	    // Build the Record Data
	    Pet newPet = pet.builder()
	    		.name(pet.getName())
	    		.client(client)
	    		.birthDate(pet.getBirthDate())
	    		.race(race)
	    		.build();
		
	    // Save
        Pet savedPet = repository.save(newPet);
        
        // Make the Return Interface
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("id", savedPet.getName());
        responseBody.put("client_cpf", savedPet.getClient().getCpf());
        responseBody.put("birth_date", savedPet.getBirthDate());
        responseBody.put("race_id", savedPet.getRace().getId());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

	@GetMapping
	public ResponseEntity<List<Map<String, Object>>> list() {
		List<Pet> pets = repository.findAll();
		
		List<Map<String, Object>> responseBody = pets.stream().map(pet -> {
			Map<String, Object> petData = new LinkedHashMap<>();
			petData.put("id", pet.getId());
			petData.put("name", pet.getName());
			petData.put("client_cpf", pet.getClient().getCpf());
			petData.put("birth_date", pet.getBirthDate());
			petData.put("race_id", pet.getRace().getId());
			return petData;
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok(responseBody);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getById(@PathVariable BigInteger id) {
		Optional<Pet> optionalPet = repository.findById(id);
		
		if (optionalPet.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Pet pet = optionalPet.get();
		
		// Build the response body
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("id", pet.getId());
		responseBody.put("name", pet.getName());
		responseBody.put("client_cpf", pet.getClient().getCpf());
		responseBody.put("birth_date", pet.getBirthDate());
		responseBody.put("race_id", pet.getRace().getId());
		
		return ResponseEntity.ok(responseBody);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> edit(@PathVariable BigInteger id, @RequestBody Pet pet) {
		Optional<Pet> optionalPet = repository.findById(id);
		if (optionalPet.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		// Check the Race in Database
		BigInteger raceId = pet.getRace().getId();
		Optional<Race> optionalRace = races.findById(raceId);
		if (optionalRace.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		// Check the Client in Database
		String cpf = pet.getClient().getCpf();
		Optional<Client> optionalClient = clients.findByCpf(cpf);
		if (optionalClient.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		// Update the pet data
		Pet existingPet = optionalPet.get();
		existingPet.setName(pet.getName());
		existingPet.setClient(optionalClient.get());
		existingPet.setBirthDate(pet.getBirthDate());
		existingPet.setRace(optionalRace.get());

		// Save the updated pet
		Pet savedPet = repository.save(existingPet);

		// Build the response body
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("id", savedPet.getId());
		responseBody.put("name", savedPet.getName());
		responseBody.put("client_cpf", savedPet.getClient().getCpf());
		responseBody.put("birth_date", savedPet.getBirthDate());
		responseBody.put("race_id", savedPet.getRace().getId());

		return ResponseEntity.ok(responseBody);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePet(@PathVariable BigInteger id) {
		Optional<Pet> pet = repository.findById(id);
		
		if (pet.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
