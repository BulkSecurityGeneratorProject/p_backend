package com.medicai.pillpal.service;

import com.medicai.pillpal.domain.Geriatric;
import com.medicai.pillpal.service.dto.AllergyDTO;
import com.medicai.pillpal.service.dto.GeriatricDTO;
import com.medicai.pillpal.service.dto.SideEffectDTO;

import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.SideEffect}.
 */
public interface SideEffectService {

    /**
     * Save a sideEffect.
     *
     * @param sideEffectDTO the entity to save.
     * @return the persisted entity.
     */
    SideEffectDTO save(SideEffectDTO sideEffectDTO);

    /**
     * Get all the sideEffects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SideEffectDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sideEffect.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SideEffectDTO> findOne(Long id);

    /**
     * Delete the "id" sideEffect.
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


    /**
     * Get the List of GenericName
     *
     * @param
     * @return the list of entities.
     */
    Page<GeriatricDTO> findGeriatricByGenericName(Pageable pageable,List<String> genericName);

    Optional<GeriatricDTO> findGeriatricByGenericName(String genericName);

}
