package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.*;
import com.medicai.pillpal.service.dto.ConsumptionDosingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConsumptionDosing} and its DTO {@link ConsumptionDosingDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface ConsumptionDosingMapper extends EntityMapper<ConsumptionDosingDTO, ConsumptionDosing> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    ConsumptionDosingDTO toDto(ConsumptionDosing consumptionDosing);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    ConsumptionDosing toEntity(ConsumptionDosingDTO consumptionDosingDTO);

    default ConsumptionDosing fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConsumptionDosing consumptionDosing = new ConsumptionDosing();
        consumptionDosing.setId(id);
        return consumptionDosing;
    }
}
