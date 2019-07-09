package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.ConsumptionProperUse;
import com.medicai.pillpal.service.dto.ConsumptionProperUseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ConsumptionProperUse} and its DTO {@link ConsumptionProperUseDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface ConsumptionProperUseMapper extends EntityMapper<ConsumptionProperUseDTO, ConsumptionProperUse> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    ConsumptionProperUseDTO toDto(ConsumptionProperUse consumptionProperUse);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    ConsumptionProperUse toEntity(ConsumptionProperUseDTO consumptionProperUseDTO);

    default ConsumptionProperUse fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConsumptionProperUse consumptionProperUse = new ConsumptionProperUse();
        consumptionProperUse.setId(id);
        return consumptionProperUse;
    }
}
