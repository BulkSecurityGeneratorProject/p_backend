package com.medicai.pillpal.service;

import com.medicai.pillpal.config.ApplicationProperties;
import com.medicai.pillpal.service.dto.ApplicationInfoDTO;
import com.mysql.cj.protocol.Resultset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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



            for (String line :lines) {
                String[] split = line.split("\t");
            }
            ApplicationInfoDTO applicationInfoDTO = new ApplicationInfoDTO();
            applicationInfoDTO.setFdaApplicationNo(0,);
            applicationInfoDTO.setName(1,);
            applicationInfoDTO.setGenericName();
            applicationInfoDTO.setBrandName();
            applicationInfoDTO.setActiveIngredient();
            applicationInfoDTO.setForm();
            applicationInfoDTO.setOverView();
            applicationInfoDTO.setStrengthAmount();
            applicationInfoDTO.setStrengthUnit();
            applicationInfoDTO.setProductNumber();
            applicationInfoDTO.setRoutsOfAdministration();


            log.info("Line Number: {}", lines.size());
            return Boolean.TRUE;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return Boolean.FALSE;
    }
}
