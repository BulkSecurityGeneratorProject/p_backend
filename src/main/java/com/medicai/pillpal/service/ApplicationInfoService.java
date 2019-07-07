package com.medicai.pillpal.service;

import com.medicai.pillpal.domain.ApplicationInfo;
import com.medicai.pillpal.service.dto.ApplicationInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.medicai.pillpal.domain.ApplicationInfo}.
 */
public interface ApplicationInfoService {

    /**
     * Save a applicationInfo.
     *
     * @param applicationInfoDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationInfoDTO save(ApplicationInfoDTO applicationInfoDTO);

    /**
     * Get all the applicationInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicationInfoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" applicationInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationInfoDTO> findOne(Long id);

    /**
     * Delete the "id" applicationInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Save a list of applicationInfos
     *
     * @param applicationInfoDTO list of entities.
     * @return list of persisted entities
     */
    List<ApplicationInfoDTO> saveAll(List<ApplicationInfoDTO> applicationInfoDTO);

    /**
     * get a list of applications genericNames
     *
     * @return list of persisted entites
     */
    Page<ApplicationInfoDTO> findByGenericNameList(Pageable pageable,List<String> genericNameList);


}
