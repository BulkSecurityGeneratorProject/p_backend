package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.PediatricDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.Pediatric}.
 */
public interface PediatricService {

    /**
     * Save a pediatric.
     *
     * @param pediatricDTO the entity to save.
     * @return the persisted entity.
     */
    PediatricDTO save(PediatricDTO pediatricDTO);

    /**
     * Get all the pediatrics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PediatricDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pediatric.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PediatricDTO> findOne(Long id);

    /**
     * Delete the "id" pediatric.
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
    Optional<PediatricDTO> findPediatricByGenericName(String genericName);

    /**
     * Get the List of GenericName
     *
     * @param
     * @return the list of entities.
     */
    Page<PediatricDTO> findPediatricByGenericNameList(Pageable pageable, List<String> genericNameList);

}
