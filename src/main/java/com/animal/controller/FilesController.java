package com.animal.controller;

import com.animal.service.ParserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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