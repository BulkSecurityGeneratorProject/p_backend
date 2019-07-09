package com.medicai.pillpal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A ConsumptionBeforeUsing.
 */
@Entity
@Table(name = "consumption_before_using")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConsumptionBeforeUsing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conclusion")
    private String conclusion;

    @Column(name = "before_using")
    private String beforeUsing;

    @ManyToOne
    @JsonIgnoreProperties("consumptionBeforeUsings")
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

    public ConsumptionBeforeUsing conclusion(String conclusion) {
        this.conclusion = conclusion;
        return this;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getBeforeUsing() {
        return beforeUsing;
    }

    public ConsumptionBeforeUsing beforeUsing(String beforeUsing) {
        this.beforeUsing = beforeUsing;
        return this;
    }

    public void setBeforeUsing(String beforeUsing) {
        this.beforeUsing = beforeUsing;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public ConsumptionBeforeUsing applicationInfo(ApplicationInfo applicationInfo) {
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
        if (!(o instanceof ConsumptionBeforeUsing)) {
            return false;
        }
        return id != null && id.equals(((ConsumptionBeforeUsing) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ConsumptionBeforeUsing{" +
            "id=" + getId() +
            ", conclusion='" + getConclusion() + "'" +
            ", beforeUsing='" + getBeforeUsing() + "'" +
            "}";
    }
}
