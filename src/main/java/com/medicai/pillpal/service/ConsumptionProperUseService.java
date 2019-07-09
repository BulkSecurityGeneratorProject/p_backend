package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.ConsumptionProperUseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.ConsumptionProperUse}.
 */
public interface ConsumptionProperUseService {

    /**
     * Save a consumptionProperUse.
     *
     * @param consumptionProperUseDTO the entity to save.
     * @return the persisted entity.
     */
    ConsumptionProperUseDTO save(ConsumptionProperUseDTO consumptionProperUseDTO);

    /**
     * Get all the consumptionProperUses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsumptionProperUseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" consumptionProperUse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsumptionProperUseDTO> findOne(Long id);

    /**
     * Delete the "id" consumptionProperUse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
