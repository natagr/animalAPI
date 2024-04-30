package com.animal.controller;

import com.querydsl.core.types.Predicate;
import com.animal.model.Animal;
import com.animal.model.dto.AnimalForm;
import com.animal.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping
    public Page<AnimalForm> getAnimals(@QuerydslPredicate(root = Animal.class) Predicate predicate, Pageable pageable) {
        return animalService.getAnimals(predicate, pageable);
    }
}
