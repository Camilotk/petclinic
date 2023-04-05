package br.com.metaway.petshop.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.metaway.petshop.models.Pet;

public interface PetRepository extends JpaRepository<Pet, BigInteger>{

}
