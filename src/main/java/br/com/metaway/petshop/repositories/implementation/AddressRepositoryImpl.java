package br.com.metaway.petshop.repositories.implementation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import br.com.metaway.petshop.models.Address;
import br.com.metaway.petshop.repositories.AddressRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class AddressRepositoryImpl implements AddressRepository {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<Address> findById(BigInteger id) throws DataAccessException {
		return Optional.ofNullable(this.em.find(Address.class, id));
	}

	@SuppressAjWarnings("unchecked")
	@Override
	public List<Address> findAll() {
	    List<Address> races = new ArrayList<>();
	    try {
	        races = this.em.createQuery("FROM Address", Address.class).getResultList();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return races;
	}


	@Override
	public Address save(Address address) throws DataAccessException {
		this.em.persist(address);
		
		return address;
	}

	@Override
	public void delete(Address address) throws DataAccessException {
		this.em.remove(em.contains(address) ? address : em.merge(address));
	}

}
