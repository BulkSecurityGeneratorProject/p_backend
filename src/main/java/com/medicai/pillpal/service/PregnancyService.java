package com.medicai.pillpal.service;

import com.medicai.pillpal.domain.Pregnancy;
import com.medicai.pillpal.service.dto.PregnancyDTO;

import com.medicai.pillpal.service.dto.ProductionInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.Pregnancy}.
 */
public interface PregnancyService {

    /**
     * Save a pregnancy.
     *
     * @param pregnancyDTO the entity to save.
     * @return the persisted entity.
     */
    PregnancyDTO save(PregnancyDTO pregnancyDTO);

    /**
     * Get all the pregnancies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PregnancyDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pregnancy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PregnancyDTO> findOne(Long id);

    /**
     * Delete the "id" pregnancy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get all the productionInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PregnancyDTO> findByGenericNameList(Pageable pageable, List<String> genericNameList);


    /**
     * Get the "id" productionInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PregnancyDTO> findByGenericName(String genericName);


}
