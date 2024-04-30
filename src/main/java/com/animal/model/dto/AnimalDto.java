package com.animal.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JacksonXmlRootElement(localName = "animal")
public class AnimalDto {

    @JsonProperty("Name")
    @JacksonXmlProperty(localName = "name")
    @NotBlank(message = "Name must not be blank")
    private String name;

    @JsonProperty("Type")
    @JacksonXmlProperty(localName = "type")
    @NotBlank(message = "Type must not be blank")
    private String type;

    @JsonProperty("Sex")
    @JacksonXmlProperty(localName = "sex")
    @NotBlank(message = "Type must not be blank")
    private String sex;

    @JsonProperty("Weight")
    @JacksonXmlProperty(localName = "weight")
    @NotNull(message = "salary may not be empty")
    private Integer weight;

    @JsonProperty("Cost")
    @JacksonXmlProperty(localName = "cost")
    @NotNull(message = "salary may not be empty")
    private Integer cost;
}