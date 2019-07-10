package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.ConsumptionBeforeUsingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.ConsumptionBeforeUsing}.
 */
public interface ConsumptionBeforeUsingService {

    /**
     * Save a consumptionBeforeUsing.
     *
     * @param consumptionBeforeUsingDTO the entity to save.
     * @return the persisted entity.
     */
    ConsumptionBeforeUsingDTO save(ConsumptionBeforeUsingDTO consumptionBeforeUsingDTO);

    /**
     * Get all the consumptionBeforeUsings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsumptionBeforeUsingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" consumptionBeforeUsing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsumptionBeforeUsingDTO> findOne(Long id);

    /**
     * Delete the "id" consumptionBeforeUsing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get a GenericName
     *
     * @param
     * @return persisted entity.
     */
    Optional<ConsumptionBeforeUsingDTO> findAllergyByGenericName(String genericName);

    /**
     * Get the List of GenericName
     *
     * @param
     * @return the list of entities.
     */
    Page<ConsumptionBeforeUsingDTO> findAllergyByGenericNameList(Pageable pageable, List<String> genericNameList);

}
