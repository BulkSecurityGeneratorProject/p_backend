package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.domain.ConsumptionDosing;
import com.medicai.pillpal.repository.ConsumptionDosingRepository;
import com.medicai.pillpal.service.ConsumptionDosingService;
import com.medicai.pillpal.service.dto.ConsumptionDosingDTO;
import com.medicai.pillpal.service.mapper.ConsumptionDosingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConsumptionDosing}.
 */
@Service
@Transactional
public class ConsumptionDosingServiceImpl implements ConsumptionDosingService {

    private final Logger log = LoggerFactory.getLogger(ConsumptionDosingServiceImpl.class);

    private final ConsumptionDosingRepository consumptionDosingRepository;

    private final ConsumptionDosingMapper consumptionDosingMapper;

    public ConsumptionDosingServiceImpl(ConsumptionDosingRepository consumptionDosingRepository, ConsumptionDosingMapper consumptionDosingMapper) {
        this.consumptionDosingRepository = consumptionDosingRepository;
        this.consumptionDosingMapper = consumptionDosingMapper;
    }

    /**
     * Save a consumptionDosing.
     *
     * @param consumptionDosingDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConsumptionDosingDTO save(ConsumptionDosingDTO consumptionDosingDTO) {
        log.debug("Request to save ConsumptionDosing : {}", consumptionDosingDTO);
        ConsumptionDosing consumptionDosing = consumptionDosingMapper.toEntity(consumptionDosingDTO);
        consumptionDosing = consumptionDosingRepository.save(consumptionDosing);
        return consumptionDosingMapper.toDto(consumptionDosing);
    }

    /**
     * Get all the consumptionDosings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsumptionDosingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsumptionDosings");
        return consumptionDosingRepository.findAll(pageable)
            .map(consumptionDosingMapper::toDto);
    }


    /**
     * Get one consumptionDosing by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConsumptionDosingDTO> findOne(Long id) {
        log.debug("Request to get ConsumptionDosing : {}", id);
        return consumptionDosingRepository.findById(id)
            .map(consumptionDosingMapper::toDto);
    }

    /**
     * Delete the consumptionDosing by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConsumptionDosing : {}", id);
        consumptionDosingRepository.deleteById(id);
    }
}
