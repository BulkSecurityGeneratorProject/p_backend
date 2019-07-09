package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.domain.SideEffect;
import com.medicai.pillpal.repository.*;
import com.medicai.pillpal.service.SideEffectService;
import com.medicai.pillpal.service.dto.*;
import com.medicai.pillpal.service.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SideEffect}.
 */
@Service
@Transactional
public class SideEffectServiceImpl implements SideEffectService {

    private final Logger log = LoggerFactory.getLogger(SideEffectServiceImpl.class);
    private final SideEffectRepository sideEffectRepository;
    private final AllergyRepository allergyRepository;
    private final GeriatricRepository geriatricRepository;
    private final PediatricRepository pediatricRepository;
    private final AppInfoSideEffectRepository appInfoSideEffectRepository;
    private final BreastFeedingRepository breastFeedingRepository;

    private final AllergyMapper allergyMapper;
    private final SideEffectMapper sideEffectMapper;
    private final GeriatricMapper geriatricMapper;
    private final PediatricMapper pediatricMapper;
    private final ApplInfoSideEffectMapper appInfoSideEffectMapper;
    private final BreastFeedingMapper breastFeedingMapper ;


    public SideEffectServiceImpl(SideEffectRepository sideEffectRepository,
                                 AllergyRepository allergyRepository,
                                 GeriatricRepository geriatricRepository,
                                 PediatricRepository pediatricRepository,
                                 AppInfoSideEffectRepository appInfoSideEffectRepository,

                                 BreastFeedingRepository breastFeedingRepository, SideEffectMapper sideEffectMapper,
                                 AllergyMapper allergyMapper,
                                 GeriatricMapper geriatricMapper,
                                 PediatricMapper pediatricMapper,
                                 ApplInfoSideEffectMapper appInfoSideEffectMapper,
                                 BreastFeedingMapper breastFeedingMapper) {
        this.sideEffectRepository = sideEffectRepository;
        this.allergyRepository = allergyRepository;
        this.geriatricRepository = geriatricRepository;
        this.pediatricRepository = pediatricRepository;
        this.appInfoSideEffectRepository = appInfoSideEffectRepository;
        this.breastFeedingRepository = breastFeedingRepository;

        this.sideEffectMapper = sideEffectMapper;
        this.allergyMapper = allergyMapper;
        this.geriatricMapper = geriatricMapper;
        this.pediatricMapper = pediatricMapper;
        this.appInfoSideEffectMapper = appInfoSideEffectMapper;
        this.breastFeedingMapper = breastFeedingMapper;
    }

    /**
     * Save a sideEffect.
     *
     * @param sideEffectDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SideEffectDTO save(SideEffectDTO sideEffectDTO) {
        log.debug("Request to save SideEffect : {}", sideEffectDTO);
        SideEffect sideEffect = sideEffectMapper.toEntity(sideEffectDTO);
        sideEffect = sideEffectRepository.save(sideEffect);
        return sideEffectMapper.toDto(sideEffect);
    }

    /**
     * Get all the sideEffects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SideEffectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SideEffects");
        return sideEffectRepository.findAll(pageable)
            .map(sideEffectMapper::toDto);
    }


    /**
     * Get one sideEffect by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SideEffectDTO> findOne(Long id) {
        log.debug("Request to get SideEffect : {}", id);
        return sideEffectRepository.findById(id)
            .map(sideEffectMapper::toDto);
    }

    /**
     * Delete the sideEffect by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SideEffect : {}", id);
        sideEffectRepository.deleteById(id);
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

    /**
     * get a generic names
     *
     * @param genericName
     * @return a persisted entity
     */
    @Override
    public Optional<BreastFeedingDTO> findBreastFeedingByGenericName(String genericName) {
        log.debug("Request to get ApplicationInfo : {}", genericName);
        return breastFeedingRepository.findBreastFeedingByGenericName(genericName)
            .map(breastFeedingMapper::toDto);
    }

    /**
     * get a list of generic names
     *
     * @param pageable
     * @param genericNameList
     * @return list of persisted entities
     */
    @Override
    public Page<BreastFeedingDTO> findBreastFeedingByGenericNameList(Pageable pageable, List<String> genericNameList) {
        log.debug("Request to get ApplicationInfo : {}", pageable);
        return breastFeedingRepository.findBreastFeedingByGenericNameList(pageable, genericNameList)
            .map(breastFeedingMapper::toDto);
    }
}
