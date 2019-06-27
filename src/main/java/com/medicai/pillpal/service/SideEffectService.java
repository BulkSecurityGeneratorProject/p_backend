package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.SideEffectDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.SideEffect}.
 */
public interface SideEffectService {

    /**
     * Save a sideEffect.
     *
     * @param sideEffectDTO the entity to save.
     * @return the persisted entity.
     */
    SideEffectDTO save(SideEffectDTO sideEffectDTO);

    /**
     * Get all the sideEffects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SideEffectDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sideEffect.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SideEffectDTO> findOne(Long id);

    /**
     * Delete the "id" sideEffect.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
