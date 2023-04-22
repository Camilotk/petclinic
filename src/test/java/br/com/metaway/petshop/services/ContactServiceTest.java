package br.com.metaway.petshop.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import br.com.metaway.petshop.models.Client;
import br.com.metaway.petshop.models.Contact;
import br.com.metaway.petshop.models.ContactType;
import br.com.metaway.petshop.models.Role;
import br.com.metaway.petshop.repositories.ClientRepository;
import br.com.metaway.petshop.repositories.ContactRepository;
import br.com.metaway.petshop.repositories.dtos.ContactData;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository repository;

    @Mock
    private ClientRepository clients;

    @InjectMocks
    private ContactService service;

    @Test
    void testCreateContact() {        
        Client theUser = Client.builder()
                               .cpf("941.173.420-02")
                               .firstname("José")
                               .lastname("Juca")
                               .email("jose.juca@gmail.com")
                               .role(Role.USER)
                               .build();
        
        Mockito.when(clients.findByCpf(theUser.getCpf())).thenReturn(Optional.of(theUser));
        
        Contact theContact = Contact.builder()
                                    .client(theUser)
                                    .type(ContactType.EMAIL)
                                    .value(BigInteger.valueOf(1234))
                                    .currency("BRL")
                                    .build();

        Contact savedContact = Contact.builder()
                                       .client(theUser)
                                       .type(ContactType.EMAIL)
                                       .value(BigInteger.valueOf(1234))
                                       .currency("BRL")
                                       .build();
        
        savedContact.setId(BigInteger.ONE);
        
        Mockito.when(repository.save(theContact)).thenReturn(savedContact);
        
        ContactData result = service.create(theContact);
        
        assertThat(result).isNotNull();
    }
    
    @Test
    void testEditContact() {
        // Create the existing contact
        Client theUser = Client.builder()
                               .cpf("941.173.420-02")
                               .firstname("José")
                               .lastname("Juca")
                               .email("jose.juca@gmail.com")
                               .role(Role.USER)
                               .build();
        
        Contact existingContact = Contact.builder()
                                         .client(theUser)
                                         .type(ContactType.EMAIL)
                                         .value(BigInteger.valueOf(1234))
                                         .currency("BRL")
                                         .build();
        
        existingContact.setId(BigInteger.ONE);
        
        // Define the updated contact
        Contact updatedContact = Contact.builder()
                                         .client(theUser)
                                         .type(ContactType.CELLPHONE)
                                         .value(BigInteger.valueOf(5678))
                                         .currency("USD")
                                         .build();
        
        updatedContact.setId(BigInteger.ONE);
        
        // Set up the mock behavior
        Mockito.when(clients.findByCpf(theUser.getCpf())).thenReturn(Optional.of(theUser));
        Mockito.when(repository.save(updatedContact)).thenReturn(updatedContact);
        
        // Call the edit method
        ContactData result = service.edit(BigInteger.ONE, updatedContact);
        
        // Check the result
        assertThat(result).isNotNull();
        assertThat(result.type()).isEqualTo(ContactType.CELLPHONE);
        assertThat(result.value()).isEqualTo(BigInteger.valueOf(5678));
        assertThat(result.currency()).isEqualTo("USD");
    }
    
    @Test
    void testDeleteContact() {
        // Create the existing contact
        Client theUser = Client.builder()
                               .cpf("941.173.420-02")
                               .firstname("José")
                               .lastname("Juca")
                               .email("jose.juca@gmail.com")
                               .role(Role.USER)
                               .build();
        
        Contact existingContact = Contact.builder()
                                         .client(theUser)
                                         .type(ContactType.EMAIL)
                                         .value(BigInteger.valueOf(1234))
                                         .currency("BRL")
                                         .build();
        
        existingContact.setId(BigInteger.ONE);
        
        // Set up the mock behavior
        Mockito.when(repository.findById(existingContact.getId())).thenReturn(Optional.of(existingContact));
        
        // Call the delete method
        service.deleteById(existingContact.getId());
        
        // Verify that the repository's delete method was called with the correct argument
        Mockito.verify(repository).delete(existingContact);
    }
    
    @Test
    void testFindOne() {
        // Create a contact with a known ID
        Client theUser = Client.builder()
                               .cpf("941.173.420-02")
                               .firstname("José")
                               .lastname("Juca")
                               .email("jose.juca@gmail.com")
                               .role(Role.USER)
                               .build();
        
        Contact contact = Contact.builder()
                                 .client(theUser)
                                 .type(ContactType.EMAIL)
                                 .value(BigInteger.valueOf(1234))
                                 .currency("BRL")
                                 .build();
        
        BigInteger id = BigInteger.valueOf(123);
        contact.setId(id);
        
        // Set up the mock behavior
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(contact));
        
        // Call the findOne method
        ContactData result = service.getById(id);
        
        // Verify that the repository's findById method was called with the correct argument
        Mockito.verify(repository).findById(id);
        
        // Assert that the result is not null and contains the expected contact information
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.type()).isEqualTo(ContactType.EMAIL);
        assertThat(result.value()).isEqualTo(BigInteger.valueOf(1234));
        assertThat(result.currency()).isEqualTo("BRL");
    }
    
    @Test
    void testGetAll() {
        // Create some sample contacts
        Client user1 = Client.builder()
                             .cpf("111.111.111-11")
                             .firstname("Alice")
                             .lastname("Smith")
                             .email("alice.smith@example.com")
                             .role(Role.USER)
                             .build();
        Contact contact1 = Contact.builder()
                                  .client(user1)
                                  .type(ContactType.EMAIL)
                                  .value(BigInteger.valueOf(1234))
                                  .currency("BRL")
                                  .build();
        contact1.setId(BigInteger.ONE);
        
        Client user2 = Client.builder()
                             .cpf("222.222.222-22")
                             .firstname("Bob")
                             .lastname("Jones")
                             .email("bob.jones@example.com")
                             .role(Role.USER)
                             .build();
        Contact contact2 = Contact.builder()
                                  .client(user2)
                                  .type(ContactType.CELLPHONE)
                                  .value(BigInteger.valueOf(5678))
                                  .currency("USD")
                                  .build();
        contact2.setId(BigInteger.TWO);
        
        // Set up the mock behavior
        PageRequest pageRequest = PageRequest.of(0, 1); // Request the first page with one element
        Page<Contact> page = new PageImpl<>(List.of(contact1), pageRequest, 2); // Return a page with one element and a total of two elements
        Mockito.when(repository.findAll(pageRequest)).thenReturn(page);
        
        // Call the getAll method
        Page<ContactData> result = service.getAll(pageRequest);
        
        // Check the result
        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(new ContactData(BigInteger.ONE, user1.getCpf(), ContactType.EMAIL, BigInteger.valueOf(1234), "BRL"));
    }
}