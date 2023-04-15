package br.com.metaway.petshop.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.repositories.RaceRepository;

@Service
public class RaceService {
	
	@Autowired
	private RaceRepository repository;
	
	public Race create(Race race) {
		repository.save(race);
		return race;
	}
	
	public List<Race> getAll() {
		List<Race> races = repository.findAll();
		return races;
	}
	
	public Race getById(BigInteger id) {
		Optional<Race> race = repository.findById(id);
		
		if (race.isPresent()) {
			return race.get();
		} else {
			return null;
		}
	}
	
	public Race edit(BigInteger id, Race race) {
		Optional<Race> optionalRace = repository.findById(id);
		
		if (optionalRace.isPresent()) {
			Race exisitingRace = optionalRace.get();
			
			exisitingRace.setDescription(race.getDescription());
			
			repository.save(exisitingRace);
			
			return exisitingRace;
		} else {
			return null;
		}
	}
	
	public Race delete(BigInteger id) {
		Optional<Race> optionalRace = repository.findById(id);
		
		if (optionalRace.isPresent()) {
			Race deletedRace = optionalRace.get();
			repository.delete(deletedRace);
			return deletedRace;
		} else {
			return null;
		}
	}
}
