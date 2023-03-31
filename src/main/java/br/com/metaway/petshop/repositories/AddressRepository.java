package br.com.metaway.petshop.repositories;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;

import br.com.metaway.petshop.models.Address;

public interface AddressRepository {
	Optional<Address> findById(BigInteger id) throws DataAccessException;
	List<Address> findAll() throws DataAccessException;
    Address save(Address address) throws DataAccessException;
    void delete(Address address) throws DataAccessException;
}
