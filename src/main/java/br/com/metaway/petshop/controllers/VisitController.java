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

import br.com.metaway.petshop.models.Pet;
import br.com.metaway.petshop.models.Visit;
import br.com.metaway.petshop.repositories.PetRepository;
import br.com.metaway.petshop.repositories.VisitRepository;


@RestController
@RequestMapping("/visits")
public class VisitController {
	@Autowired
	private VisitRepository repository;
	
	@Autowired
	private PetRepository pets;
	
	@PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Visit visit) {

        // Check if the pet exists
        BigInteger petId = visit.getPet().getId();
        Optional<Pet> optionalPet = pets.findById(petId);

        if (optionalPet.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Build the record data
        Pet pet = optionalPet.get();
        Visit newVisit = visit.builder()
                .pet(pet)
                .date(visit.getDate())
                .description(visit.getDescription())
                .build();

        // Save
        Visit savedVisit = repository.save(newVisit);

        // Build the response body
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("id", savedVisit.getId());
        responseBody.put("pet_id", savedVisit.getPet().getId());
        responseBody.put("date", savedVisit.getDate());
        responseBody.put("description", savedVisit.getDescription());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }
}
