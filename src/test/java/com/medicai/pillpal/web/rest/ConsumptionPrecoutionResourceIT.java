package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.ConsumptionPrecoution;
import com.medicai.pillpal.repository.ConsumptionPrecoutionRepository;
import com.medicai.pillpal.service.ConsumptionPrecoutionService;
import com.medicai.pillpal.service.dto.ConsumptionPrecoutionDTO;
import com.medicai.pillpal.service.mapper.ConsumptionPrecoutionMapper;
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
 * Integration tests for the {@Link ConsumptionPrecoutionResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class ConsumptionPrecoutionResourceIT {

    private static final String DEFAULT_CONCLUSION = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION = "BBBBBBBBBB";

    private static final String DEFAULT_PRECOUTION = "AAAAAAAAAA";
    private static final String UPDATED_PRECOUTION = "BBBBBBBBBB";

    @Autowired
    private ConsumptionPrecoutionRepository consumptionPrecoutionRepository;

    @Autowired
    private ConsumptionPrecoutionMapper consumptionPrecoutionMapper;

    @Autowired
    private ConsumptionPrecoutionService consumptionPrecoutionService;

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

    private MockMvc restConsumptionPrecoutionMockMvc;

    private ConsumptionPrecoution consumptionPrecoution;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsumptionPrecoutionResource consumptionPrecoutionResource = new ConsumptionPrecoutionResource(consumptionPrecoutionService);
        this.restConsumptionPrecoutionMockMvc = MockMvcBuilders.standaloneSetup(consumptionPrecoutionResource)
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
    public static ConsumptionPrecoution createEntity(EntityManager em) {
        ConsumptionPrecoution consumptionPrecoution = new ConsumptionPrecoution()
            .conclusion(DEFAULT_CONCLUSION)
            .precoution(DEFAULT_PRECOUTION);
        return consumptionPrecoution;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsumptionPrecoution createUpdatedEntity(EntityManager em) {
        ConsumptionPrecoution consumptionPrecoution = new ConsumptionPrecoution()
            .conclusion(UPDATED_CONCLUSION)
            .precoution(UPDATED_PRECOUTION);
        return consumptionPrecoution;
    }

    @BeforeEach
    public void initTest() {
        consumptionPrecoution = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsumptionPrecoution() throws Exception {
        int databaseSizeBeforeCreate = consumptionPrecoutionRepository.findAll().size();

        // Create the ConsumptionPrecoution
        ConsumptionPrecoutionDTO consumptionPrecoutionDTO = consumptionPrecoutionMapper.toDto(consumptionPrecoution);
        restConsumptionPrecoutionMockMvc.perform(post("/api/consumption-precoutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionPrecoutionDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsumptionPrecoution in the database
        List<ConsumptionPrecoution> consumptionPrecoutionList = consumptionPrecoutionRepository.findAll();
        assertThat(consumptionPrecoutionList).hasSize(databaseSizeBeforeCreate + 1);
        ConsumptionPrecoution testConsumptionPrecoution = consumptionPrecoutionList.get(consumptionPrecoutionList.size() - 1);
        assertThat(testConsumptionPrecoution.getConclusion()).isEqualTo(DEFAULT_CONCLUSION);
        assertThat(testConsumptionPrecoution.getPrecoution()).isEqualTo(DEFAULT_PRECOUTION);
    }

    @Test
    @Transactional
    public void createConsumptionPrecoutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consumptionPrecoutionRepository.findAll().size();

        // Create the ConsumptionPrecoution with an existing ID
        consumptionPrecoution.setId(1L);
        ConsumptionPrecoutionDTO consumptionPrecoutionDTO = consumptionPrecoutionMapper.toDto(consumptionPrecoution);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumptionPrecoutionMockMvc.perform(post("/api/consumption-precoutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionPrecoutionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionPrecoution in the database
        List<ConsumptionPrecoution> consumptionPrecoutionList = consumptionPrecoutionRepository.findAll();
        assertThat(consumptionPrecoutionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConsumptionPrecoutions() throws Exception {
        // Initialize the database
        consumptionPrecoutionRepository.saveAndFlush(consumptionPrecoution);

        // Get all the consumptionPrecoutionList
        restConsumptionPrecoutionMockMvc.perform(get("/api/consumption-precoutions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumptionPrecoution.getId().intValue())))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION.toString())))
            .andExpect(jsonPath("$.[*].precoution").value(hasItem(DEFAULT_PRECOUTION.toString())));
    }
    
    @Test
    @Transactional
    public void getConsumptionPrecoution() throws Exception {
        // Initialize the database
        consumptionPrecoutionRepository.saveAndFlush(consumptionPrecoution);

        // Get the consumptionPrecoution
        restConsumptionPrecoutionMockMvc.perform(get("/api/consumption-precoutions/{id}", consumptionPrecoution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consumptionPrecoution.getId().intValue()))
            .andExpect(jsonPath("$.conclusion").value(DEFAULT_CONCLUSION.toString()))
            .andExpect(jsonPath("$.precoution").value(DEFAULT_PRECOUTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsumptionPrecoution() throws Exception {
        // Get the consumptionPrecoution
        restConsumptionPrecoutionMockMvc.perform(get("/api/consumption-precoutions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsumptionPrecoution() throws Exception {
        // Initialize the database
        consumptionPrecoutionRepository.saveAndFlush(consumptionPrecoution);

        int databaseSizeBeforeUpdate = consumptionPrecoutionRepository.findAll().size();

        // Update the consumptionPrecoution
        ConsumptionPrecoution updatedConsumptionPrecoution = consumptionPrecoutionRepository.findById(consumptionPrecoution.getId()).get();
        // Disconnect from session so that the updates on updatedConsumptionPrecoution are not directly saved in db
        em.detach(updatedConsumptionPrecoution);
        updatedConsumptionPrecoution
            .conclusion(UPDATED_CONCLUSION)
            .precoution(UPDATED_PRECOUTION);
        ConsumptionPrecoutionDTO consumptionPrecoutionDTO = consumptionPrecoutionMapper.toDto(updatedConsumptionPrecoution);

        restConsumptionPrecoutionMockMvc.perform(put("/api/consumption-precoutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionPrecoutionDTO)))
            .andExpect(status().isOk());

        // Validate the ConsumptionPrecoution in the database
        List<ConsumptionPrecoution> consumptionPrecoutionList = consumptionPrecoutionRepository.findAll();
        assertThat(consumptionPrecoutionList).hasSize(databaseSizeBeforeUpdate);
        ConsumptionPrecoution testConsumptionPrecoution = consumptionPrecoutionList.get(consumptionPrecoutionList.size() - 1);
        assertThat(testConsumptionPrecoution.getConclusion()).isEqualTo(UPDATED_CONCLUSION);
        assertThat(testConsumptionPrecoution.getPrecoution()).isEqualTo(UPDATED_PRECOUTION);
    }

    @Test
    @Transactional
    public void updateNonExistingConsumptionPrecoution() throws Exception {
        int databaseSizeBeforeUpdate = consumptionPrecoutionRepository.findAll().size();

        // Create the ConsumptionPrecoution
        ConsumptionPrecoutionDTO consumptionPrecoutionDTO = consumptionPrecoutionMapper.toDto(consumptionPrecoution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumptionPrecoutionMockMvc.perform(put("/api/consumption-precoutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionPrecoutionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionPrecoution in the database
        List<ConsumptionPrecoution> consumptionPrecoutionList = consumptionPrecoutionRepository.findAll();
        assertThat(consumptionPrecoutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsumptionPrecoution() throws Exception {
        // Initialize the database
        consumptionPrecoutionRepository.saveAndFlush(consumptionPrecoution);

        int databaseSizeBeforeDelete = consumptionPrecoutionRepository.findAll().size();

        // Delete the consumptionPrecoution
        restConsumptionPrecoutionMockMvc.perform(delete("/api/consumption-precoutions/{id}", consumptionPrecoution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConsumptionPrecoution> consumptionPrecoutionList = consumptionPrecoutionRepository.findAll();
        assertThat(consumptionPrecoutionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionPrecoution.class);
        ConsumptionPrecoution consumptionPrecoution1 = new ConsumptionPrecoution();
        consumptionPrecoution1.setId(1L);
        ConsumptionPrecoution consumptionPrecoution2 = new ConsumptionPrecoution();
        consumptionPrecoution2.setId(consumptionPrecoution1.getId());
        assertThat(consumptionPrecoution1).isEqualTo(consumptionPrecoution2);
        consumptionPrecoution2.setId(2L);
        assertThat(consumptionPrecoution1).isNotEqualTo(consumptionPrecoution2);
        consumptionPrecoution1.setId(null);
        assertThat(consumptionPrecoution1).isNotEqualTo(consumptionPrecoution2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionPrecoutionDTO.class);
        ConsumptionPrecoutionDTO consumptionPrecoutionDTO1 = new ConsumptionPrecoutionDTO();
        consumptionPrecoutionDTO1.setId(1L);
        ConsumptionPrecoutionDTO consumptionPrecoutionDTO2 = new ConsumptionPrecoutionDTO();
        assertThat(consumptionPrecoutionDTO1).isNotEqualTo(consumptionPrecoutionDTO2);
        consumptionPrecoutionDTO2.setId(consumptionPrecoutionDTO1.getId());
        assertThat(consumptionPrecoutionDTO1).isEqualTo(consumptionPrecoutionDTO2);
        consumptionPrecoutionDTO2.setId(2L);
        assertThat(consumptionPrecoutionDTO1).isNotEqualTo(consumptionPrecoutionDTO2);
        consumptionPrecoutionDTO1.setId(null);
        assertThat(consumptionPrecoutionDTO1).isNotEqualTo(consumptionPrecoutionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consumptionPrecoutionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consumptionPrecoutionMapper.fromId(null)).isNull();
    }
}
