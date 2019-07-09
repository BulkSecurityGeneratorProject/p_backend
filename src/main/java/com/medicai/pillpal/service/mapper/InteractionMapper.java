package com.medicai.pillpal.service.mapper;

import com.medicai.pillpal.domain.Interaction;
import com.medicai.pillpal.service.dto.InteractionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Interaction} and its DTO {@link InteractionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InteractionMapper extends EntityMapper<InteractionDTO, Interaction> {



    default Interaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Interaction interaction = new Interaction();
        interaction.setId(id);
        return interaction;
    }
}
