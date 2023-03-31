package br.com.metaway.petshop.repositories.implementation;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;

import br.com.metaway.petshop.models.Address;
import br.com.metaway.petshop.repositories.AddressRepository;
import jakarta.persistence.EntityManager;

public class AddressRepositoryImpl implements AddressRepository {
	
	private EntityManager em;

	@Override
	public Optional<Address> findById(BigInteger id) throws DataAccessException {
		return Optional.ofNullable(this.em.find(Address.class, id));
	}

	@Override
	public List<Address> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address save(Address address) throws DataAccessException {
		this.em.persist(address);
		
		return address;
	}

	@Override
	public void delete(Address address) throws DataAccessException {
		// TODO Auto-generated method stub

	}

}
