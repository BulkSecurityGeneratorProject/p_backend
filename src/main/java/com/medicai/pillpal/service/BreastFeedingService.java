package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.BreastFeedingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.BreastFeeding}.
 */
public interface BreastFeedingService {

    /**
     * Save a breastFeeding.
     *
     * @param breastFeedingDTO the entity to save.
     * @return the persisted entity.
     */
    BreastFeedingDTO save(BreastFeedingDTO breastFeedingDTO);

    /**
     * Get all the breastFeedings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BreastFeedingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" breastFeeding.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BreastFeedingDTO> findOne(Long id);

    /**
     * Delete the "id" breastFeeding.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get a GenericName
     *
     * @param genericName
     * @return the list of entities.
     */
    Optional<BreastFeedingDTO> findBreastFeedingByGenericName(String genericName);

    /**
     * Get the List of GenericName
     *
     * @param
     * @return the list of entities.
     */
    Page<BreastFeedingDTO> findBreastFeedingByGenericNameList(Pageable pageable, List<String> genericNameList);
}
