package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.service.ConsumptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Consumptions.
 */
@Service
@Transactional
public class ConsumptionServiceImpl implements ConsumptionService {

    private final Logger log = LoggerFactory.getLogger( ConsumptionServiceImpl.class);

}
