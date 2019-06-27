package com.medicai.pillpal.service;

import com.medicai.pillpal.domain.ConsumptionPrecoution;
import com.medicai.pillpal.repository.ConsumptionPrecoutionRepository;
import com.medicai.pillpal.service.dto.ConsumptionPrecoutionDTO;
import com.medicai.pillpal.service.mapper.ConsumptionPrecoutionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConsumptionPrecoution}.
 */
@Service
@Transactional
public class ConsumptionPrecoutionService {

    private final Logger log = LoggerFactory.getLogger(ConsumptionPrecoutionService.class);

    private final ConsumptionPrecoutionRepository consumptionPrecoutionRepository;

    private final ConsumptionPrecoutionMapper consumptionPrecoutionMapper;

    public ConsumptionPrecoutionService(ConsumptionPrecoutionRepository consumptionPrecoutionRepository, ConsumptionPrecoutionMapper consumptionPrecoutionMapper) {
        this.consumptionPrecoutionRepository = consumptionPrecoutionRepository;
        this.consumptionPrecoutionMapper = consumptionPrecoutionMapper;
    }

    /**
     * Save a consumptionPrecoution.
     *
     * @param consumptionPrecoutionDTO the entity to save.
     * @return the persisted entity.
     */
    public ConsumptionPrecoutionDTO save(ConsumptionPrecoutionDTO consumptionPrecoutionDTO) {
        log.debug("Request to save ConsumptionPrecoution : {}", consumptionPrecoutionDTO);
        ConsumptionPrecoution consumptionPrecoution = consumptionPrecoutionMapper.toEntity(consumptionPrecoutionDTO);
        consumptionPrecoution = consumptionPrecoutionRepository.save(consumptionPrecoution);
        return consumptionPrecoutionMapper.toDto(consumptionPrecoution);
    }

    /**
     * Get all the consumptionPrecoutions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsumptionPrecoutionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsumptionPrecoutions");
        return consumptionPrecoutionRepository.findAll(pageable)
            .map(consumptionPrecoutionMapper::toDto);
    }


    /**
     * Get one consumptionPrecoution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConsumptionPrecoutionDTO> findOne(Long id) {
        log.debug("Request to get ConsumptionPrecoution : {}", id);
        return consumptionPrecoutionRepository.findById(id)
            .map(consumptionPrecoutionMapper::toDto);
    }

    /**
     * Delete the consumptionPrecoution by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConsumptionPrecoution : {}", id);
        consumptionPrecoutionRepository.deleteById(id);
    }
}
