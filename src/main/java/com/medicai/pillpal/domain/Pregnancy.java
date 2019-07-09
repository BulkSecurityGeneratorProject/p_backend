package com.medicai.pillpal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Some information using drug while pregnancy
 */
@Entity
@Table(name = "pregnancy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pregnancy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pre_category")
    private String preCategory;

    @Column(name = "pre_explanation")
    private String preExplanation;

    @ManyToOne
    @JsonIgnoreProperties("pregnancies")
    private ApplicationInfo applicationInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreCategory() {
        return preCategory;
    }

    public Pregnancy preCategory(String preCategory) {
        this.preCategory = preCategory;
        return this;
    }

    public void setPreCategory(String preCategory) {
        this.preCategory = preCategory;
    }

    public String getPreExplanation() {
        return preExplanation;
    }

    public Pregnancy preExplanation(String preExplanation) {
        this.preExplanation = preExplanation;
        return this;
    }

    public void setPreExplanation(String preExplanation) {
        this.preExplanation = preExplanation;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public Pregnancy applicationInfo(ApplicationInfo applicationInfo) {
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
        if (!(o instanceof Pregnancy)) {
            return false;
        }
        return id != null && id.equals(((Pregnancy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pregnancy{" +
            "id=" + getId() +
            ", preCategory='" + getPreCategory() + "'" +
            ", preExplanation='" + getPreExplanation() + "'" +
            "}";
    }
}
