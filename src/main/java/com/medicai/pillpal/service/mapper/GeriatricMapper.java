package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.*;
import com.medicai.pillpal.service.dto.GeriatricDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Geriatric} and its DTO {@link GeriatricDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface GeriatricMapper extends EntityMapper<GeriatricDTO, Geriatric> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    GeriatricDTO toDto(Geriatric geriatric);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    Geriatric toEntity(GeriatricDTO geriatricDTO);

    default Geriatric fromId(Long id) {
        if (id == null) {
            return null;
        }
        Geriatric geriatric = new Geriatric();
        geriatric.setId(id);
        return geriatric;
    }
}
