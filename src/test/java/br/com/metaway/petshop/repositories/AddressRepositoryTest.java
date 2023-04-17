package br.com.metaway.petshop.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.metaway.petshop.models.Address;

@DataJpaTest
class AddressRepositoryTest {
	
	@Autowired
	private AddressRepository repository;
	
	Address someAddress;
	Address otherAddress;
	
	@BeforeEach
	void setup() {
		someAddress = Address.builder()
							 .addressLine("R. Saldanha Marinho, 570")
							 .avenue("Centro")
							 .city("Bento Gonçalves")
							 .state("RS")
							 .country("Brasil")
							 .zipCode("95700-082")
							 .additionalInformation("Sala 404")
							 .build();
		
		otherAddress = Address.builder()
							  .addressLine("R. Avelino Antônio de Souza, 1730")
							  .avenue("Nossa Sra. de Fátima")
							  .city("Caxias do Sul")
							  .state("RS")
							  .country("Brasil")
							  .zipCode("95043-700")
							  .additionalInformation("Enviar a Reitoria")
							  .build();
	}
	
	void clean() {
		repository.deleteAll();
	}

	@Test
	@DisplayName("it should save an address")
	void testSaveAddress() {
	    repository.save(someAddress);
	    assertThat(repository.findAll()).contains(someAddress);
	}

	@Test
	@DisplayName("it should retrieve all addresses")
	void testFindAllAddresses() {
	    repository.save(someAddress);
	    repository.save(otherAddress);
	    assertThat(repository.findAll()).containsExactly(someAddress, otherAddress);
	}

	@Test
	@DisplayName("it should retrieve an address by id")
	void testFindAddressById() {
	    repository.save(someAddress);
	    assertThat(repository.findById(someAddress.getId())).contains(someAddress);
	}

	@Test
	@DisplayName("it should delete an address by id")
	void testDeleteAddressById() {
	    repository.save(someAddress);
	    repository.deleteById(someAddress.getId());
	    assertThat(repository.findAll()).isEmpty();
	}

	@Test
	@DisplayName("it should update an address")
	void testUpdateAddress() {
	    repository.save(someAddress);
	    someAddress.setCity("Porto Alegre");
	    repository.save(someAddress);
	    assertThat(repository.findById(someAddress.getId())).contains(someAddress);
	}

}
