package com.medicai.pillpal.service.dto;

import com.medicai.pillpal.domain.enumeration.SideEffectType;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.AppInfoSideEffect} entity.
 */
public class AppInfoSideEffectDTO implements Serializable {

    private Long id;

    private SideEffectType sideEffectType;


    private Long applicationInfoId;

    private Long sideEffectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SideEffectType getSideEffectType() {
        return sideEffectType;
    }

    public void setSideEffectType(SideEffectType sideEffectType) {
        this.sideEffectType = sideEffectType;
    }

    public Long getApplicationInfoId() {
        return applicationInfoId;
    }

    public void setApplicationInfoId(Long applicationInfoId) {
        this.applicationInfoId = applicationInfoId;
    }

    public Long getSideEffectId() {
        return sideEffectId;
    }

    public void setSideEffectId(Long sideEffectId) {
        this.sideEffectId = sideEffectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppInfoSideEffectDTO appInfoSideEffectDTO = (AppInfoSideEffectDTO) o;
        if (appInfoSideEffectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appInfoSideEffectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppInfoSideEffectDTO{" +
            "id=" + getId() +
            ", sideEffectType='" + getSideEffectType() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            ", sideEffect=" + getSideEffectId() +
            "}";
    }
}
