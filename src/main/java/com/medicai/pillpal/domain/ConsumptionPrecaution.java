package com.medicai.pillpal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A ConsumptionPrecaution.
 */
@Entity
@Table(name = "consumption_precaution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConsumptionPrecaution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conclusion")
    private String conclusion;

    @Column(name = "precaution")
    private String precaution;

    @ManyToOne
    @JsonIgnoreProperties("consumptionPrecautions")
    private ApplicationInfo applicationInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConclusion() {
        return conclusion;
    }

    public ConsumptionPrecaution conclusion(String conclusion) {
        this.conclusion = conclusion;
        return this;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getPrecaution() {
        return precaution;
    }

    public ConsumptionPrecaution precaution(String precaution) {
        this.precaution = precaution;
        return this;
    }

    public void setPrecaution(String precaution) {
        this.precaution = precaution;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public ConsumptionPrecaution applicationInfo(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
        return this;
    }

    public void setApplicationInfo(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsumptionPrecaution)) {
            return false;
        }
        return id != null && id.equals(((ConsumptionPrecaution) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ConsumptionPrecaution{" +
            "id=" + getId() +
            ", conclusion='" + getConclusion() + "'" +
            ", precaution='" + getPrecaution() + "'" +
            "}";
    }
}
