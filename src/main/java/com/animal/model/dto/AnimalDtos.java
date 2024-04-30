package com.animal.model.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JacksonXmlRootElement(localName = "animals")
public class AnimalDtos {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "animal")
    private List<AnimalDto> animals;
}
