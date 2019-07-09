package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.*;
import com.medicai.pillpal.service.dto.PharmaceuticalCodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PharmaceuticalCode} and its DTO {@link PharmaceuticalCodeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface PharmaceuticalCodeMapper extends EntityMapper<PharmaceuticalCodeDTO, PharmaceuticalCode> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    PharmaceuticalCodeDTO toDto(PharmaceuticalCode pharmaceuticalCode);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    PharmaceuticalCode toEntity(PharmaceuticalCodeDTO pharmaceuticalCodeDTO);

    default PharmaceuticalCode fromId(Long id) {
        if (id == null) {
            return null;
        }
        PharmaceuticalCode pharmaceuticalCode = new PharmaceuticalCode();
        pharmaceuticalCode.setId(id);
        return pharmaceuticalCode;
    }
}
