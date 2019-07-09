package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.ConsumptionPrecautionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.ConsumptionPrecaution}.
 */
public interface ConsumptionPrecautionService {

    /**
     * Save a consumptionPrecaution.
     *
     * @param consumptionPrecautionDTO the entity to save.
     * @return the persisted entity.
     */
    ConsumptionPrecautionDTO save(ConsumptionPrecautionDTO consumptionPrecautionDTO);

    /**
     * Get all the consumptionPrecautions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsumptionPrecautionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" consumptionPrecaution.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsumptionPrecautionDTO> findOne(Long id);

    /**
     * Delete the "id" consumptionPrecaution.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
