package com.medicai.pillpal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medicai.pillpal.domain.enumeration.RecommendationType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * a reaction between two (or more) drugs or between
 * a drug and a food, beverage, or supplement.
 */
@Entity
@Table(name = "interaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Interaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "recommendation_type", nullable = false)
    private RecommendationType recommendationType;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("baseInteractions")
    private ApplicationInfo baseApplicationInfo;

    @ManyToOne
    @JsonIgnoreProperties("descInteractions")
    private ApplicationInfo descApplicationInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecommendationType getRecommendationType() {
        return recommendationType;
    }

    public Interaction recommendationType(RecommendationType recommendationType) {
        this.recommendationType = recommendationType;
        return this;
    }

    public void setRecommendationType(RecommendationType recommendationType) {
        this.recommendationType = recommendationType;
    }

    public String getDescription() {
        return description;
    }

    public Interaction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApplicationInfo getBaseApplicationInfo() {
        return baseApplicationInfo;
    }

    public Interaction baseApplicationInfo(ApplicationInfo applicationInfo) {
        this.baseApplicationInfo = applicationInfo;
        return this;
    }

    public void setBaseApplicationInfo(ApplicationInfo applicationInfo) {
        this.baseApplicationInfo = applicationInfo;
    }

    public ApplicationInfo getDescApplicationInfo() {
        return descApplicationInfo;
    }

    public Interaction descApplicationInfo(ApplicationInfo applicationInfo) {
        this.descApplicationInfo = applicationInfo;
        return this;
    }

    public void setDescApplicationInfo(ApplicationInfo applicationInfo) {
        this.descApplicationInfo = applicationInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Interaction)) {
            return false;
        }
        return id != null && id.equals(((Interaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Interaction{" +
            "id=" + getId() +
            ", recommendationType='" + getRecommendationType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
