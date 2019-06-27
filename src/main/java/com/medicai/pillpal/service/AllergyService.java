package com.medicai.pillpal.service;

import com.medicai.pillpal.domain.Allergy;
import com.medicai.pillpal.repository.AllergyRepository;
import com.medicai.pillpal.service.dto.AllergyDTO;
import com.medicai.pillpal.service.mapper.AllergyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Allergy}.
 */
@Service
@Transactional
public class AllergyService {

    private final Logger log = LoggerFactory.getLogger(AllergyService.class);

    private final AllergyRepository allergyRepository;

    private final AllergyMapper allergyMapper;

    public AllergyService(AllergyRepository allergyRepository, AllergyMapper allergyMapper) {
        this.allergyRepository = allergyRepository;
        this.allergyMapper = allergyMapper;
    }

    /**
     * Save a allergy.
     *
     * @param allergyDTO the entity to save.
     * @return the persisted entity.
     */
    public AllergyDTO save(AllergyDTO allergyDTO) {
        log.debug("Request to save Allergy : {}", allergyDTO);
        Allergy allergy = allergyMapper.toEntity(allergyDTO);
        allergy = allergyRepository.save(allergy);
        return allergyMapper.toDto(allergy);
    }

    /**
     * Get all the allergies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AllergyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Allergies");
        return allergyRepository.findAll(pageable)
            .map(allergyMapper::toDto);
    }


    /**
     * Get one allergy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AllergyDTO> findOne(Long id) {
        log.debug("Request to get Allergy : {}", id);
        return allergyRepository.findById(id)
            .map(allergyMapper::toDto);
    }

    /**
     * Delete the allergy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Allergy : {}", id);
        allergyRepository.deleteById(id);
    }
}
