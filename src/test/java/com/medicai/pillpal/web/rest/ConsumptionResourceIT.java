package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.enumeration.SideEffectType;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration tests for the Consumption REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class ConsumptionResourceIT {

    private static final SideEffectType DEFAULT_SIDE_EFFECT_TYPE = SideEffectType.COMMON;
    private static final SideEffectType UPDATED_SIDE_EFFECT_TYPE = SideEffectType.RARE;


}
