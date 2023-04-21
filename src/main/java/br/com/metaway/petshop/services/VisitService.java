package br.com.metaway.petshop.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.metaway.petshop.models.Pet;
import br.com.metaway.petshop.models.Visit;
import br.com.metaway.petshop.repositories.PetRepository;
import br.com.metaway.petshop.repositories.VisitRepository;
import br.com.metaway.petshop.repositories.dtos.VisitData;

@Service
public class VisitService {
	@Autowired
	private VisitRepository repository;
	
	@Autowired
	private PetRepository pets;
	
	@CachePut(value = "visit", key = "#visit.id")
	public VisitData create(Visit visit) {
		// Check if the Pet exists and get the data
		Optional<Pet> optionalPet = pets.findById(visit.getPet().getId());
		
		if (optionalPet.isEmpty()) {
            return null;
        }
		
		Pet pet = optionalPet.get();
		
		// Build a Visit
		Visit newVisit = visit.builder()
                .pet(pet)
                .date(visit.getDate())
                .description(visit.getDescription())
                .value(visit.getValue())
                .currency("BRL")
                .build();
		
		// Save
        Visit savedVisit = repository.save(newVisit);
        
		return new VisitData(savedVisit.getId(), 
				             savedVisit.getPet().getId(), 
				             savedVisit.getDate(),
				             savedVisit.getDescription(),
				             savedVisit.getValue(),
				             savedVisit.getCurrency());
	}
	
	@Cacheable(value = "allVisits") 
	public List<VisitData> getAll() {
		List<Visit> visits = repository.findAll();
		List<VisitData> visitsData = visits.stream()
											.map(visit -> new VisitData(visit.getId(),
				                                                        visit.getPet().getId(),
		                                                                visit.getDate(),
		                                                                visit.getDescription(),
		                                                                visit.getValue(),
		                                                                visit.getCurrency())).toList();
		return visitsData;
	}
	
	@Cacheable(value = "visitById", key = "#id")
	public VisitData getById(BigInteger id) {
		Optional<Visit> optionalVisit = repository.findById(id);
		
		if (optionalVisit.isEmpty()) {
			return null;
		}
		
		Visit visit = optionalVisit.get();
		
		return new VisitData(visit.getId(), 
				             visit.getPet().getId(),
				             visit.getDate(),
				             visit.getDescription(),
				             visit.getValue(),
				             visit.getCurrency());
	}
	
	@CachePut(value = "visit", key = "#id")
	public VisitData edit(BigInteger id, Visit visit) {
		// Check if the Visit exists in DB, if it does retrieve if not return null.
		Optional<Visit> optionalVisit = repository.findById(id);
		
		if (optionalVisit.isEmpty()) {
			return null;
		}
		
		Visit existingVisit = optionalVisit.get();
		
		// Check if the sent Pet data is valid
		Optional<Pet> optionalPet = pets.findById(visit.getPet().getId());
		
		if (optionalPet.isEmpty()) {
			return new VisitData(null, null, null, null, null, null);
		}
		
		Pet pet = optionalPet.get();
		
		// Update the existing Visit
		Visit editedVisit = Visit.builder()
				                 .pet(pet)
				                 .date(visit.getDate())
				                 .description(visit.getDescription())
				                 .value(visit.getValue())
				                 .currency(visit.getCurrency())
				                 .build();
		
		editedVisit.setId(id);
		
		// Save
		Visit savedVisit = repository.save(editedVisit);
		
		return new VisitData(savedVisit.getId(),
							 savedVisit.getPet().getId(),
							 savedVisit.getDate(),
							 savedVisit.getDescription(),
							 savedVisit.getValue(),
							 savedVisit.getCurrency());
	}
	
	@CacheEvict(value = "visit", key = "#id")
	public Visit delete(BigInteger id) {
		Optional<Visit> visit = repository.findById(id);
		
		if (visit.isEmpty()) {
			return null;
		}
		
		Visit deletedVisit = visit.get();
		repository.delete(deletedVisit);
		
		return deletedVisit;
	}
}
