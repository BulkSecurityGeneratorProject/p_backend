package com.medicai.pillpal.service;

import com.medicai.pillpal.service.dto.ProductionInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.ProductionInfo}.
 */
public interface ProductionInfoService {

    /**
     * Save a productionInfo.
     *
     * @param productionInfoDTO the entity to save.
     * @return the persisted entity.
     */
    ProductionInfoDTO save(ProductionInfoDTO productionInfoDTO);

    /**
     * Get all the productionInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductionInfoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productionInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductionInfoDTO> findOne(Long id);

    /**
     * Delete the "id" productionInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
