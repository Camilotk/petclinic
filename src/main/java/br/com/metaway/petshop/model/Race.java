package br.com.metaway.petshop.model;

import jakarta.persistence.Column;

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
