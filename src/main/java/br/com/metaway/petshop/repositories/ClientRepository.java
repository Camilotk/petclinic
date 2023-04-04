package br.com.metaway.petshop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.metaway.petshop.models.Client;

public interface ClientRepository extends JpaRepository<Client, String>{
	Optional<Client> findByCpf(String cpf);
}
