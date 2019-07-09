package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.PharmaceuticalCodeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.PharmaceuticalCode}.
 */
public interface PharmaceuticalCodeService {

    /**
     * Save a pharmaceuticalCode.
     *
     * @param pharmaceuticalCodeDTO the entity to save.
     * @return the persisted entity.
     */
    PharmaceuticalCodeDTO save(PharmaceuticalCodeDTO pharmaceuticalCodeDTO);

    /**
     * Get all the pharmaceuticalCodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PharmaceuticalCodeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pharmaceuticalCode.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PharmaceuticalCodeDTO> findOne(Long id);

    /**
     * Delete the "id" pharmaceuticalCode.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
