package br.com.metaway.petshop.config.docs;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.metaway.petshop.models.Visit;
import br.com.metaway.petshop.repositories.dtos.VisitData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Visit")
public interface VisitDocumentation {
	@Operation(description = "Returns a page cotaining the 20 first visits in database, it can be changed with GET parameters")
	public ResponseEntity<Page<VisitData>> index(Pageable pageable);
	
	@Operation(description = "Returns the information about the Visit of required ID")
	public ResponseEntity<VisitData> show(@PathVariable BigInteger id);
	
	@Operation(description = "Saves the passed Visit in the database")
	public ResponseEntity<VisitData> store(@RequestBody Visit visit);
	
	@Operation(description = "Update the data of the existing Visit in database with provided Visit information.")
	public ResponseEntity<VisitData> update(@PathVariable BigInteger id, @RequestBody Visit visit);
	
	@Operation(description = "Delete the Visit data with given ID")
	public ResponseEntity<Void> destroy(@PathVariable BigInteger id);
}
