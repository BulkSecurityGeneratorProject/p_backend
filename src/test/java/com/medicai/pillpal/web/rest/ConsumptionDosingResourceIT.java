package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.ConsumptionDosing;
import com.medicai.pillpal.repository.ConsumptionDosingRepository;
import com.medicai.pillpal.service.ConsumptionDosingService;
import com.medicai.pillpal.service.dto.ConsumptionDosingDTO;
import com.medicai.pillpal.service.mapper.ConsumptionDosingMapper;
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
 * Integration tests for the {@Link ConsumptionDosingResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class ConsumptionDosingResourceIT {

    private static final String DEFAULT_CONCLUSION = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION = "BBBBBBBBBB";

    private static final String DEFAULT_DOSING = "AAAAAAAAAA";
    private static final String UPDATED_DOSING = "BBBBBBBBBB";

    @Autowired
    private ConsumptionDosingRepository consumptionDosingRepository;

    @Autowired
    private ConsumptionDosingMapper consumptionDosingMapper;

    @Autowired
    private ConsumptionDosingService consumptionDosingService;

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

    private MockMvc restConsumptionDosingMockMvc;

    private ConsumptionDosing consumptionDosing;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsumptionDosingResource consumptionDosingResource = new ConsumptionDosingResource(consumptionDosingService);
        this.restConsumptionDosingMockMvc = MockMvcBuilders.standaloneSetup(consumptionDosingResource)
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
    public static ConsumptionDosing createEntity(EntityManager em) {
        ConsumptionDosing consumptionDosing = new ConsumptionDosing()
            .conclusion(DEFAULT_CONCLUSION)
            .dosing(DEFAULT_DOSING);
        return consumptionDosing;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsumptionDosing createUpdatedEntity(EntityManager em) {
        ConsumptionDosing consumptionDosing = new ConsumptionDosing()
            .conclusion(UPDATED_CONCLUSION)
            .dosing(UPDATED_DOSING);
        return consumptionDosing;
    }

    @BeforeEach
    public void initTest() {
        consumptionDosing = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsumptionDosing() throws Exception {
        int databaseSizeBeforeCreate = consumptionDosingRepository.findAll().size();

        // Create the ConsumptionDosing
        ConsumptionDosingDTO consumptionDosingDTO = consumptionDosingMapper.toDto(consumptionDosing);
        restConsumptionDosingMockMvc.perform(post("/api/consumption-dosings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionDosingDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsumptionDosing in the database
        List<ConsumptionDosing> consumptionDosingList = consumptionDosingRepository.findAll();
        assertThat(consumptionDosingList).hasSize(databaseSizeBeforeCreate + 1);
        ConsumptionDosing testConsumptionDosing = consumptionDosingList.get(consumptionDosingList.size() - 1);
        assertThat(testConsumptionDosing.getConclusion()).isEqualTo(DEFAULT_CONCLUSION);
        assertThat(testConsumptionDosing.getDosing()).isEqualTo(DEFAULT_DOSING);
    }

    @Test
    @Transactional
    public void createConsumptionDosingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consumptionDosingRepository.findAll().size();

        // Create the ConsumptionDosing with an existing ID
        consumptionDosing.setId(1L);
        ConsumptionDosingDTO consumptionDosingDTO = consumptionDosingMapper.toDto(consumptionDosing);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumptionDosingMockMvc.perform(post("/api/consumption-dosings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionDosingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionDosing in the database
        List<ConsumptionDosing> consumptionDosingList = consumptionDosingRepository.findAll();
        assertThat(consumptionDosingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConsumptionDosings() throws Exception {
        // Initialize the database
        consumptionDosingRepository.saveAndFlush(consumptionDosing);

        // Get all the consumptionDosingList
        restConsumptionDosingMockMvc.perform(get("/api/consumption-dosings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumptionDosing.getId().intValue())))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION.toString())))
            .andExpect(jsonPath("$.[*].dosing").value(hasItem(DEFAULT_DOSING.toString())));
    }
    
    @Test
    @Transactional
    public void getConsumptionDosing() throws Exception {
        // Initialize the database
        consumptionDosingRepository.saveAndFlush(consumptionDosing);

        // Get the consumptionDosing
        restConsumptionDosingMockMvc.perform(get("/api/consumption-dosings/{id}", consumptionDosing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consumptionDosing.getId().intValue()))
            .andExpect(jsonPath("$.conclusion").value(DEFAULT_CONCLUSION.toString()))
            .andExpect(jsonPath("$.dosing").value(DEFAULT_DOSING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsumptionDosing() throws Exception {
        // Get the consumptionDosing
        restConsumptionDosingMockMvc.perform(get("/api/consumption-dosings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsumptionDosing() throws Exception {
        // Initialize the database
        consumptionDosingRepository.saveAndFlush(consumptionDosing);

        int databaseSizeBeforeUpdate = consumptionDosingRepository.findAll().size();

        // Update the consumptionDosing
        ConsumptionDosing updatedConsumptionDosing = consumptionDosingRepository.findById(consumptionDosing.getId()).get();
        // Disconnect from session so that the updates on updatedConsumptionDosing are not directly saved in db
        em.detach(updatedConsumptionDosing);
        updatedConsumptionDosing
            .conclusion(UPDATED_CONCLUSION)
            .dosing(UPDATED_DOSING);
        ConsumptionDosingDTO consumptionDosingDTO = consumptionDosingMapper.toDto(updatedConsumptionDosing);

        restConsumptionDosingMockMvc.perform(put("/api/consumption-dosings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionDosingDTO)))
            .andExpect(status().isOk());

        // Validate the ConsumptionDosing in the database
        List<ConsumptionDosing> consumptionDosingList = consumptionDosingRepository.findAll();
        assertThat(consumptionDosingList).hasSize(databaseSizeBeforeUpdate);
        ConsumptionDosing testConsumptionDosing = consumptionDosingList.get(consumptionDosingList.size() - 1);
        assertThat(testConsumptionDosing.getConclusion()).isEqualTo(UPDATED_CONCLUSION);
        assertThat(testConsumptionDosing.getDosing()).isEqualTo(UPDATED_DOSING);
    }

    @Test
    @Transactional
    public void updateNonExistingConsumptionDosing() throws Exception {
        int databaseSizeBeforeUpdate = consumptionDosingRepository.findAll().size();

        // Create the ConsumptionDosing
        ConsumptionDosingDTO consumptionDosingDTO = consumptionDosingMapper.toDto(consumptionDosing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumptionDosingMockMvc.perform(put("/api/consumption-dosings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionDosingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionDosing in the database
        List<ConsumptionDosing> consumptionDosingList = consumptionDosingRepository.findAll();
        assertThat(consumptionDosingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsumptionDosing() throws Exception {
        // Initialize the database
        consumptionDosingRepository.saveAndFlush(consumptionDosing);

        int databaseSizeBeforeDelete = consumptionDosingRepository.findAll().size();

        // Delete the consumptionDosing
        restConsumptionDosingMockMvc.perform(delete("/api/consumption-dosings/{id}", consumptionDosing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConsumptionDosing> consumptionDosingList = consumptionDosingRepository.findAll();
        assertThat(consumptionDosingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionDosing.class);
        ConsumptionDosing consumptionDosing1 = new ConsumptionDosing();
        consumptionDosing1.setId(1L);
        ConsumptionDosing consumptionDosing2 = new ConsumptionDosing();
        consumptionDosing2.setId(consumptionDosing1.getId());
        assertThat(consumptionDosing1).isEqualTo(consumptionDosing2);
        consumptionDosing2.setId(2L);
        assertThat(consumptionDosing1).isNotEqualTo(consumptionDosing2);
        consumptionDosing1.setId(null);
        assertThat(consumptionDosing1).isNotEqualTo(consumptionDosing2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionDosingDTO.class);
        ConsumptionDosingDTO consumptionDosingDTO1 = new ConsumptionDosingDTO();
        consumptionDosingDTO1.setId(1L);
        ConsumptionDosingDTO consumptionDosingDTO2 = new ConsumptionDosingDTO();
        assertThat(consumptionDosingDTO1).isNotEqualTo(consumptionDosingDTO2);
        consumptionDosingDTO2.setId(consumptionDosingDTO1.getId());
        assertThat(consumptionDosingDTO1).isEqualTo(consumptionDosingDTO2);
        consumptionDosingDTO2.setId(2L);
        assertThat(consumptionDosingDTO1).isNotEqualTo(consumptionDosingDTO2);
        consumptionDosingDTO1.setId(null);
        assertThat(consumptionDosingDTO1).isNotEqualTo(consumptionDosingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consumptionDosingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consumptionDosingMapper.fromId(null)).isNull();
    }
}
