package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.ConsumptionPrecaution;
import com.medicai.pillpal.service.dto.ConsumptionPrecautionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ConsumptionPrecaution} and its DTO {@link ConsumptionPrecautionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface ConsumptionPrecautionMapper extends EntityMapper<ConsumptionPrecautionDTO, ConsumptionPrecaution> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    ConsumptionPrecautionDTO toDto(ConsumptionPrecaution consumptionPrecaution);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    ConsumptionPrecaution toEntity(ConsumptionPrecautionDTO consumptionPrecautionDTO);

    default ConsumptionPrecaution fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConsumptionPrecaution consumptionPrecaution = new ConsumptionPrecaution();
        consumptionPrecaution.setId(id);
        return consumptionPrecaution;
    }
}
