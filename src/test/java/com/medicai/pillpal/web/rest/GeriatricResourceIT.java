package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.Geriatric;
import com.medicai.pillpal.repository.GeriatricRepository;
import com.medicai.pillpal.service.GeriatricService;
import com.medicai.pillpal.service.dto.GeriatricDTO;
import com.medicai.pillpal.service.mapper.GeriatricMapper;
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
 * Integration tests for the {@Link GeriatricResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class GeriatricResourceIT {

    private static final String DEFAULT_GERIATRIC = "AAAAAAAAAA";
    private static final String UPDATED_GERIATRIC = "BBBBBBBBBB";

    @Autowired
    private GeriatricRepository geriatricRepository;

    @Autowired
    private GeriatricMapper geriatricMapper;

    @Autowired
    private GeriatricService geriatricService;

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

    private MockMvc restGeriatricMockMvc;

    private Geriatric geriatric;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GeriatricResource geriatricResource = new GeriatricResource(geriatricService);
        this.restGeriatricMockMvc = MockMvcBuilders.standaloneSetup(geriatricResource)
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
    public static Geriatric createEntity(EntityManager em) {
        Geriatric geriatric = new Geriatric()
            .geriatric(DEFAULT_GERIATRIC);
        return geriatric;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geriatric createUpdatedEntity(EntityManager em) {
        Geriatric geriatric = new Geriatric()
            .geriatric(UPDATED_GERIATRIC);
        return geriatric;
    }

    @BeforeEach
    public void initTest() {
        geriatric = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeriatric() throws Exception {
        int databaseSizeBeforeCreate = geriatricRepository.findAll().size();

        // Create the Geriatric
        GeriatricDTO geriatricDTO = geriatricMapper.toDto(geriatric);
        restGeriatricMockMvc.perform(post("/api/geriatrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geriatricDTO)))
            .andExpect(status().isCreated());

        // Validate the Geriatric in the database
        List<Geriatric> geriatricList = geriatricRepository.findAll();
        assertThat(geriatricList).hasSize(databaseSizeBeforeCreate + 1);
        Geriatric testGeriatric = geriatricList.get(geriatricList.size() - 1);
        assertThat(testGeriatric.getGeriatric()).isEqualTo(DEFAULT_GERIATRIC);
    }

    @Test
    @Transactional
    public void createGeriatricWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geriatricRepository.findAll().size();

        // Create the Geriatric with an existing ID
        geriatric.setId(1L);
        GeriatricDTO geriatricDTO = geriatricMapper.toDto(geriatric);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeriatricMockMvc.perform(post("/api/geriatrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geriatricDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Geriatric in the database
        List<Geriatric> geriatricList = geriatricRepository.findAll();
        assertThat(geriatricList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGeriatrics() throws Exception {
        // Initialize the database
        geriatricRepository.saveAndFlush(geriatric);

        // Get all the geriatricList
        restGeriatricMockMvc.perform(get("/api/geriatrics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geriatric.getId().intValue())))
            .andExpect(jsonPath("$.[*].geriatric").value(hasItem(DEFAULT_GERIATRIC.toString())));
    }

    @Test
    @Transactional
    public void getGeriatric() throws Exception {
        // Initialize the database
        geriatricRepository.saveAndFlush(geriatric);

        // Get the geriatric
        restGeriatricMockMvc.perform(get("/api/geriatrics/{id}", geriatric.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(geriatric.getId().intValue()))
            .andExpect(jsonPath("$.geriatric").value(DEFAULT_GERIATRIC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGeriatric() throws Exception {
        // Get the geriatric
        restGeriatricMockMvc.perform(get("/api/geriatrics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeriatric() throws Exception {
        // Initialize the database
        geriatricRepository.saveAndFlush(geriatric);

        int databaseSizeBeforeUpdate = geriatricRepository.findAll().size();

        // Update the geriatric
        Geriatric updatedGeriatric = geriatricRepository.findById(geriatric.getId()).get();
        // Disconnect from session so that the updates on updatedGeriatric are not directly saved in db
        em.detach(updatedGeriatric);
        updatedGeriatric
            .geriatric(UPDATED_GERIATRIC);
        GeriatricDTO geriatricDTO = geriatricMapper.toDto(updatedGeriatric);

        restGeriatricMockMvc.perform(put("/api/geriatrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geriatricDTO)))
            .andExpect(status().isOk());

        // Validate the Geriatric in the database
        List<Geriatric> geriatricList = geriatricRepository.findAll();
        assertThat(geriatricList).hasSize(databaseSizeBeforeUpdate);
        Geriatric testGeriatric = geriatricList.get(geriatricList.size() - 1);
        assertThat(testGeriatric.getGeriatric()).isEqualTo(UPDATED_GERIATRIC);
    }

    @Test
    @Transactional
    public void updateNonExistingGeriatric() throws Exception {
        int databaseSizeBeforeUpdate = geriatricRepository.findAll().size();

        // Create the Geriatric
        GeriatricDTO geriatricDTO = geriatricMapper.toDto(geriatric);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeriatricMockMvc.perform(put("/api/geriatrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geriatricDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Geriatric in the database
        List<Geriatric> geriatricList = geriatricRepository.findAll();
        assertThat(geriatricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGeriatric() throws Exception {
        // Initialize the database
        geriatricRepository.saveAndFlush(geriatric);

        int databaseSizeBeforeDelete = geriatricRepository.findAll().size();

        // Delete the geriatric
        restGeriatricMockMvc.perform(delete("/api/geriatrics/{id}", geriatric.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Geriatric> geriatricList = geriatricRepository.findAll();
        assertThat(geriatricList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Geriatric.class);
        Geriatric geriatric1 = new Geriatric();
        geriatric1.setId(1L);
        Geriatric geriatric2 = new Geriatric();
        geriatric2.setId(geriatric1.getId());
        assertThat(geriatric1).isEqualTo(geriatric2);
        geriatric2.setId(2L);
        assertThat(geriatric1).isNotEqualTo(geriatric2);
        geriatric1.setId(null);
        assertThat(geriatric1).isNotEqualTo(geriatric2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeriatricDTO.class);
        GeriatricDTO geriatricDTO1 = new GeriatricDTO();
        geriatricDTO1.setId(1L);
        GeriatricDTO geriatricDTO2 = new GeriatricDTO();
        assertThat(geriatricDTO1).isNotEqualTo(geriatricDTO2);
        geriatricDTO2.setId(geriatricDTO1.getId());
        assertThat(geriatricDTO1).isEqualTo(geriatricDTO2);
        geriatricDTO2.setId(2L);
        assertThat(geriatricDTO1).isNotEqualTo(geriatricDTO2);
        geriatricDTO1.setId(null);
        assertThat(geriatricDTO1).isNotEqualTo(geriatricDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(geriatricMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(geriatricMapper.fromId(null)).isNull();
    }
}
