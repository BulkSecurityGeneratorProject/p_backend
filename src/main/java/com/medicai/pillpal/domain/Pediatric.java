package com.medicai.pillpal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Information about using drug in children younger than 10
 */
@Entity
@Table(name = "pediatric")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pediatric implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pediatric")
    private String pediatric;

    @ManyToOne
    @JsonIgnoreProperties("pediatrics")
    private ApplicationInfo applicationInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPediatric() {
        return pediatric;
    }

    public Pediatric pediatric(String pediatric) {
        this.pediatric = pediatric;
        return this;
    }

    public void setPediatric(String pediatric) {
        this.pediatric = pediatric;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public Pediatric applicationInfo(ApplicationInfo applicationInfo) {
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
        if (!(o instanceof Pediatric)) {
            return false;
        }
        return id != null && id.equals(((Pediatric) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pediatric{" +
            "id=" + getId() +
            ", pediatric='" + getPediatric() + "'" +
            "}";
    }
}
