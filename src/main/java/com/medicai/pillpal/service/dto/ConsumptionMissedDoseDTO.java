package com.medicai.pillpal.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.ConsumptionMissedDose} entity.
 */
public class ConsumptionMissedDoseDTO implements Serializable {

    private Long id;

    private String conclusion;

    private String missedDose;


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

    public String getMissedDose() {
        return missedDose;
    }

    public void setMissedDose(String missedDose) {
        this.missedDose = missedDose;
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

        ConsumptionMissedDoseDTO consumptionMissedDoseDTO = (ConsumptionMissedDoseDTO) o;
        if (consumptionMissedDoseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consumptionMissedDoseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsumptionMissedDoseDTO{" +
            "id=" + getId() +
            ", conclusion='" + getConclusion() + "'" +
            ", missedDose='" + getMissedDose() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
