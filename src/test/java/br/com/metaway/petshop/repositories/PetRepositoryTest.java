package br.com.metaway.petshop.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.metaway.petshop.models.Pet;

@DataJpaTest
class PetRepositoryTest {
	
	@Autowired
	private PetRepository repository;
	
	Pet somePet;
	
	
	@BeforeEach
	void setup() {
		somePet = Pet.builder().name("Galileu").birthDate(LocalDate.now()).build();
	}
	
	@Test
	void test() {
		repository.save(somePet);
		
		List<Pet> pets = repository.findAll();
		
		assertThat(pets.size()).isEqualTo(1);
	}

}
