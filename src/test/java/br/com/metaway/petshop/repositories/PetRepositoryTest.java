package br.com.metaway.petshop.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.metaway.petshop.models.Pet;

@DataJpaTest
class PetRepositoryTest {

    @Autowired
    private PetRepository repository;
	
	Pet somePet;
	Pet anotherPet;
	
	@BeforeEach
	void setup() {
		somePet = Pet.builder().name("Galileu").birthDate(LocalDate.now()).build();
		anotherPet = Pet.builder().name("Max").birthDate(LocalDate.now()).build();
	}
	
	@AfterEach
	void clean() {
		repository.deleteAll();
	}
	
	@Test
	@DisplayName("it should save pets and retrieve all pets")
    void testFindAll() {	
		repository.save(somePet);
		repository.save(anotherPet);
	
        List<Pet> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
	
	@Test
	@DisplayName("it should save a pet and retrieve it by id")
	void testFindById() {
	    repository.save(anotherPet);
	    assertThat(repository.findAll()).hasSize(1);

	    Pet savedPet = repository.findAll().get(0);
	    Optional<Pet> theOptionalPet = repository.findById(savedPet.getId());

	    assertThat(theOptionalPet).isPresent().get().isEqualTo(savedPet);
	}
	
	@Test
	@DisplayName("it should save a pet and then get all pets")
	void testCreate() {
		repository.save(somePet);
		
		List<Pet> pets = repository.findAll();
		
		assertThat(pets.size()).isEqualTo(1);
	}
	
	@Test
	@DisplayName("it should save then delete a pet")
	public void testDelete() {
	    repository.save(somePet);
	    repository.delete(somePet);

	    assertThat(repository.findAll()).doesNotContain(somePet);
	}
	
	@Test
	@DisplayName("it should save a pet and then edit it")
	public void testUpdatePet() {
	    repository.save(somePet);

	    Optional<Pet> theOptionalPet = repository.findById(BigInteger.ONE);
	    Pet thePet = theOptionalPet.get();
	    

	    thePet.setName("Carlos Manoel");
	    repository.save(thePet);
	    
	    Optional<Pet> theOptionalEditedPet = repository.findById(BigInteger.ONE);
	    Pet theEditedPet = theOptionalEditedPet.get();

	    assertThat(theEditedPet.getName()).isEqualTo("Carlos Manoel");
	}

}
