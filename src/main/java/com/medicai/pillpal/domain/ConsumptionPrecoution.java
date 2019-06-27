package com.medicai.pillpal.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ConsumptionPrecoution.
 */
@Entity
@Table(name = "consumption_precoution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConsumptionPrecoution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conclusion")
    private String conclusion;

    @Column(name = "precoution")
    private String precoution;

    @ManyToOne
    @JsonIgnoreProperties("consumptionPrecoutions")
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

    public ConsumptionPrecoution conclusion(String conclusion) {
        this.conclusion = conclusion;
        return this;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getPrecoution() {
        return precoution;
    }

    public ConsumptionPrecoution precoution(String precoution) {
        this.precoution = precoution;
        return this;
    }

    public void setPrecoution(String precoution) {
        this.precoution = precoution;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public ConsumptionPrecoution applicationInfo(ApplicationInfo applicationInfo) {
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
        if (!(o instanceof ConsumptionPrecoution)) {
            return false;
        }
        return id != null && id.equals(((ConsumptionPrecoution) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ConsumptionPrecoution{" +
            "id=" + getId() +
            ", conclusion='" + getConclusion() + "'" +
            ", precoution='" + getPrecoution() + "'" +
            "}";
    }
}
