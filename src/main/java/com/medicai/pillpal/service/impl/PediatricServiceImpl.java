package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.domain.Pediatric;
import com.medicai.pillpal.repository.PediatricRepository;
import com.medicai.pillpal.service.PediatricService;
import com.medicai.pillpal.service.dto.PediatricDTO;
import com.medicai.pillpal.service.mapper.PediatricMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Pediatric}.
 */
@Service
@Transactional
public class PediatricServiceImpl implements PediatricService {

    private final Logger log = LoggerFactory.getLogger(PediatricServiceImpl.class);

    private final PediatricRepository pediatricRepository;

    private final PediatricMapper pediatricMapper;

    public PediatricServiceImpl(PediatricRepository pediatricRepository, PediatricMapper pediatricMapper) {
        this.pediatricRepository = pediatricRepository;
        this.pediatricMapper = pediatricMapper;
    }

    /**
     * Save a pediatric.
     *
     * @param pediatricDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pediatric : {}", id);
        pediatricRepository.deleteById(id);
    }

    /**
     * get a generic names
     *
     * @param genericName
     * @return a persisted entity
     */
    @Override
    public Optional<PediatricDTO> findPediatricByGenericName(String genericName) {
        log.debug("Request to get ApplicationInfo : {}", genericName);
        return pediatricRepository.findByGenericName(genericName)
            .map(pediatricMapper::toDto);
    }

    /**
     * get a list of generic names
     *
     * @param pageable
     * @param genericNameList
     * @return list of persisted entities
     */
    @Override
    public Page<PediatricDTO> findPediatricByGenericNameList(Pageable pageable, List<String> genericNameList) {
        log.debug("Request to get ApplicationInfo : {}", pageable);
        return pediatricRepository.findByGenericNameList(pageable, genericNameList)
            .map(pediatricMapper::toDto);
    }
}
