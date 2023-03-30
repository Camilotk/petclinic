package br.com.metaway.petshop.models;

import jakarta.persistence.Column;

@jakarta.persistence.Entity
public class Race extends Entity {
	@Column(name="description")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
