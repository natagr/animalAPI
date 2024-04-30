package com.animal.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParserManager {

    private final List<FileParser> fileParsers;

    /**
     * Processes a given file by identifying and using a suitable parser from the list that supports the file's content type.
     *
     * @param file The {@link MultipartFile} to be processed.
     * @throws IllegalArgumentException if the file type is not supported by any of the registered parsers.
     * Utilizes {@link FileParser#supports(String)} to identify the correct parser delegate the actual parsing process.
     */
    @SneakyThrows
    public void processFile(MultipartFile file) {
        fileParsers.stream()
                .filter(fileParser -> fileParser.supports(file.getContentType()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("File type is not supported by any parser"))
                .parseFile(file.getInputStream());
    }
}