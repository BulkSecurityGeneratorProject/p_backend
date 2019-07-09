package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.*;
import com.medicai.pillpal.service.dto.PregnancyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pregnancy} and its DTO {@link PregnancyDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface PregnancyMapper extends EntityMapper<PregnancyDTO, Pregnancy> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    PregnancyDTO toDto(Pregnancy pregnancy);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    Pregnancy toEntity(PregnancyDTO pregnancyDTO);

    default Pregnancy fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pregnancy pregnancy = new Pregnancy();
        pregnancy.setId(id);
        return pregnancy;
    }
}
