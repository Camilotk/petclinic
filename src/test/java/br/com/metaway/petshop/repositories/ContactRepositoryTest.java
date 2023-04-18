package br.com.metaway.petshop.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.metaway.petshop.models.Client;
import br.com.metaway.petshop.models.Contact;
import br.com.metaway.petshop.models.ContactType;

@DataJpaTest
class ContactRepositoryTest {
    
    @Autowired
    private ContactRepository repository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    private Client client;
    private Contact contact;
    
    @BeforeEach
    void setUp() {
        client = Client.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .cpf("499.865.210-92")
                .registrationDate(new Date())
                .password("asdf")
                .build();
        
        clientRepository.save(client);
        
        contact = Contact.builder()
                .client(client)
                .type(ContactType.EMAIL)
                .value(BigInteger.valueOf(1000))
                .currency("USD")
                .build();
    }
    
    @AfterEach
    void tearDown() {
        repository.deleteAll();
        clientRepository.deleteAll();
    }
    
    @Test
    @DisplayName("it should find all contacts")
    void testFindAll() {
        // given
        repository.save(contact);

        // when
        List<Contact> contacts = repository.findAll();

        // then
        assertThat(contacts).hasSize(1);
        assertThat(contacts.get(0)).isEqualTo(contact);
    }
    
    @Test
    @DisplayName("it should find a contact by id")
    void testFindById() {
        repository.save(contact);
        Contact savedContact = repository.findAll().get(0);
        Optional<Contact> foundContact = repository.findById(savedContact.getId());
        assertThat(foundContact).isPresent().contains(savedContact);
    }
    
    @Test
    @DisplayName("it should save a new contact")
    void testSave() {
        repository.save(contact);
        assertThat(repository.findAll()).hasSize(1).contains(contact);
    }
    
    @Test
    @DisplayName("it should delete a contact")
    void testDelete() {
        repository.save(contact);
        repository.delete(contact);
        assertThat(repository.findAll()).isEmpty();
    }
    
    @Test
    @DisplayName("it should update a contact")
    void testUpdate() {
        repository.save(contact);
        Contact savedContact = repository.findAll().get(0);
        savedContact.setCurrency("BRL");
        repository.save(savedContact);
        assertThat(repository.findById(savedContact.getId()))
                .isPresent()
                .get()
                .extracting(Contact::getCurrency)
                .isEqualTo("BRL");
    }
    
}