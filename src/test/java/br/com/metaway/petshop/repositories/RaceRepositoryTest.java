package br.com.metaway.petshop.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.metaway.petshop.models.Race;

@DataJpaTest
class RaceRepositoryTest {
    
    @Autowired
    private RaceRepository raceRepository;
    
    @Test
    @DisplayName("it should save a new race")
    void testSave() {
        // given
        Race  race = new Race();
        race.setDescription("Marathon");

        // when
        raceRepository.save(race);

        // then
        assertThat(raceRepository.findAll()).hasSize(1).contains(race);
    }
    
    @Test
    @DisplayName("it should find a race by id")
    void testFindById() {
        // given
        Race race = new Race();
        race.setDescription("5K");
        raceRepository.save(race);

        // when
        Optional<Race> foundRace = raceRepository.findById(race.getId());

        // then
        assertThat(foundRace).isPresent().contains(race);
    }
    
    @Test
    @DisplayName("it should update a race")
    void testUpdate() {
        // given
        Race race = new Race();
        race.setDescription("10K");
        raceRepository.save(race);

        // when
        Race savedRace = raceRepository.findAll().get(0);
        savedRace.setDescription("Half Marathon");
        raceRepository.save(savedRace);

        // then
        assertThat(raceRepository.findById(savedRace.getId()))
                .isPresent()
                .get()
                .extracting(Race::getDescription)
                .isEqualTo("Half Marathon");
    }
    
    @Test
    @DisplayName("it should delete a race")
    void testDelete() {
        // given
        Race race = new Race();
        race.setDescription("Sprint");
        raceRepository.save(race);

        // when
        raceRepository.delete(race);

        // then
        assertThat(raceRepository.findAll()).isEmpty();
    }
    
}
