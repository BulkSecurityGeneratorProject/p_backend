package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.*;
import com.medicai.pillpal.service.dto.PediatricDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pediatric} and its DTO {@link PediatricDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface PediatricMapper extends EntityMapper<PediatricDTO, Pediatric> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    PediatricDTO toDto(Pediatric pediatric);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    Pediatric toEntity(PediatricDTO pediatricDTO);

    default Pediatric fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pediatric pediatric = new Pediatric();
        pediatric.setId(id);
        return pediatric;
    }
}
