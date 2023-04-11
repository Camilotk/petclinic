package br.com.metaway.petshop.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import br.com.metaway.petshop.models.Address;
import br.com.metaway.petshop.repositories.AddressRepository;

public class AddressService {
	@Autowired
	private AddressRepository repository;
	
	public Address create(Address address) {
		repository.save(address);
		return address;
	}
	
	public List<Address> getAll() {
		List<Address> addresses = repository.findAll();
		return addresses;
	}

	public Address getById(BigInteger id) {
		Optional<Address> address = repository.findById(id);
		
	    if (address.isPresent()) {
	        return address.get();
	    } else {
	        return null;
	    }
	}
	
	public Address edit(BigInteger id, Address address) {
		Optional<Address> optionalAddress = repository.findById(id);
		
	    if (optionalAddress.isPresent()) {
	        
	    	Address existingAddress = optionalAddress.get();
	        
	        existingAddress.setAddressLine(address.getAddressLine());
	        existingAddress.setAvenue(address.getAvenue());
	        existingAddress.setCity(address.getCity());
	        existingAddress.setState(address.getState());
	        existingAddress.setCountry(address.getCountry());
	        existingAddress.setZipCode(address.getZipCode());
	        existingAddress.setAdditionalInformation(address.getAdditionalInformation());
	        
	        Address savedAddress = repository.save(existingAddress);
	        
	        return savedAddress;
	    } else {
	        return null;
	    }
	}
	
	public Address delete(BigInteger id) {
		Optional<Address> address = repository.findById(id);
		
	    if (address.isPresent()) {
	        repository.delete(address.get());
	        return address.get();
	    } else {
	        return null;
	    }
	}
}
