package com.medicai.pillpal.service;

import com.medicai.pillpal.config.ApplicationProperties;
import com.medicai.pillpal.domain.enumeration.Form;
import com.medicai.pillpal.service.dto.ApplicationInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class FDAFileReader {

    private final Logger log = LoggerFactory.getLogger(FDAFileReader.class);

    private final FileService fileService;

    private final ApplicationProperties applicationProperties;
    private Object Connection;

    public FDAFileReader(FileService fileReaderService, ApplicationProperties applicationProperties) {
        this.fileService = fileReaderService;
        this.applicationProperties = applicationProperties;
    }

    public boolean readFile() {
        String path = applicationProperties.getFDA_FILE_PATH();
        try {
            List<String> lines = fileService.readLinesOfFile(path);
            List<ApplicationInfoDTO> arrayApp = new ArrayList<>();

            for (String line :lines) {
                String[] split = line.split("\t");

                ApplicationInfoDTO applicationInfoDTO = new ApplicationInfoDTO();
                applicationInfoDTO.setFdaApplicationNo(split[0]);
                applicationInfoDTO.setProductNumber(Integer.valueOf(split[1]));
                applicationInfoDTO.setForm(Form.valueOf(split[2].split("/|;|,")[0]));
                applicationInfoDTO.setStrengthUnit(split[3]);
                applicationInfoDTO.setName(split[5]);
                applicationInfoDTO.setActiveIngredient(split[6]);
                arrayApp.add(applicationInfoDTO);
            }

            log.info("Line Number: {}", lines.size());
            return Boolean.TRUE;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return Boolean.FALSE;
    }
}
