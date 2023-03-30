package br.com.metaway.petshop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Race extends BaseEntity {
	@Column(name="description")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
