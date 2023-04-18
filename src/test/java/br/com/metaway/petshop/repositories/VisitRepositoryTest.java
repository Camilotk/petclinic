package br.com.metaway.petshop.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.metaway.petshop.models.Pet;
import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.models.Visit;

@DataJpaTest
public class VisitRepositoryTest {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private RaceRepository raceRepository;

    private Pet pet;
    private Race race;
    private Visit visit;

    @BeforeEach
    public void setup() {
        race = Race.builder().description("Labrador").build();
        raceRepository.save(race);

        pet = Pet.builder()
                .name("Fido")
                .birthDate(LocalDate.now())
                .race(race)
                .build();

        petRepository.save(pet);

        visit = Visit.builder()
                .date(new Date())
                .description("Routine checkup")
                .pet(pet)
                .value(BigInteger.valueOf(100))
                .currency("USD")
                .build();

        visitRepository.save(visit);
    }
    
    @AfterEach
    void clean() {
    	visitRepository.deleteAll();
    	petRepository.deleteAll();
    	raceRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        List<Visit> visits = visitRepository.findAll();
        assertThat(visits).hasSize(1);
        assertThat(visits.get(0)).isEqualTo(visit);
    }

    @Test
    public void testFindById() {
        Optional<Visit> foundVisit = visitRepository.findById(visit.getId());
        assertThat(foundVisit).isPresent().contains(visit);
    }

    @Test
    public void testSave() {
        Visit newVisit = Visit.builder()
                .date(new Date())
                .description("Vaccination")
                .pet(pet)
                .value(BigInteger.valueOf(50))
                .currency("BRL")
                .build();

        visitRepository.save(newVisit);

        List<Visit> visits = visitRepository.findAll();
        assertThat(visits).hasSize(2).contains(newVisit);
    }

    @Test
    public void testDelete() {
        visitRepository.delete(visit);
        assertThat(visitRepository.findAll()).isEmpty();
    }

    @Test
    public void testUpdate() {
        visit.setDescription("Updated description");
        visitRepository.save(visit);
        Optional<Visit> foundVisit = visitRepository.findById(visit.getId());
        assertThat(foundVisit).isPresent().get().extracting(Visit::getDescription).isEqualTo("Updated description");
    }
}
