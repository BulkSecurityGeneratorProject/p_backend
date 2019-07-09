package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.*;
import com.medicai.pillpal.service.dto.InteractionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Interaction} and its DTO {@link InteractionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationInfoMapper.class})
public interface InteractionMapper extends EntityMapper<InteractionDTO, Interaction> {

    @Mapping(source = "baseApplicationInfo.id", target = "baseApplicationInfoId")
    @Mapping(source = "descApplicationInfo.id", target = "descApplicationInfoId")
    InteractionDTO toDto(Interaction interaction);

    @Mapping(source = "baseApplicationInfoId", target = "baseApplicationInfo")
    @Mapping(source = "descApplicationInfoId", target = "descApplicationInfo")
    Interaction toEntity(InteractionDTO interactionDTO);

    default Interaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Interaction interaction = new Interaction();
        interaction.setId(id);
        return interaction;
    }
}
