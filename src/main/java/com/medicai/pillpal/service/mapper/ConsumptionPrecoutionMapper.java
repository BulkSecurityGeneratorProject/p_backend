package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.ConsumptionPrecoution;
import com.medicai.pillpal.service.dto.ConsumptionPrecoutionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ConsumptionPrecoution} and its DTO {@link ConsumptionPrecoutionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface ConsumptionPrecoutionMapper extends EntityMapper<ConsumptionPrecoutionDTO, ConsumptionPrecoution> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    ConsumptionPrecoutionDTO toDto(ConsumptionPrecoution consumptionPrecoution);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    ConsumptionPrecoution toEntity(ConsumptionPrecoutionDTO consumptionPrecoutionDTO);

    default ConsumptionPrecoution fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConsumptionPrecoution consumptionPrecoution = new ConsumptionPrecoution();
        consumptionPrecoution.setId(id);
        return consumptionPrecoution;
    }
}
