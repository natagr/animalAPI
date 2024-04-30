package com.animal.model.mapper;

import com.animal.model.Animal;
import com.animal.model.dto.AnimalDto;

import com.animal.model.dto.AnimalForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(target = "category", ignore = true)
    Animal toEntity(AnimalDto animalDto);

    @Mapping(source = "animal.category.label", target = "category")
    AnimalForm toForm(Animal animal);
}
