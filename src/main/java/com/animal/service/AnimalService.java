package com.animal.service;

import com.querydsl.core.types.Predicate;
import com.animal.model.Animal;
import com.animal.model.dto.AnimalDto;
import com.animal.model.dto.AnimalForm;
import com.animal.model.mapper.AnimalMapper;
import com.animal.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    /**
     * Saves a list of animal data transfer objects (DTOs) to the repository.
     *
     * @param animalDtos A list of {@link AnimalDto} objects to be saved.
     * Each DTO is mapped to an {@link Animal} entity using {@link AnimalMapper} and then saved in bulk via {@link AnimalRepository}.
     */
    @Transactional
    public void saveAnimals(List<AnimalDto> animalDtos) {
        List<Animal> animals = animalDtos.stream().map(animalMapper::toEntity).toList();
        animalRepository.saveAll(animals);
    }

    /**
     * Retrieves a page of animal form data matching a given predicate and pageable settings.
     *
     * @param predicate A {@link Predicate} to filter the results based on specific conditions.
     * @param pageable A {@link Pageable} instance defining pagination details such as page number and size.
     * @return A {@link Page} of {@link AnimalForm} instances, each mapped from an {@link Animal} entity by {@link AnimalMapper}.
     */
    @Transactional(readOnly = true)
    public Page<AnimalForm> getAnimals(Predicate predicate, Pageable pageable) {
        return animalRepository.findAll(predicate, pageable)
                .map(animalMapper::toForm);
    }
}
