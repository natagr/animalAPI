package com.animal.model;

import com.animal.model.base.AbstractIdentifiable;
import com.animal.model.constant.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "animals")
public class Animal extends AbstractIdentifiable {

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "sex")
    private String sex;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    public void setCost(Integer cost) {
        this.cost = cost;
        this.category = determineCategory(cost);
    }

    private Category determineCategory(Integer cost) {
        int categoryIndex = cost / 20;

        return switch (categoryIndex) {
            case 0 -> Category.FIRST;
            case 1 -> Category.SECOND;
            case 2 -> Category.THIRD;
            default -> Category.FOURTH;
        };
    }
}
