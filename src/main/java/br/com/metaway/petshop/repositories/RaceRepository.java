package br.com.metaway.petshop.repositories;

import java.util.List;

import org.springframework.dao.DataAccessException;

import br.com.metaway.petshop.models.Race;

public interface RaceRepository {
	
	Race findById(int id) throws DataAccessException;
	
	List<Race> findAll() throws DataAccessException;
	
	Race save(Race race) throws DataAccessException;
	
	void delete(Race race) throws DataAccessException;

}
