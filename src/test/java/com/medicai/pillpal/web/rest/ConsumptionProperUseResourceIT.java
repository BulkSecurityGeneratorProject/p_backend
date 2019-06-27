package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.ConsumptionProperUse;
import com.medicai.pillpal.repository.ConsumptionProperUseRepository;
import com.medicai.pillpal.service.ConsumptionProperUseService;
import com.medicai.pillpal.service.dto.ConsumptionProperUseDTO;
import com.medicai.pillpal.service.mapper.ConsumptionProperUseMapper;
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
 * Integration tests for the {@Link ConsumptionProperUseResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class ConsumptionProperUseResourceIT {

    private static final String DEFAULT_CONCLUSION = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION = "BBBBBBBBBB";

    private static final String DEFAULT_PROPER_USE = "AAAAAAAAAA";
    private static final String UPDATED_PROPER_USE = "BBBBBBBBBB";

    @Autowired
    private ConsumptionProperUseRepository consumptionProperUseRepository;

    @Autowired
    private ConsumptionProperUseMapper consumptionProperUseMapper;

    @Autowired
    private ConsumptionProperUseService consumptionProperUseService;

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

    private MockMvc restConsumptionProperUseMockMvc;

    private ConsumptionProperUse consumptionProperUse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsumptionProperUseResource consumptionProperUseResource = new ConsumptionProperUseResource(consumptionProperUseService);
        this.restConsumptionProperUseMockMvc = MockMvcBuilders.standaloneSetup(consumptionProperUseResource)
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
    public static ConsumptionProperUse createEntity(EntityManager em) {
        ConsumptionProperUse consumptionProperUse = new ConsumptionProperUse()
            .conclusion(DEFAULT_CONCLUSION)
            .properUse(DEFAULT_PROPER_USE);
        return consumptionProperUse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsumptionProperUse createUpdatedEntity(EntityManager em) {
        ConsumptionProperUse consumptionProperUse = new ConsumptionProperUse()
            .conclusion(UPDATED_CONCLUSION)
            .properUse(UPDATED_PROPER_USE);
        return consumptionProperUse;
    }

    @BeforeEach
    public void initTest() {
        consumptionProperUse = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsumptionProperUse() throws Exception {
        int databaseSizeBeforeCreate = consumptionProperUseRepository.findAll().size();

        // Create the ConsumptionProperUse
        ConsumptionProperUseDTO consumptionProperUseDTO = consumptionProperUseMapper.toDto(consumptionProperUse);
        restConsumptionProperUseMockMvc.perform(post("/api/consumption-proper-uses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionProperUseDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsumptionProperUse in the database
        List<ConsumptionProperUse> consumptionProperUseList = consumptionProperUseRepository.findAll();
        assertThat(consumptionProperUseList).hasSize(databaseSizeBeforeCreate + 1);
        ConsumptionProperUse testConsumptionProperUse = consumptionProperUseList.get(consumptionProperUseList.size() - 1);
        assertThat(testConsumptionProperUse.getConclusion()).isEqualTo(DEFAULT_CONCLUSION);
        assertThat(testConsumptionProperUse.getProperUse()).isEqualTo(DEFAULT_PROPER_USE);
    }

    @Test
    @Transactional
    public void createConsumptionProperUseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consumptionProperUseRepository.findAll().size();

        // Create the ConsumptionProperUse with an existing ID
        consumptionProperUse.setId(1L);
        ConsumptionProperUseDTO consumptionProperUseDTO = consumptionProperUseMapper.toDto(consumptionProperUse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumptionProperUseMockMvc.perform(post("/api/consumption-proper-uses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionProperUseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionProperUse in the database
        List<ConsumptionProperUse> consumptionProperUseList = consumptionProperUseRepository.findAll();
        assertThat(consumptionProperUseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConsumptionProperUses() throws Exception {
        // Initialize the database
        consumptionProperUseRepository.saveAndFlush(consumptionProperUse);

        // Get all the consumptionProperUseList
        restConsumptionProperUseMockMvc.perform(get("/api/consumption-proper-uses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumptionProperUse.getId().intValue())))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION.toString())))
            .andExpect(jsonPath("$.[*].properUse").value(hasItem(DEFAULT_PROPER_USE.toString())));
    }
    
    @Test
    @Transactional
    public void getConsumptionProperUse() throws Exception {
        // Initialize the database
        consumptionProperUseRepository.saveAndFlush(consumptionProperUse);

        // Get the consumptionProperUse
        restConsumptionProperUseMockMvc.perform(get("/api/consumption-proper-uses/{id}", consumptionProperUse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consumptionProperUse.getId().intValue()))
            .andExpect(jsonPath("$.conclusion").value(DEFAULT_CONCLUSION.toString()))
            .andExpect(jsonPath("$.properUse").value(DEFAULT_PROPER_USE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsumptionProperUse() throws Exception {
        // Get the consumptionProperUse
        restConsumptionProperUseMockMvc.perform(get("/api/consumption-proper-uses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsumptionProperUse() throws Exception {
        // Initialize the database
        consumptionProperUseRepository.saveAndFlush(consumptionProperUse);

        int databaseSizeBeforeUpdate = consumptionProperUseRepository.findAll().size();

        // Update the consumptionProperUse
        ConsumptionProperUse updatedConsumptionProperUse = consumptionProperUseRepository.findById(consumptionProperUse.getId()).get();
        // Disconnect from session so that the updates on updatedConsumptionProperUse are not directly saved in db
        em.detach(updatedConsumptionProperUse);
        updatedConsumptionProperUse
            .conclusion(UPDATED_CONCLUSION)
            .properUse(UPDATED_PROPER_USE);
        ConsumptionProperUseDTO consumptionProperUseDTO = consumptionProperUseMapper.toDto(updatedConsumptionProperUse);

        restConsumptionProperUseMockMvc.perform(put("/api/consumption-proper-uses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionProperUseDTO)))
            .andExpect(status().isOk());

        // Validate the ConsumptionProperUse in the database
        List<ConsumptionProperUse> consumptionProperUseList = consumptionProperUseRepository.findAll();
        assertThat(consumptionProperUseList).hasSize(databaseSizeBeforeUpdate);
        ConsumptionProperUse testConsumptionProperUse = consumptionProperUseList.get(consumptionProperUseList.size() - 1);
        assertThat(testConsumptionProperUse.getConclusion()).isEqualTo(UPDATED_CONCLUSION);
        assertThat(testConsumptionProperUse.getProperUse()).isEqualTo(UPDATED_PROPER_USE);
    }

    @Test
    @Transactional
    public void updateNonExistingConsumptionProperUse() throws Exception {
        int databaseSizeBeforeUpdate = consumptionProperUseRepository.findAll().size();

        // Create the ConsumptionProperUse
        ConsumptionProperUseDTO consumptionProperUseDTO = consumptionProperUseMapper.toDto(consumptionProperUse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumptionProperUseMockMvc.perform(put("/api/consumption-proper-uses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionProperUseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionProperUse in the database
        List<ConsumptionProperUse> consumptionProperUseList = consumptionProperUseRepository.findAll();
        assertThat(consumptionProperUseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsumptionProperUse() throws Exception {
        // Initialize the database
        consumptionProperUseRepository.saveAndFlush(consumptionProperUse);

        int databaseSizeBeforeDelete = consumptionProperUseRepository.findAll().size();

        // Delete the consumptionProperUse
        restConsumptionProperUseMockMvc.perform(delete("/api/consumption-proper-uses/{id}", consumptionProperUse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConsumptionProperUse> consumptionProperUseList = consumptionProperUseRepository.findAll();
        assertThat(consumptionProperUseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionProperUse.class);
        ConsumptionProperUse consumptionProperUse1 = new ConsumptionProperUse();
        consumptionProperUse1.setId(1L);
        ConsumptionProperUse consumptionProperUse2 = new ConsumptionProperUse();
        consumptionProperUse2.setId(consumptionProperUse1.getId());
        assertThat(consumptionProperUse1).isEqualTo(consumptionProperUse2);
        consumptionProperUse2.setId(2L);
        assertThat(consumptionProperUse1).isNotEqualTo(consumptionProperUse2);
        consumptionProperUse1.setId(null);
        assertThat(consumptionProperUse1).isNotEqualTo(consumptionProperUse2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionProperUseDTO.class);
        ConsumptionProperUseDTO consumptionProperUseDTO1 = new ConsumptionProperUseDTO();
        consumptionProperUseDTO1.setId(1L);
        ConsumptionProperUseDTO consumptionProperUseDTO2 = new ConsumptionProperUseDTO();
        assertThat(consumptionProperUseDTO1).isNotEqualTo(consumptionProperUseDTO2);
        consumptionProperUseDTO2.setId(consumptionProperUseDTO1.getId());
        assertThat(consumptionProperUseDTO1).isEqualTo(consumptionProperUseDTO2);
        consumptionProperUseDTO2.setId(2L);
        assertThat(consumptionProperUseDTO1).isNotEqualTo(consumptionProperUseDTO2);
        consumptionProperUseDTO1.setId(null);
        assertThat(consumptionProperUseDTO1).isNotEqualTo(consumptionProperUseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consumptionProperUseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consumptionProperUseMapper.fromId(null)).isNull();
    }
}
