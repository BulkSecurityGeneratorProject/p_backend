package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.domain.ConsumptionMissedDose;
import com.medicai.pillpal.repository.ConsumptionMissedDoseRepository;
import com.medicai.pillpal.service.ConsumptionMissedDoseService;
import com.medicai.pillpal.service.dto.ConsumptionMissedDoseDTO;
import com.medicai.pillpal.service.mapper.ConsumptionMissedDoseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConsumptionMissedDose}.
 */
@Service
@Transactional
public class ConsumptionMissedDoseServiceImpl implements ConsumptionMissedDoseService {

    private final Logger log = LoggerFactory.getLogger(ConsumptionMissedDoseServiceImpl.class);

    private final ConsumptionMissedDoseRepository consumptionMissedDoseRepository;

    private final ConsumptionMissedDoseMapper consumptionMissedDoseMapper;

    public ConsumptionMissedDoseServiceImpl(ConsumptionMissedDoseRepository consumptionMissedDoseRepository, ConsumptionMissedDoseMapper consumptionMissedDoseMapper) {
        this.consumptionMissedDoseRepository = consumptionMissedDoseRepository;
        this.consumptionMissedDoseMapper = consumptionMissedDoseMapper;
    }

    /**
     * Save a consumptionMissedDose.
     *
     * @param consumptionMissedDoseDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConsumptionMissedDoseDTO save(ConsumptionMissedDoseDTO consumptionMissedDoseDTO) {
        log.debug("Request to save ConsumptionMissedDose : {}", consumptionMissedDoseDTO);
        ConsumptionMissedDose consumptionMissedDose = consumptionMissedDoseMapper.toEntity(consumptionMissedDoseDTO);
        consumptionMissedDose = consumptionMissedDoseRepository.save(consumptionMissedDose);
        return consumptionMissedDoseMapper.toDto(consumptionMissedDose);
    }

    /**
     * Get all the consumptionMissedDoses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsumptionMissedDoseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsumptionMissedDoses");
        return consumptionMissedDoseRepository.findAll(pageable)
            .map(consumptionMissedDoseMapper::toDto);
    }


    /**
     * Get one consumptionMissedDose by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConsumptionMissedDoseDTO> findOne(Long id) {
        log.debug("Request to get ConsumptionMissedDose : {}", id);
        return consumptionMissedDoseRepository.findById(id)
            .map(consumptionMissedDoseMapper::toDto);
    }

    /**
     * Delete the consumptionMissedDose by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConsumptionMissedDose : {}", id);
        consumptionMissedDoseRepository.deleteById(id);
    }
}
