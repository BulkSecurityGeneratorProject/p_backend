package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.domain.PharmaceuticalCode;
import com.medicai.pillpal.repository.PharmaceuticalCodeRepository;
import com.medicai.pillpal.service.PharmaceuticalCodeService;
import com.medicai.pillpal.service.dto.PharmaceuticalCodeDTO;
import com.medicai.pillpal.service.mapper.PharmaceuticalCodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PharmaceuticalCode}.
 */
@Service
@Transactional
public class PharmaceuticalCodeServiceImpl implements PharmaceuticalCodeService {

    private final Logger log = LoggerFactory.getLogger(PharmaceuticalCodeServiceImpl.class);

    private final PharmaceuticalCodeRepository pharmaceuticalCodeRepository;

    private final PharmaceuticalCodeMapper pharmaceuticalCodeMapper;

    public PharmaceuticalCodeServiceImpl(PharmaceuticalCodeRepository pharmaceuticalCodeRepository, PharmaceuticalCodeMapper pharmaceuticalCodeMapper) {
        this.pharmaceuticalCodeRepository = pharmaceuticalCodeRepository;
        this.pharmaceuticalCodeMapper = pharmaceuticalCodeMapper;
    }

    /**
     * Save a pharmaceuticalCode.
     *
     * @param pharmaceuticalCodeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PharmaceuticalCodeDTO save(PharmaceuticalCodeDTO pharmaceuticalCodeDTO) {
        log.debug("Request to save PharmaceuticalCode : {}", pharmaceuticalCodeDTO);
        PharmaceuticalCode pharmaceuticalCode = pharmaceuticalCodeMapper.toEntity(pharmaceuticalCodeDTO);
        pharmaceuticalCode = pharmaceuticalCodeRepository.save(pharmaceuticalCode);
        return pharmaceuticalCodeMapper.toDto(pharmaceuticalCode);
    }

    /**
     * Get all the pharmaceuticalCodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PharmaceuticalCodeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PharmaceuticalCodes");
        return pharmaceuticalCodeRepository.findAll(pageable)
            .map(pharmaceuticalCodeMapper::toDto);
    }


    /**
     * Get one pharmaceuticalCode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PharmaceuticalCodeDTO> findOne(Long id) {
        log.debug("Request to get PharmaceuticalCode : {}", id);
        return pharmaceuticalCodeRepository.findById(id)
            .map(pharmaceuticalCodeMapper::toDto);
    }

    /**
     * Delete the pharmaceuticalCode by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PharmaceuticalCode : {}", id);
        pharmaceuticalCodeRepository.deleteById(id);
    }
}
