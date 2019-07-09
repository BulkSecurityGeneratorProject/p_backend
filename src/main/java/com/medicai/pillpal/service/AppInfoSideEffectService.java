package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.AppInfoSideEffectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.AppInfoSideEffect}.
 */
public interface AppInfoSideEffectService {

    /**
     * Save a appInfoSideEffect.
     *
     * @param appInfoSideEffectDTO the entity to save.
     * @return the persisted entity.
     */
    AppInfoSideEffectDTO save(AppInfoSideEffectDTO appInfoSideEffectDTO);

    /**
     * Get all the appInfoSideEffects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppInfoSideEffectDTO> findAll(Pageable pageable);


    /**
     * Get the "id" appInfoSideEffect.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppInfoSideEffectDTO> findOne(Long id);

    /**
     * Delete the "id" appInfoSideEffect.
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
    Optional<AppInfoSideEffectDTO> findAppInfoSideEffectByGenericName(String genericName);

    /**
     * Get the List of GenericName
     *
     * @param
     * @return the list of entities.
     */
    Page<AppInfoSideEffectDTO> findAppInfoSideEffectByGenericNameList(Pageable pageable, List<String> genericNameList);
}
