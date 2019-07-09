package com.medicai.pillpal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A ConsumptionMissedDose.
 */
@Entity
@Table(name = "consumption_missed_dose")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConsumptionMissedDose implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conclusion")
    private String conclusion;

    @Column(name = "missed_dose")
    private String missedDose;

    @ManyToOne
    @JsonIgnoreProperties("consumptionMissedDoses")
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

    public ConsumptionMissedDose conclusion(String conclusion) {
        this.conclusion = conclusion;
        return this;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getMissedDose() {
        return missedDose;
    }

    public ConsumptionMissedDose missedDose(String missedDose) {
        this.missedDose = missedDose;
        return this;
    }

    public void setMissedDose(String missedDose) {
        this.missedDose = missedDose;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public ConsumptionMissedDose applicationInfo(ApplicationInfo applicationInfo) {
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
        if (!(o instanceof ConsumptionMissedDose)) {
            return false;
        }
        return id != null && id.equals(((ConsumptionMissedDose) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ConsumptionMissedDose{" +
            "id=" + getId() +
            ", conclusion='" + getConclusion() + "'" +
            ", missedDose='" + getMissedDose() + "'" +
            "}";
    }
}
