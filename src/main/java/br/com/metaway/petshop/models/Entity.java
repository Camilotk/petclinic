package br.com.metaway.petshop.models;

import java.math.BigInteger;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Simple JavaBean domain object with an id property. Used as a base class with ID.
 * 
 * @author cazevedo
 *
 */
@MappedSuperclass
public class Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected BigInteger id;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}
}