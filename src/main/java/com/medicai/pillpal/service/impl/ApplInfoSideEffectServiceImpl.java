package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.service.ApplInfoSideEffectService;
import com.medicai.pillpal.domain.ApplInfoSideEffect;
import com.medicai.pillpal.repository.ApplInfoSideEffectRepository;
import com.medicai.pillpal.service.dto.ApplInfoSideEffectDTO;
import com.medicai.pillpal.service.mapper.ApplInfoSideEffectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplInfoSideEffect}.
 */
@Service
@Transactional
public class ApplInfoSideEffectServiceImpl implements ApplInfoSideEffectService {

    private final Logger log = LoggerFactory.getLogger(ApplInfoSideEffectServiceImpl.class);

    private final ApplInfoSideEffectRepository applInfoSideEffectRepository;

    private final ApplInfoSideEffectMapper applInfoSideEffectMapper;

    public ApplInfoSideEffectServiceImpl(ApplInfoSideEffectRepository applInfoSideEffectRepository, ApplInfoSideEffectMapper applInfoSideEffectMapper) {
        this.applInfoSideEffectRepository = applInfoSideEffectRepository;
        this.applInfoSideEffectMapper = applInfoSideEffectMapper;
    }

    /**
     * Save a applInfoSideEffect.
     *
     * @param applInfoSideEffectDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApplInfoSideEffectDTO save(ApplInfoSideEffectDTO applInfoSideEffectDTO) {
        log.debug("Request to save ApplInfoSideEffect : {}", applInfoSideEffectDTO);
        ApplInfoSideEffect applInfoSideEffect = applInfoSideEffectMapper.toEntity(applInfoSideEffectDTO);
        applInfoSideEffect = applInfoSideEffectRepository.save(applInfoSideEffect);
        return applInfoSideEffectMapper.toDto(applInfoSideEffect);
    }

    /**
     * Get all the applInfoSideEffects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplInfoSideEffectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplInfoSideEffects");
        return applInfoSideEffectRepository.findAll(pageable)
            .map(applInfoSideEffectMapper::toDto);
    }


    /**
     * Get one applInfoSideEffect by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplInfoSideEffectDTO> findOne(Long id) {
        log.debug("Request to get ApplInfoSideEffect : {}", id);
        return applInfoSideEffectRepository.findById(id)
            .map(applInfoSideEffectMapper::toDto);
    }

    /**
     * Delete the applInfoSideEffect by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplInfoSideEffect : {}", id);
        applInfoSideEffectRepository.deleteById(id);
    }
}
