package br.com.metaway.petshop.config.docs;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.metaway.petshop.models.Race;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Race")
public interface RaceDocumentation {
	@Operation(description = "Returns a page cotaining the 20 first races in database, it can be changed with GET parameters")
	public ResponseEntity<Page<Race>> index(Pageable pageable);
	
	@Operation(description = "Returns the information about the Race of required ID")
	public ResponseEntity<Race> show(@PathVariable BigInteger id);
	
	@Operation(description = "Saves the passed Race in the database")
	public ResponseEntity<Race> store(@RequestBody Race race);
	
	@Operation(description = "Update the data of the existing Race in database with provided Race information.")
	public ResponseEntity<Race> update(@PathVariable BigInteger id, @RequestBody Race race);
	
	@Operation(description = "Delete the Race data with given ID")
	public ResponseEntity<Void> destroy(@PathVariable BigInteger id);
}
