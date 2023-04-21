package br.com.metaway.petshop.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.lang.NonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pet extends BaseEntity implements Serializable {	
	private static final long serialVersionUID = -9102164740303188304L;

	@NonNull
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "client_cpf", referencedColumnName = "cpf")
	private Client client;
	
	@NonNull
	@Column(name = "birth_date", columnDefinition = "DATE")
	private LocalDate birthDate;
	
	@ManyToOne
	@JoinColumn(name = "race_id")
	private Race race;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
    private Set<Visit> visits;
}
