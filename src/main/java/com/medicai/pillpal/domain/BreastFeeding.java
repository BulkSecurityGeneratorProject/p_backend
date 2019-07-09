package com.medicai.pillpal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Drug Information in women breast feeding
 */
@Entity
@Table(name = "breast_feeding")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BreastFeeding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "breast_feeding")
    private String breastFeeding;

    @ManyToOne
    @JsonIgnoreProperties("breastFeedings")
    private ApplicationInfo applicationInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreastFeeding() {
        return breastFeeding;
    }

    public BreastFeeding breastFeeding(String breastFeeding) {
        this.breastFeeding = breastFeeding;
        return this;
    }

    public void setBreastFeeding(String breastFeeding) {
        this.breastFeeding = breastFeeding;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public BreastFeeding applicationInfo(ApplicationInfo applicationInfo) {
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
        if (!(o instanceof BreastFeeding)) {
            return false;
        }
        return id != null && id.equals(((BreastFeeding) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BreastFeeding{" +
            "id=" + getId() +
            ", breastFeeding='" + getBreastFeeding() + "'" +
            "}";
    }
}
