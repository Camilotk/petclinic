package br.com.metaway.petshop.services;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.repositories.RaceRepository;

@Service
public class RaceService {

    private final RaceRepository repository;

    public RaceService(@Autowired RaceRepository repository) {
        this.repository = repository;
    }    
 
    @CachePut(value = "race")
    @CacheEvict(value = "allRaces", allEntries = true)
    public Race create(Race race) {
        return repository.save(race);
    }

    @Cacheable(value = "allRaces") 
    public Page<Race> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Cacheable(value = "raceById", key = "#id")
	public Race getById(BigInteger id) {
		Optional<Race> race = repository.findById(id);

		if (race.isPresent()) {
			return race.get();
		} else {
			return null;
		}
	}

    @CachePut(value = "race", key = "#id")
    public Race edit(BigInteger id, Race race) {
        Optional<Race> optionalRace = repository.findById(id);

        if (optionalRace.isPresent()) {
            Race existingRace = optionalRace.get();
            existingRace.setDescription(race.getDescription());
            return repository.save(existingRace);
        } else {
            return null;
        }
    }

    @CacheEvict(value = {"race", "allRaces"}, key = "#id", allEntries = true)
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