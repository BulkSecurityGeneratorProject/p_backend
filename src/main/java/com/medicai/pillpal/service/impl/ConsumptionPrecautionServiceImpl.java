package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.domain.ConsumptionPrecaution;
import com.medicai.pillpal.repository.ConsumptionPrecautionRepository;
import com.medicai.pillpal.service.ConsumptionPrecautionService;
import com.medicai.pillpal.service.dto.ConsumptionPrecautionDTO;
import com.medicai.pillpal.service.mapper.ConsumptionPrecautionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ConsumptionPrecaution}.
 */
@Service
@Transactional
public class ConsumptionPrecautionServiceImpl implements ConsumptionPrecautionService {

    private final Logger log = LoggerFactory.getLogger(ConsumptionPrecautionServiceImpl.class);

    private final ConsumptionPrecautionRepository consumptionPrecautionRepository;

    private final ConsumptionPrecautionMapper consumptionPrecautionMapper;

    public ConsumptionPrecautionServiceImpl(ConsumptionPrecautionRepository consumptionPrecautionRepository, ConsumptionPrecautionMapper consumptionPrecautionMapper) {
        this.consumptionPrecautionRepository = consumptionPrecautionRepository;
        this.consumptionPrecautionMapper = consumptionPrecautionMapper;
    }

    /**
     * Save a consumptionPrecaution.
     *
     * @param consumptionPrecautionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConsumptionPrecautionDTO save(ConsumptionPrecautionDTO consumptionPrecautionDTO) {
        log.debug("Request to save ConsumptionPrecaution : {}", consumptionPrecautionDTO);
        ConsumptionPrecaution consumptionPrecaution = consumptionPrecautionMapper.toEntity(consumptionPrecautionDTO);
        consumptionPrecaution = consumptionPrecautionRepository.save(consumptionPrecaution);
        return consumptionPrecautionMapper.toDto(consumptionPrecaution);
    }

    /**
     * Get all the consumptionPrecautions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsumptionPrecautionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsumptionPrecautions");
        return consumptionPrecautionRepository.findAll(pageable)
            .map(consumptionPrecautionMapper::toDto);
    }


    /**
     * Get one consumptionPrecaution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConsumptionPrecautionDTO> findOne(Long id) {
        log.debug("Request to get ConsumptionPrecaution : {}", id);
        return consumptionPrecautionRepository.findById(id)
            .map(consumptionPrecautionMapper::toDto);
    }

    /**
     * Delete the consumptionPrecaution by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConsumptionPrecaution : {}", id);
        consumptionPrecautionRepository.deleteById(id);
    }

    /**
     * get a generic names
     *
     * @param genericName
     * @return a persisted entity
     */
    @Override
    public Optional<ConsumptionPrecautionDTO> findAllergyByGenericName(String genericName) {
        log.debug("Request to get ApplicationInfo : {}", genericName);
        return consumptionPrecautionRepository.findByGenericName(genericName)
            .map(consumptionPrecautionMapper::toDto);
    }

    /**
     * get a list of generic names
     *
     * @param pageable
     * @param genericNameList
     * @return list of persisted entities
     */
    @Override
    public Page<ConsumptionPrecautionDTO> findAllergyByGenericNameList(Pageable pageable, List<String> genericNameList) {
        log.debug("Request to get ApplicationInfo : {}", pageable);
        return consumptionPrecautionRepository.findByGenericNameList(pageable, genericNameList)
            .map(consumptionPrecautionMapper::toDto);
    }
}
