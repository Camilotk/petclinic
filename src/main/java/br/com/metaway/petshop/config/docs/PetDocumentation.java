package br.com.metaway.petshop.config.docs;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.metaway.petshop.models.Pet;
import br.com.metaway.petshop.repositories.dtos.PetData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pet")
public interface PetDocumentation {
	@Operation(description = "Returns a page cotaining the 20 first pets in database, it can be changed with GET parameters")
	public ResponseEntity<Page<PetData>> index(Pageable pageable);
	
	@Operation(description = "Returns the information about the Pet of required ID")
	public ResponseEntity<PetData> show(@PathVariable BigInteger id);
	
	@Operation(description = "Saves the passed Pet in the database")
	public ResponseEntity<PetData> store(@RequestBody Pet pet);
	
	@Operation(description = "Update the data of the existing Pet in database with provided Pet information.")
	public ResponseEntity<PetData> update(@PathVariable BigInteger id, @RequestBody Pet pet);
	
	@Operation(description = "Delete the Pet data with given ID")
	public ResponseEntity<Void> destroy(@PathVariable BigInteger id);
}
