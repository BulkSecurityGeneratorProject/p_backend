package com.medicai.pillpal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Pillpal.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    //TODO move to .yml file
    private final String FDA_FILE_PATH = "src/main/resources/files/Products.txt";

    public String getFDA_FILE_PATH() {
        return FDA_FILE_PATH;
    }
}
