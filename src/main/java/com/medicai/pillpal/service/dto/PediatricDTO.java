package com.medicai.pillpal.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.Pediatric} entity.
 */
@ApiModel(description = "Information about using drug in childern younger than 10")
public class PediatricDTO implements Serializable {

    private Long id;

    private String pediatric;


    private Long applicationInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPediatric() {
        return pediatric;
    }

    public void setPediatric(String pediatric) {
        this.pediatric = pediatric;
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

        PediatricDTO pediatricDTO = (PediatricDTO) o;
        if (pediatricDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pediatricDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PediatricDTO{" +
            "id=" + getId() +
            ", pediatric='" + getPediatric() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
