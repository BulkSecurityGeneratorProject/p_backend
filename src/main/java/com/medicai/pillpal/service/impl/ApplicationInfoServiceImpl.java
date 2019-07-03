package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.service.ApplicationInfoService;
import com.medicai.pillpal.domain.ApplicationInfo;
import com.medicai.pillpal.repository.ApplicationInfoRepository;
import com.medicai.pillpal.service.dto.ApplicationInfoDTO;
import com.medicai.pillpal.service.mapper.ApplicationInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplicationInfo}.
 */
@Service
@Transactional
public class ApplicationInfoServiceImpl implements ApplicationInfoService {

    private final Logger log = LoggerFactory.getLogger(ApplicationInfoServiceImpl.class);

    private final ApplicationInfoRepository applicationInfoRepository;

    private final ApplicationInfoMapper applicationInfoMapper;

    public ApplicationInfoServiceImpl(ApplicationInfoRepository applicationInfoRepository, ApplicationInfoMapper applicationInfoMapper) {
        this.applicationInfoRepository = applicationInfoRepository;
        this.applicationInfoMapper = applicationInfoMapper;
    }

    /**
     * Save a applicationInfo.
     *
     * @param applicationInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApplicationInfoDTO save(ApplicationInfoDTO applicationInfoDTO) {
        log.debug("Request to save ApplicationInfo : {}", applicationInfoDTO);
        ApplicationInfo applicationInfo = applicationInfoMapper.toEntity(applicationInfoDTO);
        applicationInfo = applicationInfoRepository.save(applicationInfo);
        return applicationInfoMapper.toDto(applicationInfo);
    }

    /**
     * Get all the applicationInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationInfos");
        return applicationInfoRepository.findAll(pageable)
            .map(applicationInfoMapper::toDto);
    }


    /**
     * Get one applicationInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationInfoDTO> findOne(Long id) {
        log.debug("Request to get ApplicationInfo : {}", id);
        return applicationInfoRepository.findById(id)
            .map(applicationInfoMapper::toDto);
    }

    /**
     * Delete the applicationInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationInfo : {}", id);
        applicationInfoRepository.deleteById(id);
    }

    @Override
    public List<ApplicationInfoDTO> saveAll(List<ApplicationInfoDTO> applicationInfoDTO) {
        log.debug("Request to save ApplicationInfo : {}", applicationInfoDTO);
        List<ApplicationInfo> applicationInfo = applicationInfoMapper.toEntity(applicationInfoDTO);
        applicationInfo = applicationInfoRepository.saveAll(applicationInfo);
        return applicationInfoMapper.toDto(applicationInfo);
    }
}
