package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.Pediatric;
import com.medicai.pillpal.repository.PediatricRepository;
import com.medicai.pillpal.service.PediatricService;
import com.medicai.pillpal.service.dto.PediatricDTO;
import com.medicai.pillpal.service.mapper.PediatricMapper;
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
 * Integration tests for the {@Link PediatricResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class PediatricResourceIT {

    private static final String DEFAULT_PEDIATRIC = "AAAAAAAAAA";
    private static final String UPDATED_PEDIATRIC = "BBBBBBBBBB";

    @Autowired
    private PediatricRepository pediatricRepository;

    @Autowired
    private PediatricMapper pediatricMapper;

    @Autowired
    private PediatricService pediatricService;

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

    private MockMvc restPediatricMockMvc;

    private Pediatric pediatric;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PediatricResource pediatricResource = new PediatricResource(pediatricService);
        this.restPediatricMockMvc = MockMvcBuilders.standaloneSetup(pediatricResource)
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
    public static Pediatric createEntity(EntityManager em) {
        Pediatric pediatric = new Pediatric()
            .pediatric(DEFAULT_PEDIATRIC);
        return pediatric;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pediatric createUpdatedEntity(EntityManager em) {
        Pediatric pediatric = new Pediatric()
            .pediatric(UPDATED_PEDIATRIC);
        return pediatric;
    }

    @BeforeEach
    public void initTest() {
        pediatric = createEntity(em);
    }

    @Test
    @Transactional
    public void createPediatric() throws Exception {
        int databaseSizeBeforeCreate = pediatricRepository.findAll().size();

        // Create the Pediatric
        PediatricDTO pediatricDTO = pediatricMapper.toDto(pediatric);
        restPediatricMockMvc.perform(post("/api/pediatrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pediatricDTO)))
            .andExpect(status().isCreated());

        // Validate the Pediatric in the database
        List<Pediatric> pediatricList = pediatricRepository.findAll();
        assertThat(pediatricList).hasSize(databaseSizeBeforeCreate + 1);
        Pediatric testPediatric = pediatricList.get(pediatricList.size() - 1);
        assertThat(testPediatric.getPediatric()).isEqualTo(DEFAULT_PEDIATRIC);
    }

    @Test
    @Transactional
    public void createPediatricWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pediatricRepository.findAll().size();

        // Create the Pediatric with an existing ID
        pediatric.setId(1L);
        PediatricDTO pediatricDTO = pediatricMapper.toDto(pediatric);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPediatricMockMvc.perform(post("/api/pediatrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pediatricDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pediatric in the database
        List<Pediatric> pediatricList = pediatricRepository.findAll();
        assertThat(pediatricList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPediatrics() throws Exception {
        // Initialize the database
        pediatricRepository.saveAndFlush(pediatric);

        // Get all the pediatricList
        restPediatricMockMvc.perform(get("/api/pediatrics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pediatric.getId().intValue())))
            .andExpect(jsonPath("$.[*].pediatric").value(hasItem(DEFAULT_PEDIATRIC.toString())));
    }
    
    @Test
    @Transactional
    public void getPediatric() throws Exception {
        // Initialize the database
        pediatricRepository.saveAndFlush(pediatric);

        // Get the pediatric
        restPediatricMockMvc.perform(get("/api/pediatrics/{id}", pediatric.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pediatric.getId().intValue()))
            .andExpect(jsonPath("$.pediatric").value(DEFAULT_PEDIATRIC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPediatric() throws Exception {
        // Get the pediatric
        restPediatricMockMvc.perform(get("/api/pediatrics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePediatric() throws Exception {
        // Initialize the database
        pediatricRepository.saveAndFlush(pediatric);

        int databaseSizeBeforeUpdate = pediatricRepository.findAll().size();

        // Update the pediatric
        Pediatric updatedPediatric = pediatricRepository.findById(pediatric.getId()).get();
        // Disconnect from session so that the updates on updatedPediatric are not directly saved in db
        em.detach(updatedPediatric);
        updatedPediatric
            .pediatric(UPDATED_PEDIATRIC);
        PediatricDTO pediatricDTO = pediatricMapper.toDto(updatedPediatric);

        restPediatricMockMvc.perform(put("/api/pediatrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pediatricDTO)))
            .andExpect(status().isOk());

        // Validate the Pediatric in the database
        List<Pediatric> pediatricList = pediatricRepository.findAll();
        assertThat(pediatricList).hasSize(databaseSizeBeforeUpdate);
        Pediatric testPediatric = pediatricList.get(pediatricList.size() - 1);
        assertThat(testPediatric.getPediatric()).isEqualTo(UPDATED_PEDIATRIC);
    }

    @Test
    @Transactional
    public void updateNonExistingPediatric() throws Exception {
        int databaseSizeBeforeUpdate = pediatricRepository.findAll().size();

        // Create the Pediatric
        PediatricDTO pediatricDTO = pediatricMapper.toDto(pediatric);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPediatricMockMvc.perform(put("/api/pediatrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pediatricDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pediatric in the database
        List<Pediatric> pediatricList = pediatricRepository.findAll();
        assertThat(pediatricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePediatric() throws Exception {
        // Initialize the database
        pediatricRepository.saveAndFlush(pediatric);

        int databaseSizeBeforeDelete = pediatricRepository.findAll().size();

        // Delete the pediatric
        restPediatricMockMvc.perform(delete("/api/pediatrics/{id}", pediatric.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pediatric> pediatricList = pediatricRepository.findAll();
        assertThat(pediatricList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pediatric.class);
        Pediatric pediatric1 = new Pediatric();
        pediatric1.setId(1L);
        Pediatric pediatric2 = new Pediatric();
        pediatric2.setId(pediatric1.getId());
        assertThat(pediatric1).isEqualTo(pediatric2);
        pediatric2.setId(2L);
        assertThat(pediatric1).isNotEqualTo(pediatric2);
        pediatric1.setId(null);
        assertThat(pediatric1).isNotEqualTo(pediatric2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PediatricDTO.class);
        PediatricDTO pediatricDTO1 = new PediatricDTO();
        pediatricDTO1.setId(1L);
        PediatricDTO pediatricDTO2 = new PediatricDTO();
        assertThat(pediatricDTO1).isNotEqualTo(pediatricDTO2);
        pediatricDTO2.setId(pediatricDTO1.getId());
        assertThat(pediatricDTO1).isEqualTo(pediatricDTO2);
        pediatricDTO2.setId(2L);
        assertThat(pediatricDTO1).isNotEqualTo(pediatricDTO2);
        pediatricDTO1.setId(null);
        assertThat(pediatricDTO1).isNotEqualTo(pediatricDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pediatricMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pediatricMapper.fromId(null)).isNull();
    }
}
