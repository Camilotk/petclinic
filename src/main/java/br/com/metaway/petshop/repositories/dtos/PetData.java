package br.com.metaway.petshop.repositories.dtos;

import java.math.BigInteger;
import java.time.LocalDate;

public record PetData(BigInteger id, String name, String client_cpf, LocalDate birth_date, BigInteger race_id) {

}
