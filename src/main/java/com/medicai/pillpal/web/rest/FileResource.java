package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.component.FDAFileReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FileResource {

    private final FDAFileReader FDAFileReader;

    public FileResource(FDAFileReader FDAFileReader) {
        this.FDAFileReader = FDAFileReader;
    }

    @GetMapping("/files")
    public ResponseEntity<Boolean> readFile() {
        return ResponseEntity.ok().body(FDAFileReader.readFile());
    }
}
