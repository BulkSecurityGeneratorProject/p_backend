package com.medicai.pillpal.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.UseAndStorage} entity.
 */
public class UseAndStorageDTO implements Serializable {

    private Long id;

    private String conclusion;

    private String useAndStorage;


    private Long applicationInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getUseAndStorage() {
        return useAndStorage;
    }

    public void setUseAndStorage(String useAndStorage) {
        this.useAndStorage = useAndStorage;
    }

    public Long getApplicationInfoId() {
        return applicationInfoId;
    }

    public void setApplicationInfoId(Long applicationInfoId) {
        this.applicationInfoId = applicationInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UseAndStorageDTO useAndStorageDTO = (UseAndStorageDTO) o;
        if (useAndStorageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), useAndStorageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UseAndStorageDTO{" +
            "id=" + getId() +
            ", conclusion='" + getConclusion() + "'" +
            ", useAndStorage='" + getUseAndStorage() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
