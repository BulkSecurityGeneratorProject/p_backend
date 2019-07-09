package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.ConsumptionMissedDose;
import com.medicai.pillpal.service.dto.ConsumptionMissedDoseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ConsumptionMissedDose} and its DTO {@link ConsumptionMissedDoseDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface ConsumptionMissedDoseMapper extends EntityMapper<ConsumptionMissedDoseDTO, ConsumptionMissedDose> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    ConsumptionMissedDoseDTO toDto(ConsumptionMissedDose consumptionMissedDose);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    ConsumptionMissedDose toEntity(ConsumptionMissedDoseDTO consumptionMissedDoseDTO);

    default ConsumptionMissedDose fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConsumptionMissedDose consumptionMissedDose = new ConsumptionMissedDose();
        consumptionMissedDose.setId(id);
        return consumptionMissedDose;
    }
}
