package br.com.metaway.petshop.repositories;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.metaway.petshop.models.Pet;
import br.com.metaway.petshop.models.Race;

public interface RaceRepository extends JpaRepository<Pet, BigInteger> {

}
