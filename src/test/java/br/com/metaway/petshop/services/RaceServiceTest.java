package br.com.metaway.petshop.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.repositories.RaceRepository;

@ExtendWith(MockitoExtension.class)
class RaceServiceTest {
	
	@Mock
	private RaceRepository repository;
	
	@InjectMocks
	private RaceService service;

	@Test
	void testCreate() {
	    Race race = new Race();
	    race.setId(BigInteger.valueOf(1));
	    race.setDescription("Test Race");
	    
	    when(repository.save(race)).thenReturn(race);
	    
	    Race savedRace = service.create(race);
	    
	    assertNotNull(savedRace);
	    assertEquals(race, savedRace);
	    
	    verify(repository).save(race);
	}
	
	@Test
	public void testGetAll() {
	    Race race1 = Race.builder().description("Race 1").build();
	    Race race2 = Race.builder().description("Race 2").build();
	    
	    when(repository.findAll()).thenReturn(Arrays.asList(race1, race2));

	    List<Race> races = service.getAll();
	    
	    assertNotNull(races);
	    assertEquals(2, races.size());
	    assertEquals(race1.getDescription(), races.get(0).getDescription());
	    assertEquals(race2.getDescription(), races.get(1).getDescription());
	    
	    verify(repository).findAll();
	}
	
	@Test
	public void testGetById() {
	    BigInteger raceId = BigInteger.valueOf(1);
	    Race race = Race.builder().description("Test Race").build();
	    race.setId(raceId);

	    when(repository.findById(raceId)).thenReturn(Optional.of(race));

	    Race foundRaceOptional = service.getById(raceId);
	    assertThat(foundRaceOptional.getDescription()).isEqualTo("Test Race");

	    Race foundRace = foundRaceOptional;
	    assertEquals(raceId, foundRace.getId());
	    assertEquals(race.getDescription(), foundRace.getDescription());

	    verify(repository).findById(raceId);
	}
	
	@Test
	public void testEdit() {
	    BigInteger raceId = BigInteger.valueOf(1);
	    Race race = Race.builder().description("Test Race").build();
	    race.setId(raceId);

	    Race updatedRace = Race.builder().description("Updated Test Race").build();

	    when(repository.findById(raceId)).thenReturn(Optional.of(race));
	    when(repository.save(race)).thenReturn(updatedRace);

	    Race editedRace = service.edit(raceId, updatedRace);

	    assertNotNull(editedRace);
	    assertEquals(updatedRace.getDescription(), editedRace.getDescription());

	    verify(repository).findById(raceId);
	    verify(repository).save(race);
	}
	
	@Test
	public void testDelete() {
	    BigInteger raceId = BigInteger.valueOf(1);
	    Race race = Race.builder().description("Test Race").build();
	    race.setId(raceId);

	    when(repository.findById(raceId)).thenReturn(Optional.of(race));

	    service.delete(raceId);

	    verify(repository).delete(race);
	    verify(repository).findById(raceId);
	}
	
}