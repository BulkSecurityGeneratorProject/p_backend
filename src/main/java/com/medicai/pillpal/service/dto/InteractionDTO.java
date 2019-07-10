package com.medicai.pillpal.service.dto;

import com.medicai.pillpal.domain.enumeration.RecommendationType;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.Interaction} entity.
 */
@ApiModel(description = "a reaction between two (or more) drugs or between a drug and a food, beverage, or supplement.")
public class InteractionDTO implements Serializable {

    private Long id;

    @NotNull
    private RecommendationType recommendationType;

    private String description;

    private Long baseApplicationInfoId;

    private Long descApplicationInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecommendationType getRecommendationType() {
        return recommendationType;
    }

    public void setRecommendationType(RecommendationType recommendationType) {
        this.recommendationType = recommendationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBaseApplicationInfoId() {
        return baseApplicationInfoId;
    }

    public void setBaseApplicationInfoId(Long applicationInfoId) {
        this.baseApplicationInfoId = applicationInfoId;
    }

    public Long getDescApplicationInfoId() {
        return descApplicationInfoId;
    }

    public void setDescApplicationInfoId(Long applicationInfoId) {
        this.descApplicationInfoId = applicationInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InteractionDTO interactionDTO = (InteractionDTO) o;
        if (interactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), interactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InteractionDTO{" +
            "id=" + getId() +
            ", recommendationType='" + getRecommendationType() + "'" +
            ", description='" + getDescription() + "'" +
            ", baseApplicationInfo=" + getBaseApplicationInfoId() +
            ", descApplicationInfo=" + getDescApplicationInfoId() +
            "}";
    }
}
