package com.animal.controller;

import com.animal.service.ParserManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FilesControllerTest {

    @Mock
    private ParserManager parserManager;

    @InjectMocks
    private FilesController filesController;

    @Test
    void testUploadAnimals() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", MediaType.TEXT_PLAIN_VALUE, "Test file content".getBytes());

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(filesController).build();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/files/uploads")
                        .file(file))
                .andExpect(status().isCreated());

        verify(parserManager, times(1)).processFile(file);
    }

    @Test
    void testUploadAnimalsNoFileProvided() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(filesController).build();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/files/uploads"))
                .andExpect(status().isBadRequest());

        verify(parserManager, times(0)).processFile(null);
    }
}