package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.ApplInfoSideEffect;
import com.medicai.pillpal.repository.ApplInfoSideEffectRepository;
import com.medicai.pillpal.service.ConsumptionService;
import com.medicai.pillpal.service.dto.ApplInfoSideEffectDTO;
import com.medicai.pillpal.service.mapper.ApplInfoSideEffectMapper;
import com.medicai.pillpal.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.medicai.pillpal.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.medicai.pillpal.domain.enumeration.SideEffectType;
/**
 * Integration tests for the Consumption REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class ConsumptionResourceIT {

    private static final SideEffectType DEFAULT_SIDE_EFFECT_TYPE = SideEffectType.COMMON;
    private static final SideEffectType UPDATED_SIDE_EFFECT_TYPE = SideEffectType.RARE;



}
