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

import br.com.metaway.petshop.models.Address;
import br.com.metaway.petshop.repositories.AddressRepository;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
	
	@Mock
	private AddressRepository repository;
	
	@InjectMocks
	private AddressService service;

	@Test
	void testCreate() {
	    Address address = new Address();
	    address.setId(BigInteger.valueOf(1));
	    address.setCity("Test City");
	    
	    when(repository.save(address)).thenReturn(address);
	    
	    Address savedAddress = service.create(address);
	    
	    assertNotNull(savedAddress);
	    assertEquals(address, savedAddress);
	    
	    verify(repository).save(address);
	}
	
	@Test
	public void testGetAll() {
	    Address address1 = Address.builder().city("City 1").build();
	    Address address2 = Address.builder().city("City 2").build();
	    
	    when(repository.findAll()).thenReturn(Arrays.asList(address1, address2));

	    List<Address> addresses = service.getAll();
	    
	    assertNotNull(addresses);
	    assertEquals(2, addresses.size());
	    assertEquals(address1.getCity(), addresses.get(0).getCity());
	    assertEquals(address2.getCity(), addresses.get(1).getCity());
	    
	    verify(repository).findAll();
	}
	
	@Test
	public void testGetById() {
	    BigInteger addressId = BigInteger.valueOf(1);
	    Address address = Address.builder().city("Test City").build();
	    address.setId(addressId);

	    when(repository.findById(addressId)).thenReturn(Optional.of(address));

	    Address foundAddressOptional = service.getById(addressId);
	    assertThat(foundAddressOptional.getCity()).isEqualTo("Test City");

	    Address foundAddress = foundAddressOptional;
	    assertEquals(addressId, foundAddress.getId());
	    assertEquals(address.getCity(), foundAddress.getCity());

	    verify(repository).findById(addressId);
	}
	
	@Test
	public void testEdit() {
	    BigInteger addressId = BigInteger.valueOf(1);
	    Address address = Address.builder().city("Test City").build();
	    address.setId(addressId);

	    Address updatedAddress = Address.builder().city("Updated Test City").build();

	    when(repository.findById(addressId)).thenReturn(Optional.of(address));
	    when(repository.save(address)).thenReturn(updatedAddress);

	    Address editedAddress = service.edit(addressId, updatedAddress);

	    assertNotNull(editedAddress);
	    assertEquals(updatedAddress.getCity(), editedAddress.getCity());

	    verify(repository).findById(addressId);
	    verify(repository).save(address);
	}
	
	@Test
	public void testDelete() {
	    BigInteger addressId = BigInteger.valueOf(1);
	    Address address = Address.builder().city("Test City").build();
	    address.setId(addressId);

	    when(repository.findById(addressId)).thenReturn(Optional.of(address));

	    service.delete(addressId);

	    verify(repository).delete(address);
	    verify(repository).findById(addressId);
	}
	
}