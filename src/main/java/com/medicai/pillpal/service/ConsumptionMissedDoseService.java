package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.ConsumptionMissedDoseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.ConsumptionMissedDose}.
 */
public interface ConsumptionMissedDoseService {

    /**
     * Save a consumptionMissedDose.
     *
     * @param consumptionMissedDoseDTO the entity to save.
     * @return the persisted entity.
     */
    ConsumptionMissedDoseDTO save(ConsumptionMissedDoseDTO consumptionMissedDoseDTO);

    /**
     * Get all the consumptionMissedDoses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsumptionMissedDoseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" consumptionMissedDose.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsumptionMissedDoseDTO> findOne(Long id);

    /**
     * Delete the "id" consumptionMissedDose.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
