package br.com.metaway.petshop.services;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.metaway.petshop.models.Address;
import br.com.metaway.petshop.repositories.AddressRepository;

@Service
public class AddressService {
	@Autowired
	private AddressRepository repository;
	
	@CachePut(value = "address", key = "#address.id")
	@CacheEvict(value = "allAddresses", allEntries = true)
	public Address create(Address address) {
		repository.save(address);
		return address;
	}
	
	@Cacheable(value = "allAddresses") 
	public Page<Address> getAll(Pageable pageable) {
		Page<Address> addresses = repository.findAll(pageable);
		return addresses;
	}

	@Cacheable(value = "addressById", key = "#id")
	public Address getById(BigInteger id) {
		Optional<Address> address = repository.findById(id);
		
	    if (address.isPresent()) {
	        return address.get();
	    } else {
	        return null;
	    }
	}
	
	@CachePut(value = "address", key = "#id")
	@CacheEvict(value = {"address", "allAddresses"}, key = "#id", allEntries = true)
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
	
	@CacheEvict(value = {"address", "allAddresses"}, key = "#id", allEntries = true)
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
