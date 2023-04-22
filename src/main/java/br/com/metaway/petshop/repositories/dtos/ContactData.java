package br.com.metaway.petshop.repositories.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import br.com.metaway.petshop.models.ContactType;

public record ContactData(BigInteger id, String clientCpf, ContactType type, BigInteger value, String currency) implements Serializable {

}
