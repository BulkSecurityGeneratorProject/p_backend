package com.medicai.pillpal.service;

import com.medicai.pillpal.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
public class FDAFileReader {

    private final Logger log = LoggerFactory.getLogger(FDAFileReader.class);

    private final FileService fileService;

    private final ApplicationProperties applicationProperties;

    public FDAFileReader(FileService fileReaderService, ApplicationProperties applicationProperties) {
        this.fileService = fileReaderService;
        this.applicationProperties = applicationProperties ;
    }

    public boolean readFile() {
        String path= applicationProperties.getFDA_FILE_PATH();
        try {
            List<String> lines = fileService.readLinesOfFile(path);
            log.info("Line Number: {}", lines.size());
            return Boolean.TRUE;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return Boolean.FALSE;
    }
}
