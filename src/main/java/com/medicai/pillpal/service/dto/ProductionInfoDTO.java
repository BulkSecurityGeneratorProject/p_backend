package com.medicai.pillpal.service.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.ProductionInfo} entity.
 */
@ApiModel(description = "some information about producer")
public class ProductionInfoDTO implements Serializable {

    private Long id;

    private String producerName;

    private String manufacturingCountry;


    private Long applicationInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getManufacturingCountry() {
        return manufacturingCountry;
    }

    public void setManufacturingCountry(String manufacturingCountry) {
        this.manufacturingCountry = manufacturingCountry;
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

        ProductionInfoDTO productionInfoDTO = (ProductionInfoDTO) o;
        if (productionInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productionInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductionInfoDTO{" +
            "id=" + getId() +
            ", producerName='" + getProducerName() + "'" +
            ", manufacturingCountry='" + getManufacturingCountry() + "'" +
            ", applicationInfo=" + getApplicationInfoId() +
            "}";
    }
}
