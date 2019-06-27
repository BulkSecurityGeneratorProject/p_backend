package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.*;
import com.medicai.pillpal.service.dto.AllergyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Allergy} and its DTO {@link AllergyDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface AllergyMapper extends EntityMapper<AllergyDTO, Allergy> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    AllergyDTO toDto(Allergy allergy);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    Allergy toEntity(AllergyDTO allergyDTO);

    default Allergy fromId(Long id) {
        if (id == null) {
            return null;
        }
        Allergy allergy = new Allergy();
        allergy.setId(id);
        return allergy;
    }
}
