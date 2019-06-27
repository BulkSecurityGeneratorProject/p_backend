package com.medicai.pillpal.service;

import com.medicai.pillpal.domain.Geriatric;
import com.medicai.pillpal.repository.GeriatricRepository;
import com.medicai.pillpal.service.dto.GeriatricDTO;
import com.medicai.pillpal.service.mapper.GeriatricMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Geriatric}.
 */
@Service
@Transactional
public class GeriatricService {

    private final Logger log = LoggerFactory.getLogger(GeriatricService.class);

    private final GeriatricRepository geriatricRepository;

    private final GeriatricMapper geriatricMapper;

    public GeriatricService(GeriatricRepository geriatricRepository, GeriatricMapper geriatricMapper) {
        this.geriatricRepository = geriatricRepository;
        this.geriatricMapper = geriatricMapper;
    }

    /**
     * Save a geriatric.
     *
     * @param geriatricDTO the entity to save.
     * @return the persisted entity.
     */
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
    public void delete(Long id) {
        log.debug("Request to delete Geriatric : {}", id);
        geriatricRepository.deleteById(id);
    }
}
