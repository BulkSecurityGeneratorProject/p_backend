package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.Pregnancy;
import com.medicai.pillpal.repository.PregnancyRepository;
import com.medicai.pillpal.service.PregnancyService;
import com.medicai.pillpal.service.dto.PregnancyDTO;
import com.medicai.pillpal.service.mapper.PregnancyMapper;
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
 * Integration tests for the {@Link PregnancyResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class PregnancyResourceIT {

    private static final String DEFAULT_PRE_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_PRE_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_PRE_EXPLANATION = "AAAAAAAAAA";
    private static final String UPDATED_PRE_EXPLANATION = "BBBBBBBBBB";

    @Autowired
    private PregnancyRepository pregnancyRepository;

    @Autowired
    private PregnancyMapper pregnancyMapper;

    @Autowired
    private PregnancyService pregnancyService;

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

    private MockMvc restPregnancyMockMvc;

    private Pregnancy pregnancy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PregnancyResource pregnancyResource = new PregnancyResource(pregnancyService);
        this.restPregnancyMockMvc = MockMvcBuilders.standaloneSetup(pregnancyResource)
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
    public static Pregnancy createEntity(EntityManager em) {
        Pregnancy pregnancy = new Pregnancy()
            .preCategory(DEFAULT_PRE_CATEGORY)
            .preExplanation(DEFAULT_PRE_EXPLANATION);
        return pregnancy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pregnancy createUpdatedEntity(EntityManager em) {
        Pregnancy pregnancy = new Pregnancy()
            .preCategory(UPDATED_PRE_CATEGORY)
            .preExplanation(UPDATED_PRE_EXPLANATION);
        return pregnancy;
    }

    @BeforeEach
    public void initTest() {
        pregnancy = createEntity(em);
    }

    @Test
    @Transactional
    public void createPregnancy() throws Exception {
        int databaseSizeBeforeCreate = pregnancyRepository.findAll().size();

        // Create the Pregnancy
        PregnancyDTO pregnancyDTO = pregnancyMapper.toDto(pregnancy);
        restPregnancyMockMvc.perform(post("/api/pregnancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pregnancyDTO)))
            .andExpect(status().isCreated());

        // Validate the Pregnancy in the database
        List<Pregnancy> pregnancyList = pregnancyRepository.findAll();
        assertThat(pregnancyList).hasSize(databaseSizeBeforeCreate + 1);
        Pregnancy testPregnancy = pregnancyList.get(pregnancyList.size() - 1);
        assertThat(testPregnancy.getPreCategory()).isEqualTo(DEFAULT_PRE_CATEGORY);
        assertThat(testPregnancy.getPreExplanation()).isEqualTo(DEFAULT_PRE_EXPLANATION);
    }

    @Test
    @Transactional
    public void createPregnancyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pregnancyRepository.findAll().size();

        // Create the Pregnancy with an existing ID
        pregnancy.setId(1L);
        PregnancyDTO pregnancyDTO = pregnancyMapper.toDto(pregnancy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPregnancyMockMvc.perform(post("/api/pregnancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pregnancyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pregnancy in the database
        List<Pregnancy> pregnancyList = pregnancyRepository.findAll();
        assertThat(pregnancyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPregnancies() throws Exception {
        // Initialize the database
        pregnancyRepository.saveAndFlush(pregnancy);

        // Get all the pregnancyList
        restPregnancyMockMvc.perform(get("/api/pregnancies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pregnancy.getId().intValue())))
            .andExpect(jsonPath("$.[*].preCategory").value(hasItem(DEFAULT_PRE_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].preExplanation").value(hasItem(DEFAULT_PRE_EXPLANATION.toString())));
    }
    
    @Test
    @Transactional
    public void getPregnancy() throws Exception {
        // Initialize the database
        pregnancyRepository.saveAndFlush(pregnancy);

        // Get the pregnancy
        restPregnancyMockMvc.perform(get("/api/pregnancies/{id}", pregnancy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pregnancy.getId().intValue()))
            .andExpect(jsonPath("$.preCategory").value(DEFAULT_PRE_CATEGORY.toString()))
            .andExpect(jsonPath("$.preExplanation").value(DEFAULT_PRE_EXPLANATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPregnancy() throws Exception {
        // Get the pregnancy
        restPregnancyMockMvc.perform(get("/api/pregnancies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePregnancy() throws Exception {
        // Initialize the database
        pregnancyRepository.saveAndFlush(pregnancy);

        int databaseSizeBeforeUpdate = pregnancyRepository.findAll().size();

        // Update the pregnancy
        Pregnancy updatedPregnancy = pregnancyRepository.findById(pregnancy.getId()).get();
        // Disconnect from session so that the updates on updatedPregnancy are not directly saved in db
        em.detach(updatedPregnancy);
        updatedPregnancy
            .preCategory(UPDATED_PRE_CATEGORY)
            .preExplanation(UPDATED_PRE_EXPLANATION);
        PregnancyDTO pregnancyDTO = pregnancyMapper.toDto(updatedPregnancy);

        restPregnancyMockMvc.perform(put("/api/pregnancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pregnancyDTO)))
            .andExpect(status().isOk());

        // Validate the Pregnancy in the database
        List<Pregnancy> pregnancyList = pregnancyRepository.findAll();
        assertThat(pregnancyList).hasSize(databaseSizeBeforeUpdate);
        Pregnancy testPregnancy = pregnancyList.get(pregnancyList.size() - 1);
        assertThat(testPregnancy.getPreCategory()).isEqualTo(UPDATED_PRE_CATEGORY);
        assertThat(testPregnancy.getPreExplanation()).isEqualTo(UPDATED_PRE_EXPLANATION);
    }

    @Test
    @Transactional
    public void updateNonExistingPregnancy() throws Exception {
        int databaseSizeBeforeUpdate = pregnancyRepository.findAll().size();

        // Create the Pregnancy
        PregnancyDTO pregnancyDTO = pregnancyMapper.toDto(pregnancy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPregnancyMockMvc.perform(put("/api/pregnancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pregnancyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pregnancy in the database
        List<Pregnancy> pregnancyList = pregnancyRepository.findAll();
        assertThat(pregnancyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePregnancy() throws Exception {
        // Initialize the database
        pregnancyRepository.saveAndFlush(pregnancy);

        int databaseSizeBeforeDelete = pregnancyRepository.findAll().size();

        // Delete the pregnancy
        restPregnancyMockMvc.perform(delete("/api/pregnancies/{id}", pregnancy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pregnancy> pregnancyList = pregnancyRepository.findAll();
        assertThat(pregnancyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pregnancy.class);
        Pregnancy pregnancy1 = new Pregnancy();
        pregnancy1.setId(1L);
        Pregnancy pregnancy2 = new Pregnancy();
        pregnancy2.setId(pregnancy1.getId());
        assertThat(pregnancy1).isEqualTo(pregnancy2);
        pregnancy2.setId(2L);
        assertThat(pregnancy1).isNotEqualTo(pregnancy2);
        pregnancy1.setId(null);
        assertThat(pregnancy1).isNotEqualTo(pregnancy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PregnancyDTO.class);
        PregnancyDTO pregnancyDTO1 = new PregnancyDTO();
        pregnancyDTO1.setId(1L);
        PregnancyDTO pregnancyDTO2 = new PregnancyDTO();
        assertThat(pregnancyDTO1).isNotEqualTo(pregnancyDTO2);
        pregnancyDTO2.setId(pregnancyDTO1.getId());
        assertThat(pregnancyDTO1).isEqualTo(pregnancyDTO2);
        pregnancyDTO2.setId(2L);
        assertThat(pregnancyDTO1).isNotEqualTo(pregnancyDTO2);
        pregnancyDTO1.setId(null);
        assertThat(pregnancyDTO1).isNotEqualTo(pregnancyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pregnancyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pregnancyMapper.fromId(null)).isNull();
    }
}
