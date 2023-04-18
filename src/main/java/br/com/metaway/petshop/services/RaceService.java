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

    private final RaceRepository repository;

    public RaceService(@Autowired RaceRepository repository) {
        this.repository = repository;
    }

    public Race create(Race race) {
        return repository.save(race);
    }

    public List<Race> getAll() {
        return repository.findAll();
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
            Race existingRace = optionalRace.get();
            existingRace.setDescription(race.getDescription());
            return repository.save(existingRace);
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