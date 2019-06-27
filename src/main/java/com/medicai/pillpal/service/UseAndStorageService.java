package com.medicai.pillpal.service;

import com.medicai.pillpal.domain.UseAndStorage;
import com.medicai.pillpal.repository.UseAndStorageRepository;
import com.medicai.pillpal.service.dto.UseAndStorageDTO;
import com.medicai.pillpal.service.mapper.UseAndStorageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UseAndStorage}.
 */
@Service
@Transactional
public class UseAndStorageService {

    private final Logger log = LoggerFactory.getLogger(UseAndStorageService.class);

    private final UseAndStorageRepository useAndStorageRepository;

    private final UseAndStorageMapper useAndStorageMapper;

    public UseAndStorageService(UseAndStorageRepository useAndStorageRepository, UseAndStorageMapper useAndStorageMapper) {
        this.useAndStorageRepository = useAndStorageRepository;
        this.useAndStorageMapper = useAndStorageMapper;
    }

    /**
     * Save a useAndStorage.
     *
     * @param useAndStorageDTO the entity to save.
     * @return the persisted entity.
     */
    public UseAndStorageDTO save(UseAndStorageDTO useAndStorageDTO) {
        log.debug("Request to save UseAndStorage : {}", useAndStorageDTO);
        UseAndStorage useAndStorage = useAndStorageMapper.toEntity(useAndStorageDTO);
        useAndStorage = useAndStorageRepository.save(useAndStorage);
        return useAndStorageMapper.toDto(useAndStorage);
    }

    /**
     * Get all the useAndStorages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UseAndStorageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UseAndStorages");
        return useAndStorageRepository.findAll(pageable)
            .map(useAndStorageMapper::toDto);
    }


    /**
     * Get one useAndStorage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UseAndStorageDTO> findOne(Long id) {
        log.debug("Request to get UseAndStorage : {}", id);
        return useAndStorageRepository.findById(id)
            .map(useAndStorageMapper::toDto);
    }

    /**
     * Delete the useAndStorage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UseAndStorage : {}", id);
        useAndStorageRepository.deleteById(id);
    }
}
