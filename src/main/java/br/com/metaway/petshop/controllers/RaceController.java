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

}