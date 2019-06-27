package com.medicai.pillpal.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ConsumptionProperUse.
 */
@Entity
@Table(name = "consumption_proper_use")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConsumptionProperUse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conclusion")
    private String conclusion;

    @Column(name = "proper_use")
    private String properUse;

    @ManyToOne
    @JsonIgnoreProperties("consumptionProperUses")
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

    public ConsumptionProperUse conclusion(String conclusion) {
        this.conclusion = conclusion;
        return this;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getProperUse() {
        return properUse;
    }

    public ConsumptionProperUse properUse(String properUse) {
        this.properUse = properUse;
        return this;
    }

    public void setProperUse(String properUse) {
        this.properUse = properUse;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public ConsumptionProperUse applicationInfo(ApplicationInfo applicationInfo) {
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
        if (!(o instanceof ConsumptionProperUse)) {
            return false;
        }
        return id != null && id.equals(((ConsumptionProperUse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ConsumptionProperUse{" +
            "id=" + getId() +
            ", conclusion='" + getConclusion() + "'" +
            ", properUse='" + getProperUse() + "'" +
            "}";
    }
}
