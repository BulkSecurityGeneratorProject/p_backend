package com.medicai.pillpal.service.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.PharmaceuticalCode} entity.
 */
@ApiModel(description = "Are used in mediacal classification to uniquely identify medication")
public class PharmaceuticalCodeDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    private String phoneNumber;


    private Long applicationInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

        PharmaceuticalCodeDTO pharmaceuticalCodeDTO = (PharmaceuticalCodeDTO) o;
        if (pharmaceuticalCodeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pharmaceuticalCodeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PharmaceuticalCodeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
