package br.com.metaway.petshop.controllers;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.metaway.petshop.models.Visit;
import br.com.metaway.petshop.repositories.dtos.VisitData;
import br.com.metaway.petshop.services.VisitService;


@RestController
@RequestMapping("/visits")
public class VisitController {
	
	@Autowired
	private VisitService service;
	
	@PostMapping
    public ResponseEntity<VisitData> store(@RequestBody Visit visit) {
		VisitData savedVisit = service.create(visit);
		
		if (savedVisit == null) {
			return ResponseEntity.badRequest().build();
		}

        return ResponseEntity.status(HttpStatus.CREATED).body(savedVisit);
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<VisitData> show(@PathVariable BigInteger id) {
    	VisitData visit = service.getById(id);
    	
    	if (visit == null) {
    		return ResponseEntity.notFound().build();
    	}
    	
    	return ResponseEntity.ok(visit);
    }

	@GetMapping
	public ResponseEntity<Page<VisitData>> index(Pageable pageable) {
        Page<VisitData> visits = service.getAll(pageable);
        return ResponseEntity.ok(visits);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitData> update(@PathVariable BigInteger id, @RequestBody Visit visit) {
    	VisitData editedVisit = service.edit(id, visit);
    	
    	if (editedVisit == null) {
    		return ResponseEntity.notFound().build();
    	}
    	
    	if (editedVisit.id() == null) {
    		return ResponseEntity.badRequest().build();
    	}
    	 
        return ResponseEntity.ok(editedVisit);
    }

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> destroy(@PathVariable BigInteger id) {
		Visit deletedVisit = service.delete(id);
		
		if (deletedVisit == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
