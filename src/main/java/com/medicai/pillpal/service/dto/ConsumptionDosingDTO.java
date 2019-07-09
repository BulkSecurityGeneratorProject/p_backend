package com.medicai.pillpal.service.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.ConsumptionDosing} entity.
 */
@ApiModel(description = "How to use medicine")
public class ConsumptionDosingDTO implements Serializable {

    private Long id;

    private String conclusion;

    private String dosing;


    private Long applicationInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getDosing() {
        return dosing;
    }

    public void setDosing(String dosing) {
        this.dosing = dosing;
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

        ConsumptionDosingDTO consumptionDosingDTO = (ConsumptionDosingDTO) o;
        if (consumptionDosingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consumptionDosingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsumptionDosingDTO{" +
            "id=" + getId() +
            ", conclusion='" + getConclusion() + "'" +
            ", dosing='" + getDosing() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
