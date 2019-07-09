package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.*;
import com.medicai.pillpal.service.dto.BreastFeedingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BreastFeeding} and its DTO {@link BreastFeedingDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface BreastFeedingMapper extends EntityMapper<BreastFeedingDTO, BreastFeeding> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    BreastFeedingDTO toDto(BreastFeeding breastFeeding);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    BreastFeeding toEntity(BreastFeedingDTO breastFeedingDTO);

    default BreastFeeding fromId(Long id) {
        if (id == null) {
            return null;
        }
        BreastFeeding breastFeeding = new BreastFeeding();
        breastFeeding.setId(id);
        return breastFeeding;
    }
}
