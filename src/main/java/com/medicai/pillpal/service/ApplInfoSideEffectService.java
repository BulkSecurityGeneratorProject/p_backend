package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.ApplInfoSideEffectDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.ApplInfoSideEffect}.
 */
public interface ApplInfoSideEffectService {

    /**
     * Save a applInfoSideEffect.
     *
     * @param applInfoSideEffectDTO the entity to save.
     * @return the persisted entity.
     */
    ApplInfoSideEffectDTO save(ApplInfoSideEffectDTO applInfoSideEffectDTO);

    /**
     * Get all the applInfoSideEffects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplInfoSideEffectDTO> findAll(Pageable pageable);


    /**
     * Get the "id" applInfoSideEffect.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplInfoSideEffectDTO> findOne(Long id);

    /**
     * Delete the "id" applInfoSideEffect.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
