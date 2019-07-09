package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.GeriatricDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.Geriatric}.
 */
public interface GeriatricService {

    /**
     * Save a geriatric.
     *
     * @param geriatricDTO the entity to save.
     * @return the persisted entity.
     */
    GeriatricDTO save(GeriatricDTO geriatricDTO);

    /**
     * Get all the geriatrics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GeriatricDTO> findAll(Pageable pageable);


    /**
     * Get the "id" geriatric.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GeriatricDTO> findOne(Long id);

    /**
     * Delete the "id" geriatric.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get the List of GenericName
     *
     * @param
     * @return the list of entities.
     */
    Page<GeriatricDTO> findGeriatricByGenericNameList(Pageable pageable, List<String> genericName);

    /**
     * Get a GenericName
     *
     * @param
     * @return persisted entity.
     */
    Optional<GeriatricDTO> findGeriatricByGenericName(String genericName);
}
