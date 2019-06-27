package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.ApplInfoSideEffect;
import com.medicai.pillpal.repository.ApplInfoSideEffectRepository;
import com.medicai.pillpal.service.ApplInfoSideEffectService;
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
 * Integration tests for the {@Link ApplInfoSideEffectResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class ApplInfoSideEffectResourceIT {

    private static final SideEffectType DEFAULT_SIDE_EFFECT_TYPE = SideEffectType.COMMON;
    private static final SideEffectType UPDATED_SIDE_EFFECT_TYPE = SideEffectType.RARE;

    @Autowired
    private ApplInfoSideEffectRepository applInfoSideEffectRepository;

    @Autowired
    private ApplInfoSideEffectMapper applInfoSideEffectMapper;

    @Autowired
    private ApplInfoSideEffectService applInfoSideEffectService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restApplInfoSideEffectMockMvc;

    private ApplInfoSideEffect applInfoSideEffect;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplInfoSideEffectResource applInfoSideEffectResource = new ApplInfoSideEffectResource(applInfoSideEffectService);
        this.restApplInfoSideEffectMockMvc = MockMvcBuilders.standaloneSetup(applInfoSideEffectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplInfoSideEffect createEntity(EntityManager em) {
        ApplInfoSideEffect applInfoSideEffect = new ApplInfoSideEffect()
            .sideEffectType(DEFAULT_SIDE_EFFECT_TYPE);
        return applInfoSideEffect;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplInfoSideEffect createUpdatedEntity(EntityManager em) {
        ApplInfoSideEffect applInfoSideEffect = new ApplInfoSideEffect()
            .sideEffectType(UPDATED_SIDE_EFFECT_TYPE);
        return applInfoSideEffect;
    }

    @BeforeEach
    public void initTest() {
        applInfoSideEffect = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplInfoSideEffect() throws Exception {
        int databaseSizeBeforeCreate = applInfoSideEffectRepository.findAll().size();

        // Create the ApplInfoSideEffect
        ApplInfoSideEffectDTO applInfoSideEffectDTO = applInfoSideEffectMapper.toDto(applInfoSideEffect);
        restApplInfoSideEffectMockMvc.perform(post("/api/appl-info-side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applInfoSideEffectDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplInfoSideEffect in the database
        List<ApplInfoSideEffect> applInfoSideEffectList = applInfoSideEffectRepository.findAll();
        assertThat(applInfoSideEffectList).hasSize(databaseSizeBeforeCreate + 1);
        ApplInfoSideEffect testApplInfoSideEffect = applInfoSideEffectList.get(applInfoSideEffectList.size() - 1);
        assertThat(testApplInfoSideEffect.getSideEffectType()).isEqualTo(DEFAULT_SIDE_EFFECT_TYPE);
    }

    @Test
    @Transactional
    public void createApplInfoSideEffectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applInfoSideEffectRepository.findAll().size();

        // Create the ApplInfoSideEffect with an existing ID
        applInfoSideEffect.setId(1L);
        ApplInfoSideEffectDTO applInfoSideEffectDTO = applInfoSideEffectMapper.toDto(applInfoSideEffect);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplInfoSideEffectMockMvc.perform(post("/api/appl-info-side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applInfoSideEffectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplInfoSideEffect in the database
        List<ApplInfoSideEffect> applInfoSideEffectList = applInfoSideEffectRepository.findAll();
        assertThat(applInfoSideEffectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApplInfoSideEffects() throws Exception {
        // Initialize the database
        applInfoSideEffectRepository.saveAndFlush(applInfoSideEffect);

        // Get all the applInfoSideEffectList
        restApplInfoSideEffectMockMvc.perform(get("/api/appl-info-side-effects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applInfoSideEffect.getId().intValue())))
            .andExpect(jsonPath("$.[*].sideEffectType").value(hasItem(DEFAULT_SIDE_EFFECT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getApplInfoSideEffect() throws Exception {
        // Initialize the database
        applInfoSideEffectRepository.saveAndFlush(applInfoSideEffect);

        // Get the applInfoSideEffect
        restApplInfoSideEffectMockMvc.perform(get("/api/appl-info-side-effects/{id}", applInfoSideEffect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applInfoSideEffect.getId().intValue()))
            .andExpect(jsonPath("$.sideEffectType").value(DEFAULT_SIDE_EFFECT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplInfoSideEffect() throws Exception {
        // Get the applInfoSideEffect
        restApplInfoSideEffectMockMvc.perform(get("/api/appl-info-side-effects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplInfoSideEffect() throws Exception {
        // Initialize the database
        applInfoSideEffectRepository.saveAndFlush(applInfoSideEffect);

        int databaseSizeBeforeUpdate = applInfoSideEffectRepository.findAll().size();

        // Update the applInfoSideEffect
        ApplInfoSideEffect updatedApplInfoSideEffect = applInfoSideEffectRepository.findById(applInfoSideEffect.getId()).get();
        // Disconnect from session so that the updates on updatedApplInfoSideEffect are not directly saved in db
        em.detach(updatedApplInfoSideEffect);
        updatedApplInfoSideEffect
            .sideEffectType(UPDATED_SIDE_EFFECT_TYPE);
        ApplInfoSideEffectDTO applInfoSideEffectDTO = applInfoSideEffectMapper.toDto(updatedApplInfoSideEffect);

        restApplInfoSideEffectMockMvc.perform(put("/api/appl-info-side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applInfoSideEffectDTO)))
            .andExpect(status().isOk());

        // Validate the ApplInfoSideEffect in the database
        List<ApplInfoSideEffect> applInfoSideEffectList = applInfoSideEffectRepository.findAll();
        assertThat(applInfoSideEffectList).hasSize(databaseSizeBeforeUpdate);
        ApplInfoSideEffect testApplInfoSideEffect = applInfoSideEffectList.get(applInfoSideEffectList.size() - 1);
        assertThat(testApplInfoSideEffect.getSideEffectType()).isEqualTo(UPDATED_SIDE_EFFECT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingApplInfoSideEffect() throws Exception {
        int databaseSizeBeforeUpdate = applInfoSideEffectRepository.findAll().size();

        // Create the ApplInfoSideEffect
        ApplInfoSideEffectDTO applInfoSideEffectDTO = applInfoSideEffectMapper.toDto(applInfoSideEffect);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplInfoSideEffectMockMvc.perform(put("/api/appl-info-side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applInfoSideEffectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplInfoSideEffect in the database
        List<ApplInfoSideEffect> applInfoSideEffectList = applInfoSideEffectRepository.findAll();
        assertThat(applInfoSideEffectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplInfoSideEffect() throws Exception {
        // Initialize the database
        applInfoSideEffectRepository.saveAndFlush(applInfoSideEffect);

        int databaseSizeBeforeDelete = applInfoSideEffectRepository.findAll().size();

        // Delete the applInfoSideEffect
        restApplInfoSideEffectMockMvc.perform(delete("/api/appl-info-side-effects/{id}", applInfoSideEffect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplInfoSideEffect> applInfoSideEffectList = applInfoSideEffectRepository.findAll();
        assertThat(applInfoSideEffectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplInfoSideEffect.class);
        ApplInfoSideEffect applInfoSideEffect1 = new ApplInfoSideEffect();
        applInfoSideEffect1.setId(1L);
        ApplInfoSideEffect applInfoSideEffect2 = new ApplInfoSideEffect();
        applInfoSideEffect2.setId(applInfoSideEffect1.getId());
        assertThat(applInfoSideEffect1).isEqualTo(applInfoSideEffect2);
        applInfoSideEffect2.setId(2L);
        assertThat(applInfoSideEffect1).isNotEqualTo(applInfoSideEffect2);
        applInfoSideEffect1.setId(null);
        assertThat(applInfoSideEffect1).isNotEqualTo(applInfoSideEffect2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplInfoSideEffectDTO.class);
        ApplInfoSideEffectDTO applInfoSideEffectDTO1 = new ApplInfoSideEffectDTO();
        applInfoSideEffectDTO1.setId(1L);
        ApplInfoSideEffectDTO applInfoSideEffectDTO2 = new ApplInfoSideEffectDTO();
        assertThat(applInfoSideEffectDTO1).isNotEqualTo(applInfoSideEffectDTO2);
        applInfoSideEffectDTO2.setId(applInfoSideEffectDTO1.getId());
        assertThat(applInfoSideEffectDTO1).isEqualTo(applInfoSideEffectDTO2);
        applInfoSideEffectDTO2.setId(2L);
        assertThat(applInfoSideEffectDTO1).isNotEqualTo(applInfoSideEffectDTO2);
        applInfoSideEffectDTO1.setId(null);
        assertThat(applInfoSideEffectDTO1).isNotEqualTo(applInfoSideEffectDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applInfoSideEffectMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applInfoSideEffectMapper.fromId(null)).isNull();
    }
}
