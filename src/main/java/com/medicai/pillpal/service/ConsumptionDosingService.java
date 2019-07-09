package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.ConsumptionDosingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.ConsumptionDosing}.
 */
public interface ConsumptionDosingService {

    /**
     * Save a consumptionDosing.
     *
     * @param consumptionDosingDTO the entity to save.
     * @return the persisted entity.
     */
    ConsumptionDosingDTO save(ConsumptionDosingDTO consumptionDosingDTO);

    /**
     * Get all the consumptionDosings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsumptionDosingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" consumptionDosing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsumptionDosingDTO> findOne(Long id);

    /**
     * Delete the "id" consumptionDosing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
