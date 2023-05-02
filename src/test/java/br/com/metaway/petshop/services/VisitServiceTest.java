package br.com.metaway.petshop.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.metaway.petshop.models.Client;
import br.com.metaway.petshop.models.Pet;
import br.com.metaway.petshop.models.Race;
import br.com.metaway.petshop.models.Visit;
import br.com.metaway.petshop.repositories.PetRepository;
import br.com.metaway.petshop.repositories.VisitRepository;
import br.com.metaway.petshop.repositories.dtos.VisitData;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private VisitService visitService;

    private Visit visit;
    private Pet pet;

    @BeforeEach
    public void setUp() {
        pet = new Pet();
        pet.setId(BigInteger.valueOf(1));
        pet.setName("Fluffy");
        pet.setRace(new Race("Persian"));

        visit = new Visit();
        visit.setId(BigInteger.valueOf(1));
        visit.setDate(new Date());
        visit.setDescription("Checkup");
        visit.setPet(pet);
        visit.setValue(BigInteger.valueOf(50));
        visit.setCurrency("BRL");
    }

    @Test
    public void createVisitShouldReturnVisitData() {
        when(petRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(pet));
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        VisitData visitData = visitService.create(visit);

        verify(petRepository, times(1)).findById(any(BigInteger.class));
        verify(visitRepository, times(1)).save(any(Visit.class));
        assertThat(visitData).isEqualToComparingFieldByField(new VisitData(visit.getId(), visit.getPet().getId(), visit.getDate(), visit.getDescription(), visit.getValue(), visit.getCurrency()));
    }

    @Test
    public void createVisitShouldReturnNullWhenPetNotFound() {
        when(petRepository.findById(any(BigInteger.class))).thenReturn(Optional.empty());

        VisitData visitData = visitService.create(visit);

        verify(petRepository, times(1)).findById(any(BigInteger.class));
        verify(visitRepository, times(0)).save(any(Visit.class));
        assertThat(visitData).isNull();
    }

    @Test
    public void getAllVisitsShouldReturnPageOfVisitData() {
        List<Visit> visits = new ArrayList<>();
        visits.add(visit);
        Page<Visit> visitPage = new PageImpl<>(visits);
        Pageable pageable = PageRequest.of(0, 10);

        when(visitRepository.findAll(pageable)).thenReturn(visitPage);

        Page<VisitData> visitDataPage = visitService.getAll(pageable);

        verify(visitRepository, times(1)).findAll(pageable);
        assertThat(visitDataPage).extracting("id", "petId", "date", "description", "value", "currency")
                .containsExactly(tuple(visit.getId(), visit.getPet().getId(), visit.getDate(), visit.getDescription(), visit.getValue(), visit.getCurrency()));
    }

	@Test
	void getVisitByIdShouldReturnVisitData() {
		// Arrange
		BigInteger id = BigInteger.valueOf(1);
		Pet pet = new Pet("Fido", new Client(), LocalDate.now(), new Race(), null);
		pet.setId(id);
		
		Set<Visit> visits = new HashSet<>();
		
		Visit first_visit = new Visit(new Date(), "Checkup", pet, BigInteger.TEN, "USD");
		first_visit.setId(BigInteger.ONE);
		Visit second_visit = new Visit(new Date(), "Vaccine", pet, BigInteger.valueOf(50), "USD");
		first_visit.setId(BigInteger.TWO);
		Visit third_visit = new Visit(new Date(), "Consult", pet, BigInteger.valueOf(25), "USD");
		first_visit.setId(BigInteger.valueOf(3));
		
		visits.add(first_visit);
		visits.add(second_visit);
		visits.add(third_visit);
		
		pet.setVisits(visits);
		
		when(visitRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(visit));

		// Act
		VisitData visitData = visitService.getById(id);

		// Assert
		verify(visitRepository, times(1)).findById(any(BigInteger.class));
		assertThat(visitData).isNotNull();
		assertThat(visitData.id()).isEqualTo(id);
		assertThat(visitData.petId()).isEqualTo(pet.getId());
		assertThat(visitData.date()).isEqualTo(visit.getDate());
		assertThat(visitData.description()).isEqualTo(visit.getDescription());
		assertThat(visitData.value()).isEqualTo(visit.getValue());
		assertThat(visitData.currency()).isEqualTo(visit.getCurrency());
	}

	@Test
	void itShouldEditVisit() {
	    // Create a new visit
		Pet pet = new Pet("Fido", new Client(), LocalDate.now(), new Race(), null);
		pet.setId(BigInteger.ONE);
	    Visit visit = new Visit(new Date(), "Checkup", pet, BigInteger.TEN, "USD");
		visit.setId(BigInteger.ONE);
	
	    when(petRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(pet));
	    when(visitRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(visit));
	    when(visitRepository.save(any(Visit.class))).thenAnswer(i -> i.getArguments()[0]);
	
	    // Edit the visit
	    Visit updatedVisit = new Visit(new Date(), "Updated visit description", pet,  BigInteger.valueOf(150), "USD");
	    VisitData updatedVisitData = visitService.edit(BigInteger.valueOf(1), updatedVisit);
	
	    // Verify the updated visit data
	    assertThat(updatedVisitData.id()).isEqualTo(BigInteger.valueOf(1));
	    assertThat(updatedVisitData.petId()).isEqualTo(BigInteger.valueOf(1));
	    assertThat(updatedVisitData.date()).isEqualTo(updatedVisit.getDate());
	    assertThat(updatedVisitData.description()).isEqualTo(updatedVisit.getDescription());
	    assertThat(updatedVisitData.value()).isEqualTo(updatedVisit.getValue());
	    assertThat(updatedVisitData.currency()).isEqualTo(updatedVisit.getCurrency());
	
	    // Verify the save method was called once with the updated visit
	    ArgumentCaptor<Visit> captor = ArgumentCaptor.forClass(Visit.class);
	    verify(visitRepository, times(1)).save(captor.capture());
	    assertThat(captor.getValue().getId()).isEqualTo(BigInteger.valueOf(1));
	    assertThat(captor.getValue().getPet()).isEqualTo(pet);
	    assertThat(captor.getValue().getDate()).isEqualTo(updatedVisit.getDate());
	    assertThat(captor.getValue().getDescription()).isEqualTo(updatedVisit.getDescription());
	    assertThat(captor.getValue().getValue()).isEqualTo(updatedVisit.getValue());
	    assertThat(captor.getValue().getCurrency()).isEqualTo(updatedVisit.getCurrency());
	}
	
	@Test
	void itShouldDeleteVisit() {
	    // Create a new visit
		Pet pet = new Pet("Fido", new Client(), LocalDate.now(), new Race(), null);
		pet.setId(BigInteger.ONE);
	    Visit visit = new Visit(new Date(), "Checkup", pet, BigInteger.TEN, "USD");
		visit.setId(BigInteger.ONE);
	
	    when(visitRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(visit));
	
	    // Delete the visit
	    Visit deletedVisit = visitService.delete(BigInteger.valueOf(1));
	
	    // Verify the deleted visit
	    assertThat(deletedVisit.getId()).isEqualTo(BigInteger.valueOf(1));
	    assertThat(deletedVisit.getPet()).isEqualTo(pet);
	    assertThat(deletedVisit.getDate()).isEqualTo(visit.getDate());
	    assertThat(deletedVisit.getDescription()).isEqualTo(visit.getDescription());
	    assertThat(deletedVisit.getValue()).isEqualTo(visit.getValue());
	    assertThat(deletedVisit.getCurrency()).isEqualTo(visit.getCurrency());
	
	    // Verify the delete method was called once with the visit
	    verify(visitRepository, times(1)).delete(visit);
	}


}
