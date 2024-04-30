package com.animal.service;

import java.io.InputStream;

public interface FileParser {

    boolean supports(String fileType);

    void parseFile(InputStream is);
}