package com.medicai.pillpal.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SideEffect.
 */
@Entity
@Table(name = "side_effect")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SideEffect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "sideEffect")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplInfoSideEffect> sideEffets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public SideEffect description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ApplInfoSideEffect> getSideEffets() {
        return sideEffets;
    }

    public SideEffect sideEffets(Set<ApplInfoSideEffect> applInfoSideEffects) {
        this.sideEffets = applInfoSideEffects;
        return this;
    }

    public SideEffect addSideEffet(ApplInfoSideEffect applInfoSideEffect) {
        this.sideEffets.add(applInfoSideEffect);
        applInfoSideEffect.setSideEffect(this);
        return this;
    }

    public SideEffect removeSideEffet(ApplInfoSideEffect applInfoSideEffect) {
        this.sideEffets.remove(applInfoSideEffect);
        applInfoSideEffect.setSideEffect(null);
        return this;
    }

    public void setSideEffets(Set<ApplInfoSideEffect> applInfoSideEffects) {
        this.sideEffets = applInfoSideEffects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SideEffect)) {
            return false;
        }
        return id != null && id.equals(((SideEffect) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SideEffect{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
