package com.medicai.pillpal.service;

import com.medicai.pillpal.domain.Pediatric;
import com.medicai.pillpal.repository.PediatricRepository;
import com.medicai.pillpal.service.dto.PediatricDTO;
import com.medicai.pillpal.service.mapper.PediatricMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pediatric}.
 */
@Service
@Transactional
public class PediatricService {

    private final Logger log = LoggerFactory.getLogger(PediatricService.class);

    private final PediatricRepository pediatricRepository;

    private final PediatricMapper pediatricMapper;

    public PediatricService(PediatricRepository pediatricRepository, PediatricMapper pediatricMapper) {
        this.pediatricRepository = pediatricRepository;
        this.pediatricMapper = pediatricMapper;
    }

    /**
     * Save a pediatric.
     *
     * @param pediatricDTO the entity to save.
     * @return the persisted entity.
     */
    public PediatricDTO save(PediatricDTO pediatricDTO) {
        log.debug("Request to save Pediatric : {}", pediatricDTO);
        Pediatric pediatric = pediatricMapper.toEntity(pediatricDTO);
        pediatric = pediatricRepository.save(pediatric);
        return pediatricMapper.toDto(pediatric);
    }

    /**
     * Get all the pediatrics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PediatricDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pediatrics");
        return pediatricRepository.findAll(pageable)
            .map(pediatricMapper::toDto);
    }


    /**
     * Get one pediatric by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PediatricDTO> findOne(Long id) {
        log.debug("Request to get Pediatric : {}", id);
        return pediatricRepository.findById(id)
            .map(pediatricMapper::toDto);
    }

    /**
     * Delete the pediatric by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pediatric : {}", id);
        pediatricRepository.deleteById(id);
    }
}
