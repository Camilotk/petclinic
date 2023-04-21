package br.com.metaway.petshop.models;

import java.io.Serializable;
import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -3465095490421345698L;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_cpf", referencedColumnName = "cpf")
	private Client client;
	
	@Enumerated(EnumType.STRING)
	private ContactType type;
	
	@NotNull
	@Column(name = "price_amount")
	private BigInteger value;
	
	@Column(name = "price_currency")
	private String currency = "BRL";
}
