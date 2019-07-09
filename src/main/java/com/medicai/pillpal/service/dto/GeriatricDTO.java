package com.medicai.pillpal.service.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.Geriatric} entity.
 */
@ApiModel(description = "Information about using drug in  the elderly")
public class GeriatricDTO implements Serializable {

    private Long id;

    private String geriatric;


    private Long applicationInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeriatric() {
        return geriatric;
    }

    public void setGeriatric(String geriatric) {
        this.geriatric = geriatric;
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

        GeriatricDTO geriatricDTO = (GeriatricDTO) o;
        if (geriatricDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), geriatricDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GeriatricDTO{" +
            "id=" + getId() +
            ", geriatric='" + getGeriatric() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
