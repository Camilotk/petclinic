package br.com.metaway.petshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.metaway.petshop.models.Client;
import br.com.metaway.petshop.models.Pet;
import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.repositories.ClientRepository;
import br.com.metaway.petshop.repositories.PetRepository;
import br.com.metaway.petshop.repositories.RaceRepository;
import br.com.metaway.petshop.repositories.dtos.PetData;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {
	
	@Mock
	private PetRepository petRepository;
	
	@Mock
	private RaceRepository raceRepository;
	
	@Mock
	private ClientRepository clientRepository;
	
	@InjectMocks
	private PetService petService;

	@Test
	void testCreate() {
	    Pet pet = new Pet();
	    pet.setId(BigInteger.valueOf(1));
	    pet.setName("Test Pet");
	    pet.setBirthDate(LocalDate.of(2020, 1, 1));
	    
	    Race race = new Race();
	    race.setId(BigInteger.valueOf(1));
	    race.setDescription("Test Race");
	    
	    Client client = new Client();
	    client.setCpf("12345678901");
	    client.setFirstname("Test");
	    
	    when(clientRepository.findByCpf(client.getCpf())).thenReturn(Optional.of(client));
	    when(raceRepository.findById(race.getId())).thenReturn(Optional.of(race));
	    when(petRepository.save(pet)).thenReturn(pet);
	    
	    pet.setRace(race);
	    pet.setClient(client);
	    
	    PetData savedPet = petService.create(pet);
	    
	    assertNotNull(savedPet);
	    assertEquals(pet.getName(), savedPet.name());
	    assertEquals(pet.getBirthDate(), savedPet.birthDate());
	    assertEquals(pet.getRace().getId(), savedPet.raceId());
	    assertEquals(pet.getClient().getCpf(), savedPet.clientCpf());
	    
	    verify(petRepository).save(pet);
	    verify(raceRepository).findById(race.getId());
	    verify(clientRepository).findByCpf(client.getCpf());
	}
	
	@Test
	void testEdit() {
	    // Create a test pet
	    Pet pet = new Pet();
	    pet.setId(BigInteger.valueOf(1));
	    pet.setName("Test Pet");
	    pet.setBirthDate(LocalDate.of(2020, 1, 1));
	    Race race = new Race();
	    race.setId(BigInteger.valueOf(1));
	    race.setDescription("Test Race");
	    Client client = new Client();
	    client.setCpf("12345678901");
	    client.setFirstname("Test");
	    pet.setRace(race);
	    pet.setClient(client);

	    // Create a modified version of the test pet
	    Pet modifiedPet = new Pet();
	    modifiedPet.setId(pet.getId());
	    modifiedPet.setName("Modified Test Pet");
	    modifiedPet.setBirthDate(LocalDate.of(2022, 1, 1));
	    Race modifiedRace = new Race();
	    modifiedRace.setId(BigInteger.valueOf(2));
	    modifiedRace.setDescription("Modified Test Race");
	    Client modifiedClient = new Client();
	    modifiedClient.setCpf("10987654321");
	    modifiedClient.setFirstname("Modified Test");
	    modifiedPet.setRace(modifiedRace);
	    modifiedPet.setClient(modifiedClient);

	    // Set up mock repository responses
	    when(petRepository.findById(pet.getId())).thenReturn(Optional.of(pet));
	    when(raceRepository.findById(modifiedRace.getId())).thenReturn(Optional.of(modifiedRace));
	    when(clientRepository.findByCpf(modifiedClient.getCpf())).thenReturn(Optional.of(modifiedClient));
	    when(petRepository.save(modifiedPet)).thenReturn(modifiedPet);

	    // Call the service method
	    PetData editedPet = petService.edit(pet.getId(), modifiedPet);

	    // Verify the returned data
	    assertNotNull(editedPet);
	    assertEquals(modifiedPet.getName(), editedPet.name());
	    assertEquals(modifiedPet.getBirthDate(), editedPet.birthDate());
	    assertEquals(modifiedPet.getRace().getId(), editedPet.raceId());
	    assertEquals(modifiedPet.getClient().getCpf(), editedPet.clientCpf());

	    // Verify the repository interactions
	    verify(petRepository).findById(pet.getId());
	    verify(raceRepository).findById(modifiedRace.getId());
	    verify(clientRepository).findByCpf(modifiedClient.getCpf());
	    verify(petRepository).save(modifiedPet);
	}
	
	@Test
	void testDelete() {
	    // Create a test pet
	    Pet pet = new Pet();
	    pet.setId(BigInteger.valueOf(1));
	    pet.setName("Test Pet");
	    pet.setBirthDate(LocalDate.of(2020, 1, 1));
	    Race race = new Race();
	    race.setId(BigInteger.valueOf(1));
	    race.setDescription("Test Race");
	    Client client = new Client();
	    client.setCpf("12345678901");
	    client.setFirstname("Test");
	    pet.setRace(race);
	    pet.setClient(client);

	    // Set up mock repository response
	    when(petRepository.findById(pet.getId())).thenReturn(Optional.of(pet));

	    // Call the service method
	    petService.delete(pet.getId());

	    // Verify the repository interactions
	    verify(petRepository).findById(pet.getId());
	    verify(petRepository).deleteById(pet.getId());
	}
	
	@Test
	void testFindById() {
	    // Create a test pet
	    Pet pet = new Pet();
	    pet.setId(BigInteger.valueOf(1));
	    pet.setName("Test Pet");
	    pet.setBirthDate(LocalDate.of(2020, 1, 1));
	    Race race = new Race();
	    race.setId(BigInteger.valueOf(1));
	    race.setDescription("Test Race");
	    Client client = new Client();
	    client.setCpf("12345678901");
	    client.setFirstname("Test");
	    pet.setRace(race);
	    pet.setClient(client);

	    // Set up mock repository response
	    when(petRepository.findById(pet.getId())).thenReturn(Optional.of(pet));

	    // Call the service method
	    PetData foundPet = petService.getById(pet.getId());

	    // Verify the repository interactions
	    verify(petRepository).findById(pet.getId());

	    // Verify the returned PetData object
	    assertNotNull(foundPet);
	    assertEquals(pet.getName(), foundPet.name());
	    assertEquals(pet.getBirthDate(), foundPet.birthDate());
	    assertEquals(pet.getRace().getId(), foundPet.raceId());
	    assertEquals(pet.getClient().getCpf(), foundPet.clientCpf());
	}
	
	@Test
	void testFindAll() {
	    // Create some test pets
	    Pet pet1 = new Pet();
	    pet1.setId(BigInteger.valueOf(1));
	    pet1.setName("Test Pet 1");
	    pet1.setBirthDate(LocalDate.of(2020, 1, 1));
	    Race race1 = new Race();
	    race1.setId(BigInteger.valueOf(1));
	    race1.setDescription("Test Race 1");
	    Client client1 = new Client();
	    client1.setCpf("12345678901");
	    client1.setFirstname("Test 1");
	    pet1.setRace(race1);
	    pet1.setClient(client1);

	    Pet pet2 = new Pet();
	    pet2.setId(BigInteger.valueOf(2));
	    pet2.setName("Test Pet 2");
	    pet2.setBirthDate(LocalDate.of(2021, 1, 1));
	    Race race2 = new Race();
	    race2.setId(BigInteger.valueOf(2));
	    race2.setDescription("Test Race 2");
	    Client client2 = new Client();
	    client2.setCpf("23456789012");
	    client2.setFirstname("Test 2");
	    pet2.setRace(race2);
	    pet2.setClient(client2);

	    // Set up mock repository response
	    List<Pet> pets = Arrays.asList(pet1, pet2);
	    when(petRepository.findAll()).thenReturn(pets);

	    // Call the service method
	    List<PetData> foundPets = petService.getAll();

	    // Verify the repository interactions
	    verify(petRepository).findAll();

	    // Verify the returned PetData objects
	    assertNotNull(foundPets);
	    assertEquals(pets.size(), foundPets.size());
	    for (int i = 0; i < pets.size(); i++) {
	        Pet pet = pets.get(i);
	        PetData foundPet = foundPets.get(i);
	        assertEquals(pet.getName(), foundPet.name());
	        assertEquals(pet.getBirthDate(), foundPet.birthDate());
	        assertEquals(pet.getRace().getId(), foundPet.raceId());
	        assertEquals(pet.getClient().getCpf(), foundPet.clientCpf());
	    }
	}
}
