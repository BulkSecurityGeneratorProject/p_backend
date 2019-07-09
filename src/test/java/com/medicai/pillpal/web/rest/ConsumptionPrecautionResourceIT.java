package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.ConsumptionPrecaution;
import com.medicai.pillpal.repository.ConsumptionPrecautionRepository;
import com.medicai.pillpal.service.ConsumptionPrecautionService;
import com.medicai.pillpal.service.dto.ConsumptionPrecautionDTO;
import com.medicai.pillpal.service.mapper.ConsumptionPrecautionMapper;
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
 * Integration tests for the {@Link ConsumptionPrecautionResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class ConsumptionPrecautionResourceIT {

    private static final String DEFAULT_CONCLUSION = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION = "BBBBBBBBBB";

    private static final String DEFAULT_PRECAUTION = "AAAAAAAAAA";
    private static final String UPDATED_PRECAUTION = "BBBBBBBBBB";

    @Autowired
    private ConsumptionPrecautionRepository consumptionPrecautionRepository;

    @Autowired
    private ConsumptionPrecautionMapper consumptionPrecautionMapper;

    @Autowired
    private ConsumptionPrecautionService consumptionPrecautionService;

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

    private MockMvc restConsumptionPrecautionMockMvc;

    private ConsumptionPrecaution consumptionPrecaution;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsumptionPrecautionResource consumptionPrecautionResource = new ConsumptionPrecautionResource(consumptionPrecautionService);
        this.restConsumptionPrecautionMockMvc = MockMvcBuilders.standaloneSetup(consumptionPrecautionResource)
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
    public static ConsumptionPrecaution createEntity(EntityManager em) {
        ConsumptionPrecaution consumptionPrecaution = new ConsumptionPrecaution()
            .conclusion(DEFAULT_CONCLUSION)
            .precaution(DEFAULT_PRECAUTION);
        return consumptionPrecaution;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsumptionPrecaution createUpdatedEntity(EntityManager em) {
        ConsumptionPrecaution consumptionPrecaution = new ConsumptionPrecaution()
            .conclusion(UPDATED_CONCLUSION)
            .precaution(UPDATED_PRECAUTION);
        return consumptionPrecaution;
    }

    @BeforeEach
    public void initTest() {
        consumptionPrecaution = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsumptionPrecaution() throws Exception {
        int databaseSizeBeforeCreate = consumptionPrecautionRepository.findAll().size();

        // Create the ConsumptionPrecaution
        ConsumptionPrecautionDTO consumptionPrecautionDTO = consumptionPrecautionMapper.toDto(consumptionPrecaution);
        restConsumptionPrecautionMockMvc.perform(post("/api/consumption-precautions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionPrecautionDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsumptionPrecaution in the database
        List<ConsumptionPrecaution> consumptionPrecautionList = consumptionPrecautionRepository.findAll();
        assertThat(consumptionPrecautionList).hasSize(databaseSizeBeforeCreate + 1);
        ConsumptionPrecaution testConsumptionPrecaution = consumptionPrecautionList.get(consumptionPrecautionList.size() - 1);
        assertThat(testConsumptionPrecaution.getConclusion()).isEqualTo(DEFAULT_CONCLUSION);
        assertThat(testConsumptionPrecaution.getPrecaution()).isEqualTo(DEFAULT_PRECAUTION);
    }

    @Test
    @Transactional
    public void createConsumptionPrecautionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consumptionPrecautionRepository.findAll().size();

        // Create the ConsumptionPrecaution with an existing ID
        consumptionPrecaution.setId(1L);
        ConsumptionPrecautionDTO consumptionPrecautionDTO = consumptionPrecautionMapper.toDto(consumptionPrecaution);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumptionPrecautionMockMvc.perform(post("/api/consumption-precautions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionPrecautionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionPrecaution in the database
        List<ConsumptionPrecaution> consumptionPrecautionList = consumptionPrecautionRepository.findAll();
        assertThat(consumptionPrecautionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConsumptionPrecautions() throws Exception {
        // Initialize the database
        consumptionPrecautionRepository.saveAndFlush(consumptionPrecaution);

        // Get all the consumptionPrecautionList
        restConsumptionPrecautionMockMvc.perform(get("/api/consumption-precautions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumptionPrecaution.getId().intValue())))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION.toString())))
            .andExpect(jsonPath("$.[*].precaution").value(hasItem(DEFAULT_PRECAUTION.toString())));
    }

    @Test
    @Transactional
    public void getConsumptionPrecaution() throws Exception {
        // Initialize the database
        consumptionPrecautionRepository.saveAndFlush(consumptionPrecaution);

        // Get the consumptionPrecaution
        restConsumptionPrecautionMockMvc.perform(get("/api/consumption-precautions/{id}", consumptionPrecaution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consumptionPrecaution.getId().intValue()))
            .andExpect(jsonPath("$.conclusion").value(DEFAULT_CONCLUSION.toString()))
            .andExpect(jsonPath("$.precaution").value(DEFAULT_PRECAUTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsumptionPrecaution() throws Exception {
        // Get the consumptionPrecaution
        restConsumptionPrecautionMockMvc.perform(get("/api/consumption-precautions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsumptionPrecaution() throws Exception {
        // Initialize the database
        consumptionPrecautionRepository.saveAndFlush(consumptionPrecaution);

        int databaseSizeBeforeUpdate = consumptionPrecautionRepository.findAll().size();

        // Update the consumptionPrecaution
        ConsumptionPrecaution updatedConsumptionPrecaution = consumptionPrecautionRepository.findById(consumptionPrecaution.getId()).get();
        // Disconnect from session so that the updates on updatedConsumptionPrecaution are not directly saved in db
        em.detach(updatedConsumptionPrecaution);
        updatedConsumptionPrecaution
            .conclusion(UPDATED_CONCLUSION)
            .precaution(UPDATED_PRECAUTION);
        ConsumptionPrecautionDTO consumptionPrecautionDTO = consumptionPrecautionMapper.toDto(updatedConsumptionPrecaution);

        restConsumptionPrecautionMockMvc.perform(put("/api/consumption-precautions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionPrecautionDTO)))
            .andExpect(status().isOk());

        // Validate the ConsumptionPrecaution in the database
        List<ConsumptionPrecaution> consumptionPrecautionList = consumptionPrecautionRepository.findAll();
        assertThat(consumptionPrecautionList).hasSize(databaseSizeBeforeUpdate);
        ConsumptionPrecaution testConsumptionPrecaution = consumptionPrecautionList.get(consumptionPrecautionList.size() - 1);
        assertThat(testConsumptionPrecaution.getConclusion()).isEqualTo(UPDATED_CONCLUSION);
        assertThat(testConsumptionPrecaution.getPrecaution()).isEqualTo(UPDATED_PRECAUTION);
    }

    @Test
    @Transactional
    public void updateNonExistingConsumptionPrecaution() throws Exception {
        int databaseSizeBeforeUpdate = consumptionPrecautionRepository.findAll().size();

        // Create the ConsumptionPrecaution
        ConsumptionPrecautionDTO consumptionPrecautionDTO = consumptionPrecautionMapper.toDto(consumptionPrecaution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumptionPrecautionMockMvc.perform(put("/api/consumption-precautions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionPrecautionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionPrecaution in the database
        List<ConsumptionPrecaution> consumptionPrecautionList = consumptionPrecautionRepository.findAll();
        assertThat(consumptionPrecautionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsumptionPrecaution() throws Exception {
        // Initialize the database
        consumptionPrecautionRepository.saveAndFlush(consumptionPrecaution);

        int databaseSizeBeforeDelete = consumptionPrecautionRepository.findAll().size();

        // Delete the consumptionPrecaution
        restConsumptionPrecautionMockMvc.perform(delete("/api/consumption-precautions/{id}", consumptionPrecaution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConsumptionPrecaution> consumptionPrecautionList = consumptionPrecautionRepository.findAll();
        assertThat(consumptionPrecautionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionPrecaution.class);
        ConsumptionPrecaution consumptionPrecaution1 = new ConsumptionPrecaution();
        consumptionPrecaution1.setId(1L);
        ConsumptionPrecaution consumptionPrecaution2 = new ConsumptionPrecaution();
        consumptionPrecaution2.setId(consumptionPrecaution1.getId());
        assertThat(consumptionPrecaution1).isEqualTo(consumptionPrecaution2);
        consumptionPrecaution2.setId(2L);
        assertThat(consumptionPrecaution1).isNotEqualTo(consumptionPrecaution2);
        consumptionPrecaution1.setId(null);
        assertThat(consumptionPrecaution1).isNotEqualTo(consumptionPrecaution2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionPrecautionDTO.class);
        ConsumptionPrecautionDTO consumptionPrecautionDTO1 = new ConsumptionPrecautionDTO();
        consumptionPrecautionDTO1.setId(1L);
        ConsumptionPrecautionDTO consumptionPrecautionDTO2 = new ConsumptionPrecautionDTO();
        assertThat(consumptionPrecautionDTO1).isNotEqualTo(consumptionPrecautionDTO2);
        consumptionPrecautionDTO2.setId(consumptionPrecautionDTO1.getId());
        assertThat(consumptionPrecautionDTO1).isEqualTo(consumptionPrecautionDTO2);
        consumptionPrecautionDTO2.setId(2L);
        assertThat(consumptionPrecautionDTO1).isNotEqualTo(consumptionPrecautionDTO2);
        consumptionPrecautionDTO1.setId(null);
        assertThat(consumptionPrecautionDTO1).isNotEqualTo(consumptionPrecautionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consumptionPrecautionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consumptionPrecautionMapper.fromId(null)).isNull();
    }
}
