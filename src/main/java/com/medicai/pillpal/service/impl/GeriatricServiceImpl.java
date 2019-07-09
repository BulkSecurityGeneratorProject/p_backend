package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.domain.Geriatric;
import com.medicai.pillpal.repository.GeriatricRepository;
import com.medicai.pillpal.service.GeriatricService;
import com.medicai.pillpal.service.dto.GeriatricDTO;
import com.medicai.pillpal.service.mapper.GeriatricMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Geriatric}.
 */
@Service
@Transactional
public class GeriatricServiceImpl implements GeriatricService {

    private final Logger log = LoggerFactory.getLogger(GeriatricServiceImpl.class);

    private final GeriatricRepository geriatricRepository;

    private final GeriatricMapper geriatricMapper;

    public GeriatricServiceImpl(GeriatricRepository geriatricRepository, GeriatricMapper geriatricMapper) {
        this.geriatricRepository = geriatricRepository;
        this.geriatricMapper = geriatricMapper;
    }

    /**
     * Save a geriatric.
     *
     * @param geriatricDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GeriatricDTO save(GeriatricDTO geriatricDTO) {
        log.debug("Request to save Geriatric : {}", geriatricDTO);
        Geriatric geriatric = geriatricMapper.toEntity(geriatricDTO);
        geriatric = geriatricRepository.save(geriatric);
        return geriatricMapper.toDto(geriatric);
    }

    /**
     * Get all the geriatrics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GeriatricDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Geriatrics");
        return geriatricRepository.findAll(pageable)
            .map(geriatricMapper::toDto);
    }


    /**
     * Get one geriatric by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GeriatricDTO> findOne(Long id) {
        log.debug("Request to get Geriatric : {}", id);
        return geriatricRepository.findById(id)
            .map(geriatricMapper::toDto);
    }

    /**
     * Delete the geriatric by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Geriatric : {}", id);
        geriatricRepository.deleteById(id);
    }

    /**
     * get the list os generic names
     *
     * @param pageable
     * @param genericName
     * @return list of persisted entity
     */
    @Override
    public Page<GeriatricDTO> findGeriatricByGenericNameList(Pageable pageable, List<String> genericName) {
        log.debug("Request to get ApplicationInfo : {}", pageable);
        return geriatricRepository.findByGenericNameList(pageable, genericName)
            .map(geriatricMapper::toDto);
    }

    /**
     * get a generic name
     *
     * @param genericName
     * @return a persisted entity
     */
    @Override
    public Optional<GeriatricDTO> findGeriatricByGenericName(String genericName) {
        log.debug("Request to get ApplicationInfo : {}", genericName);
        return geriatricRepository.findGeriatricByGenericName(genericName)
            .map(geriatricMapper::toDto);
    }
}
