package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.service.ConsumptionService;
import com.medicai.pillpal.domain.ApplInfoSideEffect;
import com.medicai.pillpal.repository.ApplInfoSideEffectRepository;
import com.medicai.pillpal.service.dto.ApplInfoSideEffectDTO;
import com.medicai.pillpal.service.mapper.ApplInfoSideEffectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Consumptions.
 */
@Service
@Transactional
public class ConsumptionServiceImpl implements ConsumptionService {

    private final Logger log = LoggerFactory.getLogger( ConsumptionServiceImpl.class);

}
