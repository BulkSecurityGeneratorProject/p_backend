package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.service.PregnancyService;
import com.medicai.pillpal.domain.Pregnancy;
import com.medicai.pillpal.repository.PregnancyRepository;
import com.medicai.pillpal.service.dto.PregnancyDTO;
import com.medicai.pillpal.service.mapper.PregnancyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pregnancy}.
 */
@Service
@Transactional
public class PregnancyServiceImpl implements PregnancyService {

    private final Logger log = LoggerFactory.getLogger(PregnancyServiceImpl.class);

    private final PregnancyRepository pregnancyRepository;

    private final PregnancyMapper pregnancyMapper;

    public PregnancyServiceImpl(PregnancyRepository pregnancyRepository, PregnancyMapper pregnancyMapper) {
        this.pregnancyRepository = pregnancyRepository;
        this.pregnancyMapper = pregnancyMapper;
    }

    /**
     * Save a pregnancy.
     *
     * @param pregnancyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PregnancyDTO save(PregnancyDTO pregnancyDTO) {
        log.debug("Request to save Pregnancy : {}", pregnancyDTO);
        Pregnancy pregnancy = pregnancyMapper.toEntity(pregnancyDTO);
        pregnancy = pregnancyRepository.save(pregnancy);
        return pregnancyMapper.toDto(pregnancy);
    }

    /**
     * Get all the pregnancies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PregnancyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pregnancies");
        return pregnancyRepository.findAll(pageable)
            .map(pregnancyMapper::toDto);
    }


    /**
     * Get one pregnancy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PregnancyDTO> findOne(Long id) {
        log.debug("Request to get Pregnancy : {}", id);
        return pregnancyRepository.findById(id)
            .map(pregnancyMapper::toDto);
    }

    /**
     * Delete the pregnancy by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pregnancy : {}", id);
        pregnancyRepository.deleteById(id);
    }
}
