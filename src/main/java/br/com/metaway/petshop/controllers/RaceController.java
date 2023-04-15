package br.com.metaway.petshop.controllers;

import java.math.BigInteger;
import java.util.List;

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

import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.services.RaceService;

@RestController
@RequestMapping("/races")
public class RaceController {
	
	@Autowired
	private RaceService service;
	
	@PostMapping
	public ResponseEntity<Race> store(@RequestBody Race race) {
		Race newRace = service.create(race);
		return ResponseEntity.status(HttpStatus.CREATED).body(newRace);
	}
	
	@GetMapping
	public ResponseEntity<List<Race>> show() {
		List<Race> races = service.getAll();
		return ResponseEntity.ok(races);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Race> getById(@PathVariable BigInteger id) {
	    Race race = service.getById(id);
	    
	    if (race == null) {
	    	return ResponseEntity.notFound().build();
	    } else {
	    	return ResponseEntity.ok(race);
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Race> update(@PathVariable BigInteger id, @RequestBody Race race) {
	    Race updatedRace = service.edit(id, race);
	    
	    if (updatedRace == null) {
	    	return ResponseEntity.notFound().build();
	    }
	    
        return ResponseEntity.ok(updatedRace);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable BigInteger id) {
		Race deletedRace = service.delete(id);

	    if (deletedRace == null) {
	    	return ResponseEntity.notFound().build();
	    }
	    
        return ResponseEntity.noContent().build();
	}
	
}