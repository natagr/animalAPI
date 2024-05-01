package com.animal.service;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.animal.model.dto.AnimalDto;
import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class XMLFileServiceTest {

    @Mock
    private AnimalService animalService;

    @Mock
    private Validator validator;

    private XMLFileService xmlFileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        xmlFileService = new XMLFileService(animalService, validator);
    }

    @Test
    public void testSupportsTrue() {
        assertTrue(xmlFileService.supports("text/xml"));
    }

    @Test
    public void testSupportsFalse() {
        assertFalse(xmlFileService.supports("text/csv"));
    }

    @Test
    public void testParseFile() {
        String xmlData = """
                <animals>
                    <animal>
                        <name>Milo</name>
                        <type>cat</type>
                        <sex>male</sex>
                        <weight>40</weight>
                        <cost>51</cost>
                    </animal>
                    <animal>
                        <name>Simon</name>
                        <type>dog</type>
                        <sex>male</sex>
                        <weight>45</weight>
                        <cost>17</cost>
                    </animal>
                </animals>
                """;
        InputStream inputStream = new ByteArrayInputStream(xmlData.getBytes(StandardCharsets.UTF_8));

        when(validator.validate(any(AnimalDto.class))).thenReturn(Set.of());

        xmlFileService.parseFile(inputStream);

        verify(animalService).saveAnimals(anyList());
    }
}