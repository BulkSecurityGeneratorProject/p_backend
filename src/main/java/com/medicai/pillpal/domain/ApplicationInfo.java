package com.medicai.pillpal.domain;

import com.medicai.pillpal.domain.enumeration.Form;
import com.medicai.pillpal.domain.enumeration.RoutsOfAdministration;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Information about medicine
 */
@Entity
@Table(name = "application_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApplicationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fda_application_no", nullable = false)
    private String fdaApplicationNo;

    @Column(name = "name")
    private String name;

    @Column(name = "generic_name")
    private String genericName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "over_view")
    private String overView;

    @Column(name = "strength_amount")
    private Float strengthAmount;

    @Column(name = "strength_unit")
    private String strengthUnit;

    @Column(name = "product_number")
    private Integer productNumber;

    @Column(name = "active_ingredient")
    private String activeIngredient;

    @Enumerated(EnumType.STRING)
    @Column(name = "form")
    private Form form;

    @Enumerated(EnumType.STRING)
    @Column(name = "routs_of_administration")
    private RoutsOfAdministration routsOfAdministration;

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductionInfo> productionInfos = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PharmaceuticalCode> pharmaceuticalCodes = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConsumptionDosing> dosings = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConsumptionMissedDose> missedDoses = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UseAndStorage> useAndStorages = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConsumptionBeforeUsing> beforeUses = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConsumptionPrecoution> precoutions = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConsumptionProperUse> properUses = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pregnancy> pregnancies = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Allergy> allergies = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pediatric> pediatrics = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Geriatric> geriatrics = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BreastFeeding> breastfeedings = new HashSet<>();

    @OneToMany(mappedBy = "applicationInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AppInfoSideEffect> appInfoSideEffects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFdaApplicationNo() {
        return fdaApplicationNo;
    }

    public ApplicationInfo fdaApplicationNo(String fdaApplicationNo) {
        this.fdaApplicationNo = fdaApplicationNo;
        return this;
    }

    public void setFdaApplicationNo(String fdaApplicationNo) {
        this.fdaApplicationNo = fdaApplicationNo;
    }

    public String getName() {
        return name;
    }

    public ApplicationInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenericName() {
        return genericName;
    }

    public ApplicationInfo genericName(String genericName) {
        this.genericName = genericName;
        return this;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getBrandName() {
        return brandName;
    }

    public ApplicationInfo brandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getOverView() {
        return overView;
    }

    public ApplicationInfo overView(String overView) {
        this.overView = overView;
        return this;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public Float getStrengthAmount() {
        return strengthAmount;
    }

    public ApplicationInfo strengthAmount(Float strengthAmount) {
        this.strengthAmount = strengthAmount;
        return this;
    }

    public void setStrengthAmount(Float strengthAmount) {
        this.strengthAmount = strengthAmount;
    }

    public String getStrengthUnit() {
        return strengthUnit;
    }

    public ApplicationInfo strengthUnit(String strengthUnit) {
        this.strengthUnit = strengthUnit;
        return this;
    }

    public void setStrengthUnit(String strengthUnit) {
        this.strengthUnit = strengthUnit;
    }

    public Integer getProductNumber() {
        return productNumber;
    }

    public ApplicationInfo productNumber(Integer productNumber) {
        this.productNumber = productNumber;
        return this;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public ApplicationInfo activeIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
        return this;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    public Form getForm() {
        return form;
    }

    public ApplicationInfo form(Form form) {
        this.form = form;
        return this;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public RoutsOfAdministration getRoutsOfAdministration() {
        return routsOfAdministration;
    }

    public ApplicationInfo routsOfAdministration(RoutsOfAdministration routsOfAdministration) {
        this.routsOfAdministration = routsOfAdministration;
        return this;
    }

    public void setRoutsOfAdministration(RoutsOfAdministration routsOfAdministration) {
        this.routsOfAdministration = routsOfAdministration;
    }

    public Set<ProductionInfo> getProductionInfos() {
        return productionInfos;
    }

    public ApplicationInfo productionInfos(Set<ProductionInfo> productionInfos) {
        this.productionInfos = productionInfos;
        return this;
    }

    public ApplicationInfo addProductionInfo(ProductionInfo productionInfo) {
        this.productionInfos.add(productionInfo);
        productionInfo.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removeProductionInfo(ProductionInfo productionInfo) {
        this.productionInfos.remove(productionInfo);
        productionInfo.setApplicationInfo(null);
        return this;
    }

    public void setProductionInfos(Set<ProductionInfo> productionInfos) {
        this.productionInfos = productionInfos;
    }

    public Set<PharmaceuticalCode> getPharmaceuticalcodes() {
        return pharmaceuticalCodes;
    }

    public ApplicationInfo pharmaceuticalCodes(Set<PharmaceuticalCode> pharmaceuticalCodes) {
        this.pharmaceuticalCodes = pharmaceuticalCodes;
        return this;
    }

    public ApplicationInfo addPharmaceuticalcode(PharmaceuticalCode pharmaceuticalCode) {
        this.pharmaceuticalCodes.add(pharmaceuticalCode);
        pharmaceuticalCode.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removePharmaceuticalcode(PharmaceuticalCode pharmaceuticalCode) {
        this.pharmaceuticalCodes.remove(pharmaceuticalCode);
        pharmaceuticalCode.setApplicationInfo(null);
        return this;
    }

    public void setPharmaceuticalcodes(Set<PharmaceuticalCode> pharmaceuticalCodes) {
        this.pharmaceuticalCodes = pharmaceuticalCodes;
    }

    public Set<ConsumptionDosing> getDosings() {
        return dosings;
    }

    public ApplicationInfo dosings(Set<ConsumptionDosing> consumptionDosings) {
        this.dosings = consumptionDosings;
        return this;
    }

    public ApplicationInfo addDosing(ConsumptionDosing consumptionDosing) {
        this.dosings.add(consumptionDosing);
        consumptionDosing.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removeDosing(ConsumptionDosing consumptionDosing) {
        this.dosings.remove(consumptionDosing);
        consumptionDosing.setApplicationInfo(null);
        return this;
    }

    public void setDosings(Set<ConsumptionDosing> consumptionDosings) {
        this.dosings = consumptionDosings;
    }

    public Set<ConsumptionMissedDose> getMissedDoses() {
        return missedDoses;
    }

    public ApplicationInfo missedDoses(Set<ConsumptionMissedDose> consumptionMissedDoses) {
        this.missedDoses = consumptionMissedDoses;
        return this;
    }

    public ApplicationInfo addMissedDose(ConsumptionMissedDose consumptionMissedDose) {
        this.missedDoses.add(consumptionMissedDose);
        consumptionMissedDose.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removeMissedDose(ConsumptionMissedDose consumptionMissedDose) {
        this.missedDoses.remove(consumptionMissedDose);
        consumptionMissedDose.setApplicationInfo(null);
        return this;
    }

    public void setMissedDoses(Set<ConsumptionMissedDose> consumptionMissedDoses) {
        this.missedDoses = consumptionMissedDoses;
    }

    public Set<UseAndStorage> getUseAndStorages() {
        return useAndStorages;
    }

    public ApplicationInfo useAndStorages(Set<UseAndStorage> useAndStorages) {
        this.useAndStorages = useAndStorages;
        return this;
    }

    public ApplicationInfo addUseAndStorage(UseAndStorage useAndStorage) {
        this.useAndStorages.add(useAndStorage);
        useAndStorage.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removeUseAndStorage(UseAndStorage useAndStorage) {
        this.useAndStorages.remove(useAndStorage);
        useAndStorage.setApplicationInfo(null);
        return this;
    }

    public void setUseAndStorages(Set<UseAndStorage> useAndStorages) {
        this.useAndStorages = useAndStorages;
    }

    public Set<ConsumptionBeforeUsing> getBeforeUses() {
        return beforeUses;
    }

    public ApplicationInfo beforeUses(Set<ConsumptionBeforeUsing> consumptionBeforeUsings) {
        this.beforeUses = consumptionBeforeUsings;
        return this;
    }

    public ApplicationInfo addBeforeUse(ConsumptionBeforeUsing consumptionBeforeUsing) {
        this.beforeUses.add(consumptionBeforeUsing);
        consumptionBeforeUsing.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removeBeforeUse(ConsumptionBeforeUsing consumptionBeforeUsing) {
        this.beforeUses.remove(consumptionBeforeUsing);
        consumptionBeforeUsing.setApplicationInfo(null);
        return this;
    }

    public void setBeforeUses(Set<ConsumptionBeforeUsing> consumptionBeforeUsings) {
        this.beforeUses = consumptionBeforeUsings;
    }

    public Set<ConsumptionPrecoution> getPrecoutions() {
        return precoutions;
    }

    public ApplicationInfo precoutions(Set<ConsumptionPrecoution> consumptionPrecoutions) {
        this.precoutions = consumptionPrecoutions;
        return this;
    }

    public ApplicationInfo addPrecoution(ConsumptionPrecoution consumptionPrecoution) {
        this.precoutions.add(consumptionPrecoution);
        consumptionPrecoution.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removePrecoution(ConsumptionPrecoution consumptionPrecoution) {
        this.precoutions.remove(consumptionPrecoution);
        consumptionPrecoution.setApplicationInfo(null);
        return this;
    }

    public void setPrecoutions(Set<ConsumptionPrecoution> consumptionPrecoutions) {
        this.precoutions = consumptionPrecoutions;
    }

    public Set<ConsumptionProperUse> getProperUses() {
        return properUses;
    }

    public ApplicationInfo properUses(Set<ConsumptionProperUse> consumptionProperUses) {
        this.properUses = consumptionProperUses;
        return this;
    }

    public ApplicationInfo addProperUse(ConsumptionProperUse consumptionProperUse) {
        this.properUses.add(consumptionProperUse);
        consumptionProperUse.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removeProperUse(ConsumptionProperUse consumptionProperUse) {
        this.properUses.remove(consumptionProperUse);
        consumptionProperUse.setApplicationInfo(null);
        return this;
    }

    public void setProperUses(Set<ConsumptionProperUse> consumptionProperUses) {
        this.properUses = consumptionProperUses;
    }

    public Set<Pregnancy> getPregnancies() {
        return pregnancies;
    }

    public ApplicationInfo pregnancies(Set<Pregnancy> pregnancies) {
        this.pregnancies = pregnancies;
        return this;
    }

    public ApplicationInfo addPregnancy(Pregnancy pregnancy) {
        this.pregnancies.add(pregnancy);
        pregnancy.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removePregnancy(Pregnancy pregnancy) {
        this.pregnancies.remove(pregnancy);
        pregnancy.setApplicationInfo(null);
        return this;
    }

    public void setPregnancies(Set<Pregnancy> pregnancies) {
        this.pregnancies = pregnancies;
    }

    public Set<Allergy> getAllergies() {
        return allergies;
    }

    public ApplicationInfo allergies(Set<Allergy> allergies) {
        this.allergies = allergies;
        return this;
    }

    public ApplicationInfo addAllergy(Allergy allergy) {
        this.allergies.add(allergy);
        allergy.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removeAllergy(Allergy allergy) {
        this.allergies.remove(allergy);
        allergy.setApplicationInfo(null);
        return this;
    }

    public void setAllergies(Set<Allergy> allergies) {
        this.allergies = allergies;
    }

    public Set<Pediatric> getPediatrics() {
        return pediatrics;
    }

    public ApplicationInfo pediatrics(Set<Pediatric> pediatrics) {
        this.pediatrics = pediatrics;
        return this;
    }

    public ApplicationInfo addPediatric(Pediatric pediatric) {
        this.pediatrics.add(pediatric);
        pediatric.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removePediatric(Pediatric pediatric) {
        this.pediatrics.remove(pediatric);
        pediatric.setApplicationInfo(null);
        return this;
    }

    public void setPediatrics(Set<Pediatric> pediatrics) {
        this.pediatrics = pediatrics;
    }

    public Set<Geriatric> getGeriatrics() {
        return geriatrics;
    }

    public ApplicationInfo geriatrics(Set<Geriatric> geriatrics) {
        this.geriatrics = geriatrics;
        return this;
    }

    public ApplicationInfo addGeriatric(Geriatric geriatric) {
        this.geriatrics.add(geriatric);
        geriatric.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removeGeriatric(Geriatric geriatric) {
        this.geriatrics.remove(geriatric);
        geriatric.setApplicationInfo(null);
        return this;
    }

    public void setGeriatrics(Set<Geriatric> geriatrics) {
        this.geriatrics = geriatrics;
    }

    public Set<BreastFeeding> getBreastfeedings() {
        return breastfeedings;
    }

    public ApplicationInfo breastfeedings(Set<BreastFeeding> breastFeedings) {
        this.breastfeedings = breastFeedings;
        return this;
    }

    public ApplicationInfo addBreastfeeding(BreastFeeding breastFeeding) {
        this.breastfeedings.add(breastFeeding);
        breastFeeding.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removeBreastfeeding(BreastFeeding breastFeeding) {
        this.breastfeedings.remove(breastFeeding);
        breastFeeding.setApplicationInfo(null);
        return this;
    }

    public void setBreastfeedings(Set<BreastFeeding> breastFeedings) {
        this.breastfeedings = breastFeedings;
    }

    public Set<AppInfoSideEffect> getAppInfoSideEffects() {
        return appInfoSideEffects;
    }

    public ApplicationInfo applInfoSideEffects(Set<AppInfoSideEffect> appInfoSideEffects) {
        this.appInfoSideEffects = appInfoSideEffects;
        return this;
    }

    public ApplicationInfo addApplInfoSideEffect(AppInfoSideEffect appInfoSideEffect) {
        this.appInfoSideEffects.add(appInfoSideEffect);
        appInfoSideEffect.setApplicationInfo(this);
        return this;
    }

    public ApplicationInfo removeApplInfoSideEffect(AppInfoSideEffect appInfoSideEffect) {
        this.appInfoSideEffects.remove(appInfoSideEffect);
        appInfoSideEffect.setApplicationInfo(null);
        return this;
    }

    public void setAppInfoSideEffects(Set<AppInfoSideEffect> appInfoSideEffects) {
        this.appInfoSideEffects = appInfoSideEffects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationInfo)) {
            return false;
        }
        return id != null && id.equals(((ApplicationInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApplicationInfo{" +
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
