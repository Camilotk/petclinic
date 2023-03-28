package br.com.metaway.petshop.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

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