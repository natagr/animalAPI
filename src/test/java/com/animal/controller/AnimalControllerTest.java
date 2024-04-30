package com.animal.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.animal.model.QAnimal;
import com.animal.model.dto.AnimalForm;
import com.animal.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static com.animal.model.constant.Category.FOURTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalControllerTest {

    @Mock
    private AnimalService animalService;

    @InjectMocks
    private AnimalController animalController;

    @Test
    void testGetAnimals() {

        AnimalForm animal1 = new AnimalForm();
        animal1.setName("Buddy");
        animal1.setSex("male");
        animal1.setType("cat");
        animal1.setWeight(12);
        animal1.setCost(12);
        animal1.setCategory("FIRST");

        AnimalForm animal2 = new AnimalForm();
        animal2.setName("Bud");
        animal2.setSex("male");
        animal2.setType("dog");
        animal2.setWeight(45);
        animal2.setCost(23);
        animal2.setCategory("SECOND");

        AnimalForm animal3 = new AnimalForm();
        animal2.setName("Betty");
        animal2.setSex("female");
        animal2.setType("cat");
        animal2.setWeight(20);
        animal2.setCost(23);
        animal2.setCategory("SECOND");

        List<AnimalForm> animalForms = List.of(
                animal1, animal2, animal3
        );

        Page<AnimalForm> expectedPage = new PageImpl<>(List.of(animalForms.get(2), animalForms.get(1), animalForms.get(0)));

        QAnimal qAnimal = QAnimal.animal;
        BooleanExpression predicate = qAnimal.type.eq("cat")
                .and(qAnimal.category.eq(FOURTH))
                .and(qAnimal.sex.eq("female"));

        when(animalService.getAnimals(any(Predicate.class), any(PageRequest.class)))
                .thenReturn(expectedPage);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("cost").descending());
        Page<AnimalForm> actualPage = animalController.getAnimals(predicate, pageRequest);

        assertEquals(expectedPage, actualPage);
    }
}