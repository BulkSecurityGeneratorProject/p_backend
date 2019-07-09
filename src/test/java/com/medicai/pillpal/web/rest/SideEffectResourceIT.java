package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.SideEffect;
import com.medicai.pillpal.repository.SideEffectRepository;
import com.medicai.pillpal.service.SideEffectService;
import com.medicai.pillpal.service.dto.SideEffectDTO;
import com.medicai.pillpal.service.mapper.SideEffectMapper;
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
 * Integration tests for the {@Link SideEffectResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class SideEffectResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SideEffectRepository sideEffectRepository;

    @Autowired
    private SideEffectMapper sideEffectMapper;

    @Autowired
    private SideEffectService sideEffectService;

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

    private MockMvc restSideEffectMockMvc;

    private SideEffect sideEffect;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SideEffectResource sideEffectResource = new SideEffectResource(sideEffectService);
        this.restSideEffectMockMvc = MockMvcBuilders.standaloneSetup(sideEffectResource)
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
    public static SideEffect createEntity(EntityManager em) {
        SideEffect sideEffect = new SideEffect()
            .description(DEFAULT_DESCRIPTION);
        return sideEffect;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SideEffect createUpdatedEntity(EntityManager em) {
        SideEffect sideEffect = new SideEffect()
            .description(UPDATED_DESCRIPTION);
        return sideEffect;
    }

    @BeforeEach
    public void initTest() {
        sideEffect = createEntity(em);
    }

    @Test
    @Transactional
    public void createSideEffect() throws Exception {
        int databaseSizeBeforeCreate = sideEffectRepository.findAll().size();

        // Create the SideEffect
        SideEffectDTO sideEffectDTO = sideEffectMapper.toDto(sideEffect);
        restSideEffectMockMvc.perform(post("/api/side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sideEffectDTO)))
            .andExpect(status().isCreated());

        // Validate the SideEffect in the database
        List<SideEffect> sideEffectList = sideEffectRepository.findAll();
        assertThat(sideEffectList).hasSize(databaseSizeBeforeCreate + 1);
        SideEffect testSideEffect = sideEffectList.get(sideEffectList.size() - 1);
        assertThat(testSideEffect.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSideEffectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sideEffectRepository.findAll().size();

        // Create the SideEffect with an existing ID
        sideEffect.setId(1L);
        SideEffectDTO sideEffectDTO = sideEffectMapper.toDto(sideEffect);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSideEffectMockMvc.perform(post("/api/side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sideEffectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SideEffect in the database
        List<SideEffect> sideEffectList = sideEffectRepository.findAll();
        assertThat(sideEffectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sideEffectRepository.findAll().size();
        // set the field null
        sideEffect.setDescription(null);

        // Create the SideEffect, which fails.
        SideEffectDTO sideEffectDTO = sideEffectMapper.toDto(sideEffect);

        restSideEffectMockMvc.perform(post("/api/side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sideEffectDTO)))
            .andExpect(status().isBadRequest());

        List<SideEffect> sideEffectList = sideEffectRepository.findAll();
        assertThat(sideEffectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSideEffects() throws Exception {
        // Initialize the database
        sideEffectRepository.saveAndFlush(sideEffect);

        // Get all the sideEffectList
        restSideEffectMockMvc.perform(get("/api/side-effects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sideEffect.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSideEffect() throws Exception {
        // Initialize the database
        sideEffectRepository.saveAndFlush(sideEffect);

        // Get the sideEffect
        restSideEffectMockMvc.perform(get("/api/side-effects/{id}", sideEffect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sideEffect.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSideEffect() throws Exception {
        // Get the sideEffect
        restSideEffectMockMvc.perform(get("/api/side-effects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSideEffect() throws Exception {
        // Initialize the database
        sideEffectRepository.saveAndFlush(sideEffect);

        int databaseSizeBeforeUpdate = sideEffectRepository.findAll().size();

        // Update the sideEffect
        SideEffect updatedSideEffect = sideEffectRepository.findById(sideEffect.getId()).get();
        // Disconnect from session so that the updates on updatedSideEffect are not directly saved in db
        em.detach(updatedSideEffect);
        updatedSideEffect
            .description(UPDATED_DESCRIPTION);
        SideEffectDTO sideEffectDTO = sideEffectMapper.toDto(updatedSideEffect);

        restSideEffectMockMvc.perform(put("/api/side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sideEffectDTO)))
            .andExpect(status().isOk());

        // Validate the SideEffect in the database
        List<SideEffect> sideEffectList = sideEffectRepository.findAll();
        assertThat(sideEffectList).hasSize(databaseSizeBeforeUpdate);
        SideEffect testSideEffect = sideEffectList.get(sideEffectList.size() - 1);
        assertThat(testSideEffect.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSideEffect() throws Exception {
        int databaseSizeBeforeUpdate = sideEffectRepository.findAll().size();

        // Create the SideEffect
        SideEffectDTO sideEffectDTO = sideEffectMapper.toDto(sideEffect);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSideEffectMockMvc.perform(put("/api/side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sideEffectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SideEffect in the database
        List<SideEffect> sideEffectList = sideEffectRepository.findAll();
        assertThat(sideEffectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSideEffect() throws Exception {
        // Initialize the database
        sideEffectRepository.saveAndFlush(sideEffect);

        int databaseSizeBeforeDelete = sideEffectRepository.findAll().size();

        // Delete the sideEffect
        restSideEffectMockMvc.perform(delete("/api/side-effects/{id}", sideEffect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SideEffect> sideEffectList = sideEffectRepository.findAll();
        assertThat(sideEffectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SideEffect.class);
        SideEffect sideEffect1 = new SideEffect();
        sideEffect1.setId(1L);
        SideEffect sideEffect2 = new SideEffect();
        sideEffect2.setId(sideEffect1.getId());
        assertThat(sideEffect1).isEqualTo(sideEffect2);
        sideEffect2.setId(2L);
        assertThat(sideEffect1).isNotEqualTo(sideEffect2);
        sideEffect1.setId(null);
        assertThat(sideEffect1).isNotEqualTo(sideEffect2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SideEffectDTO.class);
        SideEffectDTO sideEffectDTO1 = new SideEffectDTO();
        sideEffectDTO1.setId(1L);
        SideEffectDTO sideEffectDTO2 = new SideEffectDTO();
        assertThat(sideEffectDTO1).isNotEqualTo(sideEffectDTO2);
        sideEffectDTO2.setId(sideEffectDTO1.getId());
        assertThat(sideEffectDTO1).isEqualTo(sideEffectDTO2);
        sideEffectDTO2.setId(2L);
        assertThat(sideEffectDTO1).isNotEqualTo(sideEffectDTO2);
        sideEffectDTO1.setId(null);
        assertThat(sideEffectDTO1).isNotEqualTo(sideEffectDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sideEffectMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sideEffectMapper.fromId(null)).isNull();
    }
}
