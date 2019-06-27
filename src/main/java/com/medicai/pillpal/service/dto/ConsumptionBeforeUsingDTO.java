package com.medicai.pillpal.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.ConsumptionBeforeUsing} entity.
 */
public class ConsumptionBeforeUsingDTO implements Serializable {

    private Long id;

    private String conclusion;

    private String beforeUsing;


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

    public String getBeforeUsing() {
        return beforeUsing;
    }

    public void setBeforeUsing(String beforeUsing) {
        this.beforeUsing = beforeUsing;
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

        ConsumptionBeforeUsingDTO consumptionBeforeUsingDTO = (ConsumptionBeforeUsingDTO) o;
        if (consumptionBeforeUsingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consumptionBeforeUsingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsumptionBeforeUsingDTO{" +
            "id=" + getId() +
            ", conclusion='" + getConclusion() + "'" +
            ", beforeUsing='" + getBeforeUsing() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
