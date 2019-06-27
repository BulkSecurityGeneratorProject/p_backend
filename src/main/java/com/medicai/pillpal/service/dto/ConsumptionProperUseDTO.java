package com.medicai.pillpal.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.ConsumptionProperUse} entity.
 */
public class ConsumptionProperUseDTO implements Serializable {

    private Long id;

    private String conclusion;

    private String properUse;


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

    public String getProperUse() {
        return properUse;
    }

    public void setProperUse(String properUse) {
        this.properUse = properUse;
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

        ConsumptionProperUseDTO consumptionProperUseDTO = (ConsumptionProperUseDTO) o;
        if (consumptionProperUseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consumptionProperUseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsumptionProperUseDTO{" +
            "id=" + getId() +
            ", conclusion='" + getConclusion() + "'" +
            ", properUse='" + getProperUse() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
