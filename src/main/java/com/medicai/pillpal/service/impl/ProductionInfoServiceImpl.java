package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.service.ProductionInfoService;
import com.medicai.pillpal.domain.ProductionInfo;
import com.medicai.pillpal.repository.ProductionInfoRepository;
import com.medicai.pillpal.service.dto.ProductionInfoDTO;
import com.medicai.pillpal.service.mapper.ProductionInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductionInfo}.
 */
@Service
@Transactional
public class ProductionInfoServiceImpl implements ProductionInfoService {

    private final Logger log = LoggerFactory.getLogger(ProductionInfoServiceImpl.class);

    private final ProductionInfoRepository productionInfoRepository;

    private final ProductionInfoMapper productionInfoMapper;

    public ProductionInfoServiceImpl(ProductionInfoRepository productionInfoRepository, ProductionInfoMapper productionInfoMapper) {
        this.productionInfoRepository = productionInfoRepository;
        this.productionInfoMapper = productionInfoMapper;
    }

    /**
     * Save a productionInfo.
     *
     * @param productionInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductionInfoDTO save(ProductionInfoDTO productionInfoDTO) {
        log.debug("Request to save ProductionInfo : {}", productionInfoDTO);
        ProductionInfo productionInfo = productionInfoMapper.toEntity(productionInfoDTO);
        productionInfo = productionInfoRepository.save(productionInfo);
        return productionInfoMapper.toDto(productionInfo);
    }

    /**
     * Get all the productionInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductionInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductionInfos");
        return productionInfoRepository.findAll(pageable)
            .map(productionInfoMapper::toDto);
    }


    /**
     * Get one productionInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductionInfoDTO> findOne(Long id) {
        log.debug("Request to get ProductionInfo : {}", id);
        return productionInfoRepository.findById(id)
            .map(productionInfoMapper::toDto);
    }

    /**
     * Delete the productionInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductionInfo : {}", id);
        productionInfoRepository.deleteById(id);
    }
}
