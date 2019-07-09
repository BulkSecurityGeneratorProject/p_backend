package com.medicai.pillpal.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private Set<AppInfoSideEffect> sideEffects = new HashSet<>();

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

    public Set<AppInfoSideEffect> getSideEffects() {
        return sideEffects;
    }

    public SideEffect sideEffects(Set<AppInfoSideEffect> appInfoSideEffects) {
        this.sideEffects = appInfoSideEffects;
        return this;
    }

    public SideEffect addSideEffect(AppInfoSideEffect appInfoSideEffect) {
        this.sideEffects.add(appInfoSideEffect);
        appInfoSideEffect.setSideEffect(this);
        return this;
    }

    public SideEffect removeSideEffect(AppInfoSideEffect appInfoSideEffect) {
        this.sideEffects.remove(appInfoSideEffect);
        appInfoSideEffect.setSideEffect(null);
        return this;
    }

    public void setSideEffects(Set<AppInfoSideEffect> appInfoSideEffects) {
        this.sideEffects = appInfoSideEffects;
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
