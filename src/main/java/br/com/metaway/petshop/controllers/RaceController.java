package br.com.metaway.petshop.controllers;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.repositories.RaceRepository;

@RestController
@RequestMapping("/races")
public class RaceController {
	
	@Autowired
	private RaceRepository repository;
	
	@PostMapping
	public ResponseEntity<Race> create(@RequestBody Race race) {
		Race newRace = this.repository.save(race);
		return ResponseEntity.status(HttpStatus.CREATED).body(newRace);
	}
	
	@GetMapping
	public ResponseEntity<List<Race>> getAll() {
		List<Race> races = this.repository.findAll();
		return ResponseEntity.ok(races);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Race> getById(@PathVariable BigInteger id) {
	    Optional<Race> race = this.repository.findById(id);
	    if (race.isPresent()) {
	        return ResponseEntity.ok(race.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Race> update(@PathVariable BigInteger id, @RequestBody Race updatedRace) {
	    Optional<Race> race = this.repository.findById(id);
	    if (race.isPresent()) {
	        Race existingRace = race.get();
	        existingRace.setDescription(updatedRace.getDescription());
	        Race savedRace = this.repository.save(existingRace);
	        return ResponseEntity.ok(savedRace);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
}