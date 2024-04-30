package com.animal.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalForm {

    private String name;
    private String type;
    private String sex;
    private Integer weight;
    private Integer cost;
    private String category;
}
