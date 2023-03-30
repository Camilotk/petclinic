package br.com.metaway.petshop.repositories;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;

import br.com.metaway.petshop.models.Race;

public interface RaceRepository {
	
	Optional<Race> findById(BigInteger id) throws DataAccessException;
	
	List<Race> findAll() throws DataAccessException;
	
	Race save(Race race) throws DataAccessException;
	
	void delete(Race race) throws DataAccessException;

}
