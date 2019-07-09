package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.domain.SideEffect;
import com.medicai.pillpal.repository.AllergyRepository;
import com.medicai.pillpal.repository.GeriatricRepository;
import com.medicai.pillpal.repository.SideEffectRepository;
import com.medicai.pillpal.service.SideEffectService;
import com.medicai.pillpal.service.dto.AllergyDTO;
import com.medicai.pillpal.service.dto.GeriatricDTO;
import com.medicai.pillpal.service.dto.SideEffectDTO;
import com.medicai.pillpal.service.mapper.AllergyMapper;
import com.medicai.pillpal.service.mapper.GeriatricMapper;
import com.medicai.pillpal.service.mapper.SideEffectMapper;
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

    private final Logger log = LoggerFactory.getLogger( SideEffectServiceImpl.class );
    private final SideEffectRepository sideEffectRepository;
    private final SideEffectMapper sideEffectMapper;
    private final AllergyRepository allergyRepository;
    private final AllergyMapper allergyMapper;
    private final GeriatricRepository geriatricRepository;
    private final GeriatricMapper geriatricMapper;

    public SideEffectServiceImpl(SideEffectRepository sideEffectRepository, SideEffectMapper sideEffectMapper,
                                 AllergyRepository allergyRepository, AllergyMapper allergyMapper,
                                 GeriatricRepository geriatricRepository, GeriatricMapper geriatricMapper) {
        this.sideEffectRepository = sideEffectRepository;
        this.sideEffectMapper = sideEffectMapper;
        this.allergyRepository = allergyRepository;
        this.allergyMapper = allergyMapper;
        this.geriatricRepository = geriatricRepository;
        this.geriatricMapper = geriatricMapper;
    }

    /**
     * Save a sideEffect.
     *
     * @param sideEffectDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SideEffectDTO save(SideEffectDTO sideEffectDTO) {
        log.debug( "Request to save SideEffect : {}", sideEffectDTO );
        SideEffect sideEffect = sideEffectMapper.toEntity( sideEffectDTO );
        sideEffect = sideEffectRepository.save( sideEffect );
        return sideEffectMapper.toDto( sideEffect );
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
        log.debug( "Request to get all SideEffects" );
        return sideEffectRepository.findAll( pageable )
            .map( sideEffectMapper::toDto );
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
        log.debug( "Request to get SideEffect : {}", id );
        return sideEffectRepository.findById( id )
            .map( sideEffectMapper::toDto );
    }

    /**
     * Delete the sideEffect by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug( "Request to delete SideEffect : {}", id );
        sideEffectRepository.deleteById( id );
    }

    /**
     * get a generic names
     *
     * @param pageable
     * @param genericName
     * @return a persisted entity
     */
    @Override
    public Page<AllergyDTO> findAllergyByGenericName(Pageable pageable, String genericName) {
        log.debug( "Request to get ApplicationInfo : {}", pageable );
        return allergyRepository.findAllergyByGenericName( pageable, genericName )
            .map( allergyMapper::toDto );
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
        log.debug( "Request to get ApplicationInfo : {}", pageable );
        return allergyRepository.findAllergyByGenericNameList( pageable, genericNameList )
            .map( allergyMapper::toDto );
    }


    /**
     * get a list of generic names
     *
     * @param pageable
     * @param genericNameList
     * @return list of persisted entities
     */
    @Override
    public Page<GeriatricDTO> findGeriatricByGenericNameList(Pageable pageable, List<String> genericNameList) {
        log.debug( "Request to get ApplicationInfo : {}", pageable );
        return geriatricRepository.findGeriatricByGenericNameList( pageable, genericNameList )
            .map( geriatricMapper::toDto );
    }


}
