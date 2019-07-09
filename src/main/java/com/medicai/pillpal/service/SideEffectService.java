package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.*;
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
    Page<GeriatricDTO> findGeriatricByGenericNameList(Pageable pageable, List<String> genericName);

    /**
     * Get a GenericName
     *
     * @param
     * @return persisted entity.
     */
    Optional<GeriatricDTO> findGeriatricByGenericName(String genericName);


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


    /**
     * Get a GenericName
     *
     * @param
     * @return persisted entity.
     */
    Optional<ApplInfoSideEffectDTO> findAppInfoSideEffectByGenericName(String genericName);

    /**
     * Get the List of GenericName
     *
     * @param
     * @return the list of entities.
     */
    Page<ApplInfoSideEffectDTO> findAppInfoSideEffectByGenericNameList(Pageable pageable, List<String> genericNameList);

    /**
     * Get a GenericName
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
