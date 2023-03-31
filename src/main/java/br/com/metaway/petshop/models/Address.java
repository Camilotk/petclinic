package br.com.metaway.petshop.models;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

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
    
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
	public String getAvenue() {
		return avenue;
	}
	public void setAvenue(String avenue) {
		this.avenue = avenue;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(additionalInformation, addressLine, avenue, city, country, state, zipCode);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (!(obj instanceof Address)) return false;
        
		Address other = (Address) obj;
		
		return Objects.equals(additionalInformation, other.additionalInformation)
				&& Objects.equals(addressLine, other.addressLine) && Objects.equals(avenue, other.avenue)
				&& Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(state, other.state) && Objects.equals(zipCode, other.zipCode);
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		
		sb.append("Address: ");
		sb.append(addressLine).append(", ");
		sb.append(avenue).append(", ");
		sb.append(city).append(", ");
		sb.append(state).append(", ");
		sb.append(zipCode);
		
		if (additionalInformation != null && !additionalInformation.trim().isEmpty()) {
			sb.append(", ").append(additionalInformation);
		}
		
		return sb.toString();
	}
}
