package com.animal.service;

import com.querydsl.core.types.Predicate;
import com.animal.model.Animal;
import com.animal.model.dto.AnimalDto;
import com.animal.model.dto.AnimalForm;
import com.animal.model.mapper.AnimalMapper;
import com.animal.repository.AnimalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AnimalMapper animalMapper;

    @InjectMocks
    private AnimalService animalService;

    @Captor
    private ArgumentCaptor<List<Animal>> animalListCaptor;

    @Test
    public void testSaveAnimals() {

        List<AnimalDto> animalDtos = List.of(new AnimalDto());
        List<Animal> animals = List.of(new Animal());

        when(animalMapper.toEntity(any(AnimalDto.class))).thenReturn(animals.get(0));

        animalService.saveAnimals(animalDtos);

        verify(animalRepository).saveAll(animalListCaptor.capture());
        List<Animal> capturedAnimals = animalListCaptor.getValue();

        assertEquals(animals.size(), capturedAnimals.size());
        assertEquals(animals.get(0), capturedAnimals.get(0));
        verify(animalMapper, times(animalDtos.size())).toEntity(any(AnimalDto.class));
    }

    @Test
    public void testGetAnimals() {

        Predicate predicate = mock(Predicate.class);
        Pageable pageable = PageRequest.of(0, 10);

        Animal animal = new Animal();
        animal.setName("Buddy");
        animal.setSex("male");
        animal.setType("cat");
        animal.setWeight(12);
        animal.setCost(12);

        AnimalForm animalForm = new AnimalForm();
        animalForm.setName("Buddy");
        animalForm.setSex("male");
        animalForm.setType("cat");
        animalForm.setWeight(12);
        animalForm.setCost(12);
        animalForm.setCategory("FIRST");

        Page<Animal> page = new PageImpl<>(List.of(animal));

        when(animalRepository.findAll(any(Predicate.class), eq(pageable))).thenReturn(page);
        when(animalMapper.toForm(any(Animal.class))).thenReturn(animalForm);

        Page<AnimalForm> result = animalService.getAnimals(predicate, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(animalRepository).findAll(any(Predicate.class), eq(pageable));
        verify(animalMapper, times(page.getContent().size())).toForm(any(Animal.class));
    }
}
