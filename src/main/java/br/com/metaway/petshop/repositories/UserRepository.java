package br.com.metaway.petshop.repositories;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.metaway.petshop.models.User;

public interface UserRepository extends JpaRepository<User, BigInteger>{
	Optional<User> findByEmail(String email);
}
