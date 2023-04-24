package br.com.metaway.petshop.config.docs;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.metaway.petshop.models.Contact;
import br.com.metaway.petshop.repositories.dtos.ContactData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Contact")
public interface ContactDocumentation {
	@Operation(description = "Returns a page cotaining the 20 first contacts in database, it can be changed with GET parameters")
	public ResponseEntity<Page<ContactData>> index(Pageable pageable);
	
	@Operation(description = "Returns the information about the Contact of required ID")
	public ResponseEntity<ContactData> show(@PathVariable BigInteger id);
	
	@Operation(description = "Saves the passed Contact in the database")
	public ResponseEntity<ContactData> store(@RequestBody Contact contact);
	
	@Operation(description = "Update the data of the existing Contact in database with provided Contact information.")
	public ResponseEntity<ContactData> update(@PathVariable BigInteger id, @RequestBody Contact contact);
	
	@Operation(description = "Delete the contact data with given ID")
	public ResponseEntity<Void> destroy(@PathVariable BigInteger id);
}
