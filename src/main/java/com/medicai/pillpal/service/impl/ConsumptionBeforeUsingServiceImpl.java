package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.domain.ConsumptionBeforeUsing;
import com.medicai.pillpal.repository.ConsumptionBeforeUsingRepository;
import com.medicai.pillpal.service.ConsumptionBeforeUsingService;
import com.medicai.pillpal.service.dto.ConsumptionBeforeUsingDTO;
import com.medicai.pillpal.service.mapper.ConsumptionBeforeUsingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConsumptionBeforeUsing}.
 */
@Service
@Transactional
public class ConsumptionBeforeUsingServiceImpl implements ConsumptionBeforeUsingService {

    private final Logger log = LoggerFactory.getLogger(ConsumptionBeforeUsingServiceImpl.class);

    private final ConsumptionBeforeUsingRepository consumptionBeforeUsingRepository;

    private final ConsumptionBeforeUsingMapper consumptionBeforeUsingMapper;

    public ConsumptionBeforeUsingServiceImpl(ConsumptionBeforeUsingRepository consumptionBeforeUsingRepository, ConsumptionBeforeUsingMapper consumptionBeforeUsingMapper) {
        this.consumptionBeforeUsingRepository = consumptionBeforeUsingRepository;
        this.consumptionBeforeUsingMapper = consumptionBeforeUsingMapper;
    }

    /**
     * Save a consumptionBeforeUsing.
     *
     * @param consumptionBeforeUsingDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConsumptionBeforeUsingDTO save(ConsumptionBeforeUsingDTO consumptionBeforeUsingDTO) {
        log.debug("Request to save ConsumptionBeforeUsing : {}", consumptionBeforeUsingDTO);
        ConsumptionBeforeUsing consumptionBeforeUsing = consumptionBeforeUsingMapper.toEntity(consumptionBeforeUsingDTO);
        consumptionBeforeUsing = consumptionBeforeUsingRepository.save(consumptionBeforeUsing);
        return consumptionBeforeUsingMapper.toDto(consumptionBeforeUsing);
    }

    /**
     * Get all the consumptionBeforeUsings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsumptionBeforeUsingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsumptionBeforeUsings");
        return consumptionBeforeUsingRepository.findAll(pageable)
            .map(consumptionBeforeUsingMapper::toDto);
    }


    /**
     * Get one consumptionBeforeUsing by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConsumptionBeforeUsingDTO> findOne(Long id) {
        log.debug("Request to get ConsumptionBeforeUsing : {}", id);
        return consumptionBeforeUsingRepository.findById(id)
            .map(consumptionBeforeUsingMapper::toDto);
    }

    /**
     * Delete the consumptionBeforeUsing by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConsumptionBeforeUsing : {}", id);
        consumptionBeforeUsingRepository.deleteById(id);
    }
}
