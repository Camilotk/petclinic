package br.com.metaway.petshop.repositories.dtos;

import java.math.BigInteger;

import br.com.metaway.petshop.models.ContactType;

public record ContactData(BigInteger id, String clientCpf, ContactType type, BigInteger value, String currency) {

}
