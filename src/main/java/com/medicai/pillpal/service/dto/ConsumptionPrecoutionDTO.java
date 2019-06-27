package com.medicai.pillpal.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.ConsumptionPrecoution} entity.
 */
public class ConsumptionPrecoutionDTO implements Serializable {

    private Long id;

    private String conclusion;

    private String precoution;


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

    public String getPrecoution() {
        return precoution;
    }

    public void setPrecoution(String precoution) {
        this.precoution = precoution;
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

        ConsumptionPrecoutionDTO consumptionPrecoutionDTO = (ConsumptionPrecoutionDTO) o;
        if (consumptionPrecoutionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consumptionPrecoutionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsumptionPrecoutionDTO{" +
            "id=" + getId() +
            ", conclusion='" + getConclusion() + "'" +
            ", precoution='" + getPrecoution() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
