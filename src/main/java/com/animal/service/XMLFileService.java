package com.animal.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.animal.model.dto.AnimalDto;
import com.animal.model.dto.AnimalDtos;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static com.animal.model.constant.FileType.XML;

@Service
@RequiredArgsConstructor
public class XMLFileService implements FileParser {

    private final AnimalService animalService;
    private final Validator validator;

    /**
     * Checks if the given file type is supported by this parser.
     *
     * @param fileType The type of the file to be checked.
     * @return true if the file type is supported as XML, false otherwise.
     */
    @Override
    public boolean supports(String fileType) {
        return XML.getContentTypes().contains(fileType);
    }

    /**
     * Parses an input stream of an XML file, validates the parsed animal data, and saves the valid entries.
     *
     * @param is The input stream of the XML file to be parsed.
     * Uses {@link XmlMapper} and {@link XMLStreamReader} to parse the XML into {@link AnimalDto} objects,
     * filters the valid data using {@link Validator}, and then saves valid animal records through {@link AnimalService}.
     */
    @SneakyThrows
    @Override
    public void parseFile(InputStream is) {
        XmlMapper xmlMapper = new XmlMapper();
        XMLInputFactory xmlFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = xmlFactory.createXMLStreamReader(is);

        AnimalDtos animals = xmlMapper.readValue(xmlStreamReader, AnimalDtos.class);

        List<AnimalDto> animalsToSave = animals.getAnimals().stream()
                .filter(animal -> validator.validate(animal).isEmpty())
                .collect(Collectors.toList());

        animalService.saveAnimals(animalsToSave);
    }
}

