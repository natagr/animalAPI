package com.animal.service;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.animal.model.dto.AnimalDto;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static com.animal.model.constant.FileType.CSV;

@Service
@RequiredArgsConstructor
public class CSVFileService implements FileParser {

    private final AnimalService animalService;
    private final Validator validator;

    /**
     * Checks if the given file type is supported by this parser.
     *
     * @param fileType The type of the file to be checked.
     * @return true if the file type is supported as CSV, false otherwise.
     */
    @Override
    public boolean supports(String fileType) {
        return CSV.getContentTypes().contains(fileType);
    }

    /**
     * Parses an input stream of a CSV file, validates the parsed animal data, and saves the valid entries.
     *
     * @param is The input stream of the CSV file to be parsed.
     * Uses {@link CsvMapper} and {@link CsvSchema} to parse the CSV, filters valid data using {@link Validator},
     * and saves valid animal records through {@link AnimalService}.
     */
    @SneakyThrows
    @Override
    public void parseFile(InputStream is) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
        ObjectReader reader = mapper.readerFor(AnimalDto.class).with(schema);
        List<AnimalDto> animalsToSave = reader.<AnimalDto>readValues(is).readAll();
        List<AnimalDto> validAnimals = animalsToSave.stream()
                .filter(animal -> validator.validate(animal).isEmpty())
                .collect(Collectors.toList());
        animalService.saveAnimals(validAnimals);
    }
}
