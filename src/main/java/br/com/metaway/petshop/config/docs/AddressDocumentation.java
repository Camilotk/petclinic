package br.com.metaway.petshop.config.docs;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.metaway.petshop.models.Address;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Address")
public interface AddressDocumentation {
	@Operation(description = "Returns a page cotaining the 20 first addresses in database, it can be changed with GET parameters")
	public ResponseEntity<Page<Address>> index(Pageable pageable);
	
	@Operation(description = "Returns the information about the Address of required ID")
	public ResponseEntity<Address> show(@PathVariable BigInteger id);
	
	@Operation(description = "Saves the passed Address in the database")
	public ResponseEntity<Address> store(@RequestBody Address address);
	
	@Operation(description = "Update the data of the existing Address in database with provided Address information.")
	public ResponseEntity<Address> update(@PathVariable BigInteger id, @RequestBody Address address);
	
	@Operation(description = "Delete the address data with given ID")
	public ResponseEntity<Void> destroy(@PathVariable BigInteger id);
}
