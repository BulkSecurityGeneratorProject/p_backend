package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.AppInfoSideEffect;
import com.medicai.pillpal.service.dto.ApplInfoSideEffectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AppInfoSideEffect} and its DTO {@link ApplInfoSideEffectDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class, SideEffectMapper.class})
public interface ApplInfoSideEffectMapper extends EntityMapper<ApplInfoSideEffectDTO, AppInfoSideEffect> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    @Mapping(source = "sideEffect.id", target = "sideEffectId")
    ApplInfoSideEffectDTO toDto(AppInfoSideEffect appInfoSideEffect);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    @Mapping(source = "sideEffectId", target = "sideEffect")
    AppInfoSideEffect toEntity(ApplInfoSideEffectDTO applInfoSideEffectDTO);

    default AppInfoSideEffect fromId(Long id) {
        if (id == null) {
            return null;
        }
        AppInfoSideEffect appInfoSideEffect = new AppInfoSideEffect();
        appInfoSideEffect.setId(id);
        return appInfoSideEffect;
    }
}
