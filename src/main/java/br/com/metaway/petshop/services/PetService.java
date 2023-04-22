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

import br.com.metaway.petshop.models.Client;
import br.com.metaway.petshop.models.Pet;
import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.repositories.ClientRepository;
import br.com.metaway.petshop.repositories.PetRepository;
import br.com.metaway.petshop.repositories.RaceRepository;
import br.com.metaway.petshop.repositories.dtos.PetData;

@Service
public class PetService {

	@Autowired
	private PetRepository repository;
	
	@Autowired
	private RaceRepository races;
	
	@Autowired
	private ClientRepository clients;
	
	@CachePut(value = "pet", key = "#pet.id")
	@CacheEvict(value = "allPets", allEntries = true)
	public PetData create(Pet pet) {
		// Check if the Race is valid
		BigInteger raceId = pet.getRace().getId();
		Optional<Race> optionalRace = races.findById(raceId);
		
		if (optionalRace.isEmpty()) {
	        return null;
	    }
		
		// Check the Client in Database
		String cpf = pet.getClient().getCpf();
	    Optional<Client> optionalClient = clients.findByCpf(cpf);

	    if (optionalClient.isEmpty()) {
	        return null;
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

		return new PetData(newPet.getId(), 
						   newPet.getName(), 
						   newPet.getClient().getCpf(), 
						   newPet.getBirthDate(),
						   newPet.getRace().getId());
	}
	
	@Cacheable(value = "allPets") 
	public Page<PetData> getAll(Pageable pageable) {
	    Page<Pet> petsPage = repository.findAll(pageable);
	    return petsPage.map(pet -> new PetData(pet.getId(), 
	                                           pet.getName(), 
	                                           pet.getClient().getCpf(), 
	                                           pet.getBirthDate(), 
	                                           pet.getRace().getId()));
	}
	
	@Cacheable(value = "petById", key = "#id")
	public PetData getById(BigInteger id) {
		Optional<Pet> optionalPet = repository.findById(id);
		
		if (optionalPet.isEmpty()) {
			return null;
		}
		
		Pet pet = optionalPet.get();		
		
		return new PetData(pet.getId(),
						   pet.getName(),
						   pet.getClient().getCpf(),
						   pet.getBirthDate(),
						   pet.getRace().getId());
	}
	
	@CachePut(value = "pet", key = "#id")
	public PetData edit(BigInteger id, Pet pet) {
		// Check if the Pet already exists by ID
		Optional<Pet> optionalPet = repository.findById(id);
		
		if (optionalPet.isEmpty()) {
			return null;
		}
		
		
		// Check if the race in the Pet data exists in Database
		BigInteger raceId = pet.getRace().getId();
		Optional<Race> optionalRace = races.findById(raceId);
		if (optionalRace.isEmpty()) {
			return null;
		}
		
		// Check if the passed Client exists in the Database
		String cpf = pet.getClient().getCpf();
		Optional<Client> optionalClient = clients.findByCpf(cpf);
		if (optionalClient.isEmpty()) {
			return null;
		}
		
		// Update the pet data
		Pet existingPet = optionalPet.get();
		existingPet.setName(pet.getName());
		existingPet.setClient(optionalClient.get());
		existingPet.setBirthDate(pet.getBirthDate());
		existingPet.setRace(optionalRace.get());
		
		// Save the updated pet
		Pet editedPet = repository.save(existingPet);
		
		return new PetData(editedPet.getId(), 
				 		   editedPet.getName(),
				 		   editedPet.getClient().getCpf(),
				 		   editedPet.getBirthDate(),
				 		   editedPet.getRace().getId());
	}
	
	@CacheEvict(value = {"pet", "allPets"}, key = "#id", allEntries = true)
	public Pet delete (BigInteger id) {
		Optional<Pet> pet = repository.findById(id);

		if (pet.isEmpty()) {
			return null;
		}
		
		Pet deletedPet = pet.get();
		repository.deleteById(id);
		
		return deletedPet;
	}
}
