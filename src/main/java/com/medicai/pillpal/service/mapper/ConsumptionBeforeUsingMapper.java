package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.*;
import com.medicai.pillpal.service.dto.ConsumptionBeforeUsingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConsumptionBeforeUsing} and its DTO {@link ConsumptionBeforeUsingDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface ConsumptionBeforeUsingMapper extends EntityMapper<ConsumptionBeforeUsingDTO, ConsumptionBeforeUsing> {

    @Mapping(source = "applicationInfo.id", target = "applicationInfoId")
    ConsumptionBeforeUsingDTO toDto(ConsumptionBeforeUsing consumptionBeforeUsing);

    @Mapping(source = "applicationInfoId", target = "applicationInfo")
    ConsumptionBeforeUsing toEntity(ConsumptionBeforeUsingDTO consumptionBeforeUsingDTO);

    default ConsumptionBeforeUsing fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConsumptionBeforeUsing consumptionBeforeUsing = new ConsumptionBeforeUsing();
        consumptionBeforeUsing.setId(id);
        return consumptionBeforeUsing;
    }
}
