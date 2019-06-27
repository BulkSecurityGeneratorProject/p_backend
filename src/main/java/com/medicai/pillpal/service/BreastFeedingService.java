package com.medicai.pillpal.service;

import com.medicai.pillpal.domain.BreastFeeding;
import com.medicai.pillpal.repository.BreastFeedingRepository;
import com.medicai.pillpal.service.dto.BreastFeedingDTO;
import com.medicai.pillpal.service.mapper.BreastFeedingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BreastFeeding}.
 */
@Service
@Transactional
public class BreastFeedingService {

    private final Logger log = LoggerFactory.getLogger(BreastFeedingService.class);

    private final BreastFeedingRepository breastFeedingRepository;

    private final BreastFeedingMapper breastFeedingMapper;

    public BreastFeedingService(BreastFeedingRepository breastFeedingRepository, BreastFeedingMapper breastFeedingMapper) {
        this.breastFeedingRepository = breastFeedingRepository;
        this.breastFeedingMapper = breastFeedingMapper;
    }

    /**
     * Save a breastFeeding.
     *
     * @param breastFeedingDTO the entity to save.
     * @return the persisted entity.
     */
    public BreastFeedingDTO save(BreastFeedingDTO breastFeedingDTO) {
        log.debug("Request to save BreastFeeding : {}", breastFeedingDTO);
        BreastFeeding breastFeeding = breastFeedingMapper.toEntity(breastFeedingDTO);
        breastFeeding = breastFeedingRepository.save(breastFeeding);
        return breastFeedingMapper.toDto(breastFeeding);
    }

    /**
     * Get all the breastFeedings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BreastFeedingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BreastFeedings");
        return breastFeedingRepository.findAll(pageable)
            .map(breastFeedingMapper::toDto);
    }


    /**
     * Get one breastFeeding by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BreastFeedingDTO> findOne(Long id) {
        log.debug("Request to get BreastFeeding : {}", id);
        return breastFeedingRepository.findById(id)
            .map(breastFeedingMapper::toDto);
    }

    /**
     * Delete the breastFeeding by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BreastFeeding : {}", id);
        breastFeedingRepository.deleteById(id);
    }
}
