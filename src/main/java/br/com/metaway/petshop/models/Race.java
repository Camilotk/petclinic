package br.com.metaway.petshop.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Race extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -8247904884809978998L;
	
	@Getter @Setter
	private String description;
}
