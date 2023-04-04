package br.com.metaway.petshop.models;

import javax.money.MonetaryAmount;

import org.hibernate.annotations.CompositeType;

import io.hypersistence.utils.hibernate.type.money.MonetaryAmountType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NonNull;
import jakarta.persistence.Column;

@Data
@Entity
public class Contact extends BaseEntity {
	@NonNull
	private Client client;
	
	@Enumerated
	private ContactType type;
	
	@AttributeOverride(
	        name = "amount",
	        column = @Column(name = "price_amount"))
	@AttributeOverride(
			name = "currency",
			column = @Column(name = "price_currency"))
	@CompositeType(MonetaryAmountType.class)
	private MonetaryAmount value;
}
