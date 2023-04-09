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
                .value(visit.getValue())
                .currency("BRL")
                .build();

        // Save
        Visit savedVisit = repository.save(newVisit);

        // Build the response body
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("id", savedVisit.getId());
        responseBody.put("pet_id", savedVisit.getPet().getId());
        responseBody.put("date", savedVisit.getDate());
        responseBody.put("description", savedVisit.getDescription());
        responseBody.put("value", savedVisit.getValue());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> list(@PathVariable BigInteger id) {
        Optional<Visit> optionalVisit = repository.findById(id);
        if (optionalVisit.isPresent()) {
            Visit visit = optionalVisit.get();
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("id", visit.getId());
            result.put("date", visit.getDate());
            result.put("description", visit.getDescription());
            result.put("pet", visit.getPet().getId());
            result.put("value", visit.getValue());
            result.put("currency", visit.getCurrency());
            result.put("client", visit.getPet().getClient().getCpf());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	@GetMapping
    public ResponseEntity<List<Map<String, Object>>> listAll() {
        List<Visit> visits = repository.findAll();

        List<Map<String, Object>> result = visits.stream().map(visit -> {
            Map<String, Object> visitMap = new LinkedHashMap<>();
            visitMap.put("id", visit.getId());
            visitMap.put("date", visit.getDate());
            visitMap.put("description", visit.getDescription());
            visitMap.put("pet", visit.getPet().getId());
            visitMap.put("value", visit.getValue());
            visitMap.put("currency", visit.getCurrency());
            visitMap.put("client", visit.getPet().getClient().getCpf());
            return visitMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable BigInteger id, @RequestBody Visit visit) {
        Optional<Visit> optionalVisit = repository.findById(id);
        if (optionalVisit.isPresent()) {
            Visit existingVisit = optionalVisit.get();

            // Check if the pet exists
            BigInteger petId = visit.getPet().getId();
            Optional<Pet> optionalPet = pets.findById(petId);

            if (optionalPet.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Update the visit data
            Pet pet = optionalPet.get();
            existingVisit.setPet(pet);
            existingVisit.setDate(visit.getDate());
            existingVisit.setDescription(visit.getDescription());
            existingVisit.setValue(visit.getValue());
//            existingVisit.setCurrency("BRL");

            // Save
            Visit savedVisit = repository.save(existingVisit);

            // Build the response body
            Map<String, Object> responseBody = new LinkedHashMap<>();
            responseBody.put("id", savedVisit.getId());
            responseBody.put("pet_id", savedVisit.getPet().getId());
            responseBody.put("date", savedVisit.getDate());
            responseBody.put("description", savedVisit.getDescription());
            responseBody.put("value", savedVisit.getValue());

            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable BigInteger id) {
		Optional<Visit> visit = repository.findById(id);
		
		if (visit.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
