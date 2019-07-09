package com.medicai.pillpal.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.ConsumptionPrecaution} entity.
 */
public class ConsumptionPrecautionDTO implements Serializable {

    private Long id;

    private String conclusion;

    private String precaution;


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

    public String getPrecaution() {
        return precaution;
    }

    public void setPrecaution(String precaution) {
        this.precaution = precaution;
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

        ConsumptionPrecautionDTO consumptionPrecautionDTO = (ConsumptionPrecautionDTO) o;
        if (consumptionPrecautionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consumptionPrecautionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsumptionPrecautionDTO{" +
            "id=" + getId() +
            ", conclusion='" + getConclusion() + "'" +
            ", precaution='" + getPrecaution() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
