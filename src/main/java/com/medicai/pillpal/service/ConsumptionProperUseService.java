package com.medicai.pillpal.service;

import com.medicai.pillpal.domain.ConsumptionProperUse;
import com.medicai.pillpal.repository.ConsumptionProperUseRepository;
import com.medicai.pillpal.service.dto.ConsumptionProperUseDTO;
import com.medicai.pillpal.service.mapper.ConsumptionProperUseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConsumptionProperUse}.
 */
@Service
@Transactional
public class ConsumptionProperUseService {

    private final Logger log = LoggerFactory.getLogger(ConsumptionProperUseService.class);

    private final ConsumptionProperUseRepository consumptionProperUseRepository;

    private final ConsumptionProperUseMapper consumptionProperUseMapper;

    public ConsumptionProperUseService(ConsumptionProperUseRepository consumptionProperUseRepository, ConsumptionProperUseMapper consumptionProperUseMapper) {
        this.consumptionProperUseRepository = consumptionProperUseRepository;
        this.consumptionProperUseMapper = consumptionProperUseMapper;
    }

    /**
     * Save a consumptionProperUse.
     *
     * @param consumptionProperUseDTO the entity to save.
     * @return the persisted entity.
     */
    public ConsumptionProperUseDTO save(ConsumptionProperUseDTO consumptionProperUseDTO) {
        log.debug("Request to save ConsumptionProperUse : {}", consumptionProperUseDTO);
        ConsumptionProperUse consumptionProperUse = consumptionProperUseMapper.toEntity(consumptionProperUseDTO);
        consumptionProperUse = consumptionProperUseRepository.save(consumptionProperUse);
        return consumptionProperUseMapper.toDto(consumptionProperUse);
    }

    /**
     * Get all the consumptionProperUses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsumptionProperUseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsumptionProperUses");
        return consumptionProperUseRepository.findAll(pageable)
            .map(consumptionProperUseMapper::toDto);
    }


    /**
     * Get one consumptionProperUse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConsumptionProperUseDTO> findOne(Long id) {
        log.debug("Request to get ConsumptionProperUse : {}", id);
        return consumptionProperUseRepository.findById(id)
            .map(consumptionProperUseMapper::toDto);
    }

    /**
     * Delete the consumptionProperUse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConsumptionProperUse : {}", id);
        consumptionProperUseRepository.deleteById(id);
    }
}
