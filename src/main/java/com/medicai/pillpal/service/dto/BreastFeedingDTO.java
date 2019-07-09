package com.medicai.pillpal.service.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.BreastFeeding} entity.
 */
@ApiModel(description = "Drug Information in women breast feeding")
public class BreastFeedingDTO implements Serializable {

    private Long id;

    private String breastFeeding;


    private Long applicationInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreastFeeding() {
        return breastFeeding;
    }

    public void setBreastFeeding(String breastFeeding) {
        this.breastFeeding = breastFeeding;
    }

    public Long getApplicationInfoId() {
        return applicationInfoId;
    }

    public void setApplicationInfoId(Long applicationInfoId) {
        this.applicationInfoId = applicationInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BreastFeedingDTO breastFeedingDTO = (BreastFeedingDTO) o;
        if (breastFeedingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), breastFeedingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BreastFeedingDTO{" +
            "id=" + getId() +
            ", breastFeeding='" + getBreastFeeding() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
