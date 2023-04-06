package br.com.metaway.petshop.controllers;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<Map<String, Object>>  createPet(@RequestBody Pet pet) {
		
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

}
