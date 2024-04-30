package com.animal.controller;

import com.animal.service.ParserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FilesController {

    private final ParserManager parserManager;

    @PostMapping(value = "/uploads", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadAnimals(@RequestParam("file") MultipartFile file) {
        parserManager.processFile(file);
    }
}