package com.medicai.pillpal.service.component;

import com.medicai.pillpal.config.ApplicationProperties;
import com.medicai.pillpal.domain.enumeration.Form;
import com.medicai.pillpal.domain.enumeration.RoutsOfAdministration;
import com.medicai.pillpal.service.ApplicationInfoService;
import com.medicai.pillpal.service.FileService;
import com.medicai.pillpal.service.dto.ApplicationInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.*;


@Component
public class FDAFileReader {

    private final Logger log = LoggerFactory.getLogger( FDAFileReader.class );

    private final ApplicationProperties applicationProperties;

    private final FileService fileService;
    private final ApplicationInfoService applicationInfoService;

    public FDAFileReader(FileService fileReaderService, ApplicationProperties applicationProperties, ApplicationInfoService applicationInfoService) {
        this.fileService = fileReaderService;
        this.applicationProperties = applicationProperties;
        this.applicationInfoService = applicationInfoService;
    }

    public boolean readFile() {
        String path = applicationProperties.getFDA_FILE_PATH();
        log.info( "path: {}", path );
        int counter = 0;
        try {
            List<String> lines = fileService.readLinesOfFile( path );
            List<ApplicationInfoDTO> arrayApp = new ArrayList<>();

            boolean firstLine = true;

            for (String line : lines) {
                counter++;
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                //TODO decide about strength entity
                String[] split = line.split( "\t" );
                ApplicationInfoDTO applicationInfoDTO = new ApplicationInfoDTO();
                applicationInfoDTO.setFdaApplicationNo( split[0] );
                applicationInfoDTO.setProductNumber( Integer.valueOf( split[1] ) );
                applicationInfoDTO.setForm( Form.valueOf( split[2] ) );
                if (split[3] != null && split[3].trim().length() > 0)
                    applicationInfoDTO.setRoutsOfAdministration( RoutsOfAdministration.valueOf( split[3].trim() ) );
                //applicationInfoDTO.setStrengthUnit( split[4] );
                applicationInfoDTO.setName( split[4] );
                applicationInfoDTO.setGenericName( split[4] );
                applicationInfoDTO.setActiveIngredient( split[5] );
                arrayApp.add( applicationInfoDTO );
            }
            applicationInfoService.saveAll( arrayApp );

            log.info( "Line Number: {}", lines.size() );
            return Boolean.TRUE;
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append( "line Number : " );
            sb.append( counter );
            log.error( sb.toString() );
            log.error( e.getMessage() );
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append( "line Number : " );
            sb.append( counter );
            log.error( sb.toString() );
            log.error( e.getMessage() );
        }

        return Boolean.FALSE;
    }
}
