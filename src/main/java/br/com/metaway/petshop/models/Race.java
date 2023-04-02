package br.com.metaway.petshop.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Race extends BaseEntity {
	@Getter @Setter
	private String description;
}
