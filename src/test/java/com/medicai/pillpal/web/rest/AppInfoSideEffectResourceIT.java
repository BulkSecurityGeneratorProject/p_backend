package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.AppInfoSideEffect;
import com.medicai.pillpal.domain.enumeration.SideEffectType;
import com.medicai.pillpal.repository.AppInfoSideEffectRepository;
import com.medicai.pillpal.service.AppInfoSideEffectService;
import com.medicai.pillpal.service.dto.AppInfoSideEffectDTO;
import com.medicai.pillpal.service.mapper.AppInfoSideEffectMapper;
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

/**
 * Integration tests for the {@Link AppInfoSideEffectResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class AppInfoSideEffectResourceIT {

    private static final SideEffectType DEFAULT_SIDE_EFFECT_TYPE = SideEffectType.COMMON;
    private static final SideEffectType UPDATED_SIDE_EFFECT_TYPE = SideEffectType.RARE;

    @Autowired
    private AppInfoSideEffectRepository appInfoSideEffectRepository;

    @Autowired
    private AppInfoSideEffectMapper appInfoSideEffectMapper;

    @Autowired
    private AppInfoSideEffectService appInfoSideEffectService;

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

    private MockMvc restAppInfoSideEffectMockMvc;

    private AppInfoSideEffect appInfoSideEffect;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppInfoSideEffectResource appInfoSideEffectResource = new AppInfoSideEffectResource(appInfoSideEffectService);
        this.restAppInfoSideEffectMockMvc = MockMvcBuilders.standaloneSetup(appInfoSideEffectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppInfoSideEffect createEntity(EntityManager em) {
        AppInfoSideEffect appInfoSideEffect = new AppInfoSideEffect()
            .sideEffectType(DEFAULT_SIDE_EFFECT_TYPE);
        return appInfoSideEffect;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppInfoSideEffect createUpdatedEntity(EntityManager em) {
        AppInfoSideEffect appInfoSideEffect = new AppInfoSideEffect()
            .sideEffectType(UPDATED_SIDE_EFFECT_TYPE);
        return appInfoSideEffect;
    }

    @BeforeEach
    public void initTest() {
        appInfoSideEffect = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppInfoSideEffect() throws Exception {
        int databaseSizeBeforeCreate = appInfoSideEffectRepository.findAll().size();

        // Create the AppInfoSideEffect
        AppInfoSideEffectDTO appInfoSideEffectDTO = appInfoSideEffectMapper.toDto(appInfoSideEffect);
        restAppInfoSideEffectMockMvc.perform(post("/api/app-info-side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appInfoSideEffectDTO)))
            .andExpect(status().isCreated());

        // Validate the AppInfoSideEffect in the database
        List<AppInfoSideEffect> appInfoSideEffectList = appInfoSideEffectRepository.findAll();
        assertThat(appInfoSideEffectList).hasSize(databaseSizeBeforeCreate + 1);
        AppInfoSideEffect testAppInfoSideEffect = appInfoSideEffectList.get(appInfoSideEffectList.size() - 1);
        assertThat(testAppInfoSideEffect.getSideEffectType()).isEqualTo(DEFAULT_SIDE_EFFECT_TYPE);
    }

    @Test
    @Transactional
    public void createAppInfoSideEffectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appInfoSideEffectRepository.findAll().size();

        // Create the AppInfoSideEffect with an existing ID
        appInfoSideEffect.setId(1L);
        AppInfoSideEffectDTO appInfoSideEffectDTO = appInfoSideEffectMapper.toDto(appInfoSideEffect);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppInfoSideEffectMockMvc.perform(post("/api/app-info-side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appInfoSideEffectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppInfoSideEffect in the database
        List<AppInfoSideEffect> appInfoSideEffectList = appInfoSideEffectRepository.findAll();
        assertThat(appInfoSideEffectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAppInfoSideEffects() throws Exception {
        // Initialize the database
        appInfoSideEffectRepository.saveAndFlush(appInfoSideEffect);

        // Get all the appInfoSideEffectList
        restAppInfoSideEffectMockMvc.perform(get("/api/app-info-side-effects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appInfoSideEffect.getId().intValue())))
            .andExpect(jsonPath("$.[*].sideEffectType").value(hasItem(DEFAULT_SIDE_EFFECT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getAppInfoSideEffect() throws Exception {
        // Initialize the database
        appInfoSideEffectRepository.saveAndFlush(appInfoSideEffect);

        // Get the appInfoSideEffect
        restAppInfoSideEffectMockMvc.perform(get("/api/app-info-side-effects/{id}", appInfoSideEffect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appInfoSideEffect.getId().intValue()))
            .andExpect(jsonPath("$.sideEffectType").value(DEFAULT_SIDE_EFFECT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppInfoSideEffect() throws Exception {
        // Get the appInfoSideEffect
        restAppInfoSideEffectMockMvc.perform(get("/api/app-info-side-effects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppInfoSideEffect() throws Exception {
        // Initialize the database
        appInfoSideEffectRepository.saveAndFlush(appInfoSideEffect);

        int databaseSizeBeforeUpdate = appInfoSideEffectRepository.findAll().size();

        // Update the appInfoSideEffect
        AppInfoSideEffect updatedAppInfoSideEffect = appInfoSideEffectRepository.findById(appInfoSideEffect.getId()).get();
        // Disconnect from session so that the updates on updatedAppInfoSideEffect are not directly saved in db
        em.detach(updatedAppInfoSideEffect);
        updatedAppInfoSideEffect
            .sideEffectType(UPDATED_SIDE_EFFECT_TYPE);
        AppInfoSideEffectDTO appInfoSideEffectDTO = appInfoSideEffectMapper.toDto(updatedAppInfoSideEffect);

        restAppInfoSideEffectMockMvc.perform(put("/api/app-info-side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appInfoSideEffectDTO)))
            .andExpect(status().isOk());

        // Validate the AppInfoSideEffect in the database
        List<AppInfoSideEffect> appInfoSideEffectList = appInfoSideEffectRepository.findAll();
        assertThat(appInfoSideEffectList).hasSize(databaseSizeBeforeUpdate);
        AppInfoSideEffect testAppInfoSideEffect = appInfoSideEffectList.get(appInfoSideEffectList.size() - 1);
        assertThat(testAppInfoSideEffect.getSideEffectType()).isEqualTo(UPDATED_SIDE_EFFECT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAppInfoSideEffect() throws Exception {
        int databaseSizeBeforeUpdate = appInfoSideEffectRepository.findAll().size();

        // Create the AppInfoSideEffect
        AppInfoSideEffectDTO appInfoSideEffectDTO = appInfoSideEffectMapper.toDto(appInfoSideEffect);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppInfoSideEffectMockMvc.perform(put("/api/app-info-side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appInfoSideEffectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppInfoSideEffect in the database
        List<AppInfoSideEffect> appInfoSideEffectList = appInfoSideEffectRepository.findAll();
        assertThat(appInfoSideEffectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppInfoSideEffect() throws Exception {
        // Initialize the database
        appInfoSideEffectRepository.saveAndFlush(appInfoSideEffect);

        int databaseSizeBeforeDelete = appInfoSideEffectRepository.findAll().size();

        // Delete the appInfoSideEffect
        restAppInfoSideEffectMockMvc.perform(delete("/api/app-info-side-effects/{id}", appInfoSideEffect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppInfoSideEffect> appInfoSideEffectList = appInfoSideEffectRepository.findAll();
        assertThat(appInfoSideEffectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppInfoSideEffect.class);
        AppInfoSideEffect appInfoSideEffect1 = new AppInfoSideEffect();
        appInfoSideEffect1.setId(1L);
        AppInfoSideEffect appInfoSideEffect2 = new AppInfoSideEffect();
        appInfoSideEffect2.setId(appInfoSideEffect1.getId());
        assertThat(appInfoSideEffect1).isEqualTo(appInfoSideEffect2);
        appInfoSideEffect2.setId(2L);
        assertThat(appInfoSideEffect1).isNotEqualTo(appInfoSideEffect2);
        appInfoSideEffect1.setId(null);
        assertThat(appInfoSideEffect1).isNotEqualTo(appInfoSideEffect2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppInfoSideEffectDTO.class);
        AppInfoSideEffectDTO appInfoSideEffectDTO1 = new AppInfoSideEffectDTO();
        appInfoSideEffectDTO1.setId(1L);
        AppInfoSideEffectDTO appInfoSideEffectDTO2 = new AppInfoSideEffectDTO();
        assertThat(appInfoSideEffectDTO1).isNotEqualTo(appInfoSideEffectDTO2);
        appInfoSideEffectDTO2.setId(appInfoSideEffectDTO1.getId());
        assertThat(appInfoSideEffectDTO1).isEqualTo(appInfoSideEffectDTO2);
        appInfoSideEffectDTO2.setId(2L);
        assertThat(appInfoSideEffectDTO1).isNotEqualTo(appInfoSideEffectDTO2);
        appInfoSideEffectDTO1.setId(null);
        assertThat(appInfoSideEffectDTO1).isNotEqualTo(appInfoSideEffectDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(appInfoSideEffectMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(appInfoSideEffectMapper.fromId(null)).isNull();
    }
}
