package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.*;
import com.medicai.pillpal.service.dto.ApplicationInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationInfo} and its DTO {@link ApplicationInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationInfoMapper extends EntityMapper<ApplicationInfoDTO, ApplicationInfo> {


    @Mapping(target = "productionInfos", ignore = true)
    @Mapping(target = "removeProductionInfo", ignore = true)
    @Mapping(target = "pharmaceuticalcodes", ignore = true)
    @Mapping(target = "removePharmaceuticalcode", ignore = true)
    @Mapping(target = "dosings", ignore = true)
    @Mapping(target = "removeDosing", ignore = true)
    @Mapping(target = "missedDoses", ignore = true)
    @Mapping(target = "removeMissedDose", ignore = true)
    @Mapping(target = "useAndStorages", ignore = true)
    @Mapping(target = "removeUseAndStorage", ignore = true)
    @Mapping(target = "beforeUses", ignore = true)
    @Mapping(target = "removeBeforeUse", ignore = true)
    @Mapping(target = "precoutions", ignore = true)
    @Mapping(target = "removePrecoution", ignore = true)
    @Mapping(target = "properUses", ignore = true)
    @Mapping(target = "removeProperUse", ignore = true)
    @Mapping(target = "pregnancies", ignore = true)
    @Mapping(target = "removePregnancy", ignore = true)
    @Mapping(target = "allergies", ignore = true)
    @Mapping(target = "removeAllergy", ignore = true)
    @Mapping(target = "pediatrics", ignore = true)
    @Mapping(target = "removePediatric", ignore = true)
    @Mapping(target = "geriatrics", ignore = true)
    @Mapping(target = "removeGeriatric", ignore = true)
    @Mapping(target = "breastfeedings", ignore = true)
    @Mapping(target = "removeBreastfeeding", ignore = true)
    @Mapping(target = "applInfoSideEffects", ignore = true)
    @Mapping(target = "removeApplInfoSideEffect", ignore = true)
    ApplicationInfo toEntity(ApplicationInfoDTO applicationInfoDTO);

    default ApplicationInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setId(id);
        return applicationInfo;
    }
}
