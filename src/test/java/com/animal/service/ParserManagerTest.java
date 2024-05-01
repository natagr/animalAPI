package com.animal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ParserManagerTest {

    @Mock
    private FileParser xmlParser;

    @Mock
    private FileParser csvParser;

    @InjectMocks
    private ParserManager parserManager;

    @Mock
    private MultipartFile file;

    @BeforeEach
    void setup() {
        lenient().when(xmlParser.supports("application/xml")).thenReturn(true);
        lenient().when(csvParser.supports("text/csv")).thenReturn(true);
        List<FileParser> parsers = Arrays.asList(xmlParser, csvParser);
        parserManager = new ParserManager(parsers);
    }

    @Test
    public void shouldThrowExceptionWhenFileTypeNotSupported() {
        when(file.getContentType()).thenReturn("application/json");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parserManager.processFile(file);
        });

        assertEquals("File type is not supported by any parser", exception.getMessage());
    }

    @Test
    public void shouldUseCorrectParserForXMLFiles() throws IOException {
        when(file.getContentType()).thenReturn("application/xml");
        InputStream dummyStream = mock(InputStream.class);
        when(file.getInputStream()).thenReturn(dummyStream);

        parserManager.processFile(file);

        verify(xmlParser).parseFile(dummyStream);
        verify(csvParser, never()).parseFile(dummyStream);
    }

    @Test
    public void shouldUseCorrectParserForCSVFiles() throws IOException {
        when(file.getContentType()).thenReturn("text/csv");
        InputStream dummyStream = mock(InputStream.class);
        when(file.getInputStream()).thenReturn(dummyStream);

        parserManager.processFile(file);

        verify(csvParser).parseFile(dummyStream);
        verify(xmlParser, never()).parseFile(dummyStream);
    }
}

