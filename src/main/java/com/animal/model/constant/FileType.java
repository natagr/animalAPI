package com.animal.model.constant;

import java.util.*;

public enum FileType {

    CSV("text/csv"),
    XML("text/xml", "application/xml");

    private final Set<String> contentTypes;

    FileType(String... contentTypes) {
        this.contentTypes = new HashSet<>(Arrays.asList(contentTypes));
    }

    public Set<String> getContentTypes() {
        return contentTypes;
    }
}