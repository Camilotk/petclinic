package br.com.metaway.petshop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Race extends BaseEntity {
	@Getter @Setter
	@Column(name="description")
	private String description;
}
