package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.UseAndStorageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.UseAndStorage}.
 */
public interface UseAndStorageService {

    /**
     * Save a useAndStorage.
     *
     * @param useAndStorageDTO the entity to save.
     * @return the persisted entity.
     */
    UseAndStorageDTO save(UseAndStorageDTO useAndStorageDTO);

    /**
     * Get all the useAndStorages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UseAndStorageDTO> findAll(Pageable pageable);


    /**
     * Get the "id" useAndStorage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UseAndStorageDTO> findOne(Long id);

    /**
     * Delete the "id" useAndStorage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
