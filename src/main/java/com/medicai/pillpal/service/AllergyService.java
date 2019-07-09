package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.AllergyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.Allergy}.
 */
public interface AllergyService {

    /**
     * Save a allergy.
     *
     * @param allergyDTO the entity to save.
     * @return the persisted entity.
     */
    AllergyDTO save(AllergyDTO allergyDTO);

    /**
     * Get all the allergies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AllergyDTO> findAll(Pageable pageable);


    /**
     * Get the "id" allergy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AllergyDTO> findOne(Long id);

    /**
     * Delete the "id" allergy.
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
    Optional<AllergyDTO> findAllergyByGenericName(String genericName);

    /**
     * Get the List of GenericName
     *
     * @param
     * @return the list of entities.
     */
    Page<AllergyDTO> findAllergyByGenericNameList(Pageable pageable, List<String> genericNameList);

}
