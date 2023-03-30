package br.com.metaway.petshop.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;
/**
 * Simple JavaBean domain object adds a name property to BaseEntity.
 * 
 * @author cazevedo
 *
 */
@MappedSuperclass
public class NamedEntity extends Entity {
	@Column(name = "name")
	@NotEmpty
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}