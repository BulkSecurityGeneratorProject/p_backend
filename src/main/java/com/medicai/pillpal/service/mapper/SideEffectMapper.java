package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.SideEffect;
import com.medicai.pillpal.service.dto.SideEffectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link SideEffect} and its DTO {@link SideEffectDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SideEffectMapper extends EntityMapper<SideEffectDTO, SideEffect> {


    @Mapping(target = "sideEffets", ignore = true)
    @Mapping(target = "removeSideEffet", ignore = true)
    SideEffect toEntity(SideEffectDTO sideEffectDTO);

    default SideEffect fromId(Long id) {
        if (id == null) {
            return null;
        }
        SideEffect sideEffect = new SideEffect();
        sideEffect.setId(id);
        return sideEffect;
    }
}
