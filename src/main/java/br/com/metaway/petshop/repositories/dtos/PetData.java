package br.com.metaway.petshop.repositories.dtos;

import java.math.BigInteger;
import java.time.LocalDate;

public record PetData(BigInteger id, String name, String clientCpf, LocalDate birthDate, BigInteger raceId) {

}
