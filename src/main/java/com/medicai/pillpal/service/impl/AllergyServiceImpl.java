package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.domain.Allergy;
import com.medicai.pillpal.repository.AllergyRepository;
import com.medicai.pillpal.service.AllergyService;
import com.medicai.pillpal.service.dto.AllergyDTO;
import com.medicai.pillpal.service.mapper.AllergyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Allergy}.
 */
@Service
@Transactional
public class AllergyServiceImpl implements AllergyService {

    private final Logger log = LoggerFactory.getLogger(AllergyServiceImpl.class);

    private final AllergyRepository allergyRepository;

    private final AllergyMapper allergyMapper;

    public AllergyServiceImpl(AllergyRepository allergyRepository, AllergyMapper allergyMapper) {
        this.allergyRepository = allergyRepository;
        this.allergyMapper = allergyMapper;
    }

    /**
     * Save a allergy.
     *
     * @param allergyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Allergy : {}", id);
        allergyRepository.deleteById(id);
    }

    /**
     * get a generic names
     *
     * @param genericName
     * @return a persisted entity
     */
    @Override
    public Optional<AllergyDTO> findAllergyByGenericName(String genericName) {
        log.debug("Request to get ApplicationInfo : {}", genericName);
        return allergyRepository.findByGenericName(genericName)
            .map(allergyMapper::toDto);
    }

    /**
     * get a list of generic names
     *
     * @param pageable
     * @param genericNameList
     * @return list of persisted entities
     */
    @Override
    public Page<AllergyDTO> findAllergyByGenericNameList(Pageable pageable, List<String> genericNameList) {
        log.debug("Request to get ApplicationInfo : {}", pageable);
        return allergyRepository.findByGenericNameList(pageable, genericNameList)
            .map(allergyMapper::toDto);
    }
}
