package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.InteractionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.Interaction}.
 */
public interface InteractionService {

    /**
     * Save a interaction.
     *
     * @param interactionDTO the entity to save.
     * @return the persisted entity.
     */
    InteractionDTO save(InteractionDTO interactionDTO);

    /**
     * Get all the interactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InteractionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" interaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InteractionDTO> findOne(Long id);

    /**
     * Delete the "id" interaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * get a generic name
     * @param genericName
     * @return a list of persisted entity
     */
    Optional<InteractionDTO> findInteractionByGenericName(String genericName);

    /**
     * get a generic name
     * @param pageable
     * @return a list of persisted entity
     */
    Page<InteractionDTO> findInteractionByGenericNameList(Pageable pageable , List<String> genericName);
}
