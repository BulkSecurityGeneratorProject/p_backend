package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.*;
import com.medicai.pillpal.service.dto.ApplInfoSideEffectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplInfoSideEffect} and its DTO {@link ApplInfoSideEffectDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class, SideEffectMapper.class})
public interface ApplInfoSideEffectMapper extends EntityMapper<ApplInfoSideEffectDTO, ApplInfoSideEffect> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    @Mapping(source = "sideEffect.id", target = "sideEffectId")
    ApplInfoSideEffectDTO toDto(ApplInfoSideEffect applInfoSideEffect);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    @Mapping(source = "sideEffectId", target = "sideEffect")
    ApplInfoSideEffect toEntity(ApplInfoSideEffectDTO applInfoSideEffectDTO);

    default ApplInfoSideEffect fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplInfoSideEffect applInfoSideEffect = new ApplInfoSideEffect();
        applInfoSideEffect.setId(id);
        return applInfoSideEffect;
    }
}
