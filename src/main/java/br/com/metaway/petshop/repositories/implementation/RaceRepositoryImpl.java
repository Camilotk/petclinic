package br.com.metaway.petshop.repositories.implementation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.repositories.RaceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class RaceRepositoryImpl implements RaceRepository {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<Race> findById(BigInteger id) throws DataAccessException {
		return Optional.ofNullable(this.em.find(Race.class, id));
	}

	@SuppressAjWarnings("unchecked")
	@Override
	public List<Race> findAll() {
	    List<Race> races = new ArrayList<>();
	    try {
	        races = this.em.createQuery("FROM Race", Race.class).getResultList();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return races;
	}

	@Override
	public Race save(Race race) throws DataAccessException {
		this.em.persist(race);
		
		return race;
	}
	
	@Override
	public void delete(Race race) throws DataAccessException {
		this.em.remove(em.contains(race) ? race : em.merge(race));
	}

}
