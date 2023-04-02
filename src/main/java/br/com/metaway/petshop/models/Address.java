package br.com.metaway.petshop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address extends BaseEntity {
	
	@NotEmpty
	@Column(name = "address_line", nullable = false)
	private String addressLine;
    
	@NotEmpty
	private String avenue;
    
	@NotEmpty
	private String city;
    
	@NotEmpty
	private String state;
    
	@NotEmpty
	private String country;
    
	@NotEmpty
	@Size(max=20)
	@Column(name = "zip_code", nullable = false)
	private String zipCode;
    
	@Column(name = "additional_information")
	private String additionalInformation;
}
