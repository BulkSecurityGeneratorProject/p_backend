package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.domain.AppInfoSideEffect;
import com.medicai.pillpal.repository.AppInfoSideEffectRepository;
import com.medicai.pillpal.service.AppInfoSideEffectService;
import com.medicai.pillpal.service.dto.AppInfoSideEffectDTO;
import com.medicai.pillpal.service.mapper.AppInfoSideEffectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AppInfoSideEffect}.
 */
@Service
@Transactional
public class AppInfoSideEffectServiceImpl implements AppInfoSideEffectService {

    private final Logger log = LoggerFactory.getLogger(AppInfoSideEffectServiceImpl.class);

    private final AppInfoSideEffectRepository appInfoSideEffectRepository;

    private final AppInfoSideEffectMapper appInfoSideEffectMapper;

    public AppInfoSideEffectServiceImpl(AppInfoSideEffectRepository appInfoSideEffectRepository, AppInfoSideEffectMapper appInfoSideEffectMapper) {
        this.appInfoSideEffectRepository = appInfoSideEffectRepository;
        this.appInfoSideEffectMapper = appInfoSideEffectMapper;
    }

    /**
     * Save a appInfoSideEffect.
     *
     * @param appInfoSideEffectDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AppInfoSideEffectDTO save(AppInfoSideEffectDTO appInfoSideEffectDTO) {
        log.debug("Request to save AppInfoSideEffect : {}", appInfoSideEffectDTO);
        AppInfoSideEffect appInfoSideEffect = appInfoSideEffectMapper.toEntity(appInfoSideEffectDTO);
        appInfoSideEffect = appInfoSideEffectRepository.save(appInfoSideEffect);
        return appInfoSideEffectMapper.toDto(appInfoSideEffect);
    }

    /**
     * Get all the appInfoSideEffects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AppInfoSideEffectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppInfoSideEffects");
        return appInfoSideEffectRepository.findAll(pageable)
            .map(appInfoSideEffectMapper::toDto);
    }


    /**
     * Get one appInfoSideEffect by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AppInfoSideEffectDTO> findOne(Long id) {
        log.debug("Request to get AppInfoSideEffect : {}", id);
        return appInfoSideEffectRepository.findById(id)
            .map(appInfoSideEffectMapper::toDto);
    }

    /**
     * Delete the appInfoSideEffect by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppInfoSideEffect : {}", id);
        appInfoSideEffectRepository.deleteById(id);
    }

    /**
     * get a generic names
     *
     * @param genericName
     * @return a persisted entity
     */
    @Override
    public Optional<AppInfoSideEffectDTO> findAppInfoSideEffectByGenericName(String genericName) {
        log.debug("Request to get ApplicationInfo : {}", genericName);
        return appInfoSideEffectRepository.findByGenericName(genericName)
            .map(appInfoSideEffectMapper::toDto);
    }

    /**
     * get a list of generic names
     *
     * @param pageable
     * @param genericNameList
     * @return list of persisted entities
     */
    @Override
    public Page<AppInfoSideEffectDTO> findAppInfoSideEffectByGenericNameList(Pageable pageable, List<String> genericNameList) {
        log.debug("Request to get ApplicationInfo : {}", pageable);
        return appInfoSideEffectRepository.findByGenericNameList(pageable, genericNameList)
            .map(appInfoSideEffectMapper::toDto);
    }
}
