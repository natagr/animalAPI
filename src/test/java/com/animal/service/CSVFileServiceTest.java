package com.animal.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CSVFileServiceTest {

    @Mock
    private AnimalService animalServiceMock;

    @Mock
    private Validator validator;

    private CSVFileService csvFileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        csvFileService = new CSVFileService(animalServiceMock, validator);
    }

    @Test
    public void testSupportsTrue() {
        assertTrue(csvFileService.supports("text/csv"));
    }

    @Test
    public void testSupportsFalse() {
        assertFalse(csvFileService.supports("application/xml"));
    }

    @Test
    void testParseFile() {
        String csvData = "Name,Type,Sex,Weight,Cost\nBuddy,cat,female,41,78\nCooper,dog,female,46,23";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        when(validator.validate(any())).thenReturn(Set.of());

        csvFileService.parseFile(inputStream);

        verify(animalServiceMock).saveAnimals(anyList());
    }
}
