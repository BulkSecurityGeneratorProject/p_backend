package com.medicai.pillpal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * some information about producer
 */
@Entity
@Table(name = "production_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "producer_name")
    private String producerName;

    @Column(name = "manufacturing_country")
    private String manufacturingCountry;

    @ManyToOne
    @JsonIgnoreProperties("productionInfos")
    private ApplicationInfo applicationInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducerName() {
        return producerName;
    }

    public ProductionInfo producerName(String producerName) {
        this.producerName = producerName;
        return this;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getManufacturingCountry() {
        return manufacturingCountry;
    }

    public ProductionInfo manufacturingCountry(String manufacturingCountry) {
        this.manufacturingCountry = manufacturingCountry;
        return this;
    }

    public void setManufacturingCountry(String manufacturingCountry) {
        this.manufacturingCountry = manufacturingCountry;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public ProductionInfo applicationInfo(ApplicationInfo applicationInfo) {
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
        if (!(o instanceof ProductionInfo)) {
            return false;
        }
        return id != null && id.equals(((ProductionInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductionInfo{" +
            "id=" + getId() +
            ", producerName='" + getProducerName() + "'" +
            ", manufacturingCountry='" + getManufacturingCountry() + "'" +
            "}";
    }
}
