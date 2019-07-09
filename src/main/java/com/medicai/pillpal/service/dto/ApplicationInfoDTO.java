package com.medicai.pillpal.service.dto;

import com.medicai.pillpal.domain.enumeration.Form;
import com.medicai.pillpal.domain.enumeration.RoutsOfAdministration;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.medicai.pillpal.domain.ApplicationInfo} entity.
 */
@ApiModel(description = "Information about medicine")
public class ApplicationInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String fdaApplicationNo;

    private String name;

    private String genericName;

    private String brandName;

    private String overView;

    private Float strengthAmount;

    private String strengthUnit;

    private Integer productNumber;

    private String activeIngredient;

    private Form form;

    private RoutsOfAdministration routsOfAdministration;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFdaApplicationNo() {
        return fdaApplicationNo;
    }

    public void setFdaApplicationNo(String fdaApplicationNo) {
        this.fdaApplicationNo = fdaApplicationNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public Float getStrengthAmount() {
        return strengthAmount;
    }

    public void setStrengthAmount(Float strengthAmount) {
        this.strengthAmount = strengthAmount;
    }

    public String getStrengthUnit() {
        return strengthUnit;
    }

    public void setStrengthUnit(String strengthUnit) {
        this.strengthUnit = strengthUnit;
    }

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public RoutsOfAdministration getRoutsOfAdministration() {
        return routsOfAdministration;
    }

    public void setRoutsOfAdministration(RoutsOfAdministration routsOfAdministration) {
        this.routsOfAdministration = routsOfAdministration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationInfoDTO applicationInfoDTO = (ApplicationInfoDTO) o;
        if (applicationInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationInfoDTO{" +
            "id=" + getId() +
            ", fdaApplicationNo='" + getFdaApplicationNo() + "'" +
            ", name='" + getName() + "'" +
            ", genericName='" + getGenericName() + "'" +
            ", brandName='" + getBrandName() + "'" +
            ", overView='" + getOverView() + "'" +
            ", strengthAmount=" + getStrengthAmount() +
            ", strengthUnit='" + getStrengthUnit() + "'" +
            ", productNumber=" + getProductNumber() +
            ", activeIngredient='" + getActiveIngredient() + "'" +
            ", form='" + getForm() + "'" +
            ", routsOfAdministration='" + getRoutsOfAdministration() + "'" +
            "}";
    }
}
