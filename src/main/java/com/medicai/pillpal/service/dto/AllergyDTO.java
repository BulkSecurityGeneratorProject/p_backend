package com.medicai.pillpal.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.Allergy} entity.
 */
@ApiModel(description = "Information about allergic reactions")
public class AllergyDTO implements Serializable {

    private Long id;

    private String allergy;


    private Long applicationInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
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

        AllergyDTO allergyDTO = (AllergyDTO) o;
        if (allergyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), allergyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AllergyDTO{" +
            "id=" + getId() +
            ", allergy='" + getAllergy() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
