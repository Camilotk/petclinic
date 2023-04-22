package br.com.metaway.petshop.repositories.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public record VisitData(BigInteger id, BigInteger petId, Date date, String description, BigInteger value, String currency) implements Serializable {

}
