package com.medicai.pillpal.service.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.Pregnancy} entity.
 */
@ApiModel(description = "Some information using drug while pregnancy")
public class PregnancyDTO implements Serializable {

    private Long id;

    private String preCategory;

    private String preExplanation;


    private Long applicationInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreCategory() {
        return preCategory;
    }

    public void setPreCategory(String preCategory) {
        this.preCategory = preCategory;
    }

    public String getPreExplanation() {
        return preExplanation;
    }

    public void setPreExplanation(String preExplanation) {
        this.preExplanation = preExplanation;
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

        PregnancyDTO pregnancyDTO = (PregnancyDTO) o;
        if (pregnancyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pregnancyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PregnancyDTO{" +
            "id=" + getId() +
            ", preCategory='" + getPreCategory() + "'" +
            ", preExplanation='" + getPreExplanation() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
