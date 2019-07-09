package com.medicai.pillpal.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.medicai.pillpal.domain.enumeration.SideEffectType;

/**
 * A AppInfoSideEffect.
 */
@Entity
@Table(name = "appl_info_side_effect")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppInfoSideEffect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "side_effect_type")
    private SideEffectType sideEffectType;

    @ManyToOne
    @JsonIgnoreProperties("applInfoSideEffects")
    private ApplicationInfo applicationInfo;

    @ManyToOne
    @JsonIgnoreProperties("applInfoSideEffects")
    private SideEffect sideEffect;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SideEffectType getSideEffectType() {
        return sideEffectType;
    }

    public AppInfoSideEffect sideEffectType(SideEffectType sideEffectType) {
        this.sideEffectType = sideEffectType;
        return this;
    }

    public void setSideEffectType(SideEffectType sideEffectType) {
        this.sideEffectType = sideEffectType;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public AppInfoSideEffect applicationInfo(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
        return this;
    }

    public void setApplicationInfo(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    public SideEffect getSideEffect() {
        return sideEffect;
    }

    public AppInfoSideEffect sideEffect(SideEffect sideEffect) {
        this.sideEffect = sideEffect;
        return this;
    }

    public void setSideEffect(SideEffect sideEffect) {
        this.sideEffect = sideEffect;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppInfoSideEffect)) {
            return false;
        }
        return id != null && id.equals(((AppInfoSideEffect) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AppInfoSideEffect{" +
            "id=" + getId() +
            ", sideEffectType='" + getSideEffectType() + "'" +
            "}";
    }
}
