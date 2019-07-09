package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.ConsumptionMissedDose;
import com.medicai.pillpal.repository.ConsumptionMissedDoseRepository;
import com.medicai.pillpal.service.ConsumptionMissedDoseService;
import com.medicai.pillpal.service.dto.ConsumptionMissedDoseDTO;
import com.medicai.pillpal.service.mapper.ConsumptionMissedDoseMapper;
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
 * Integration tests for the {@Link ConsumptionMissedDoseResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class ConsumptionMissedDoseResourceIT {

    private static final String DEFAULT_CONCLUSION = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION = "BBBBBBBBBB";

    private static final String DEFAULT_MISSED_DOSE = "AAAAAAAAAA";
    private static final String UPDATED_MISSED_DOSE = "BBBBBBBBBB";

    @Autowired
    private ConsumptionMissedDoseRepository consumptionMissedDoseRepository;

    @Autowired
    private ConsumptionMissedDoseMapper consumptionMissedDoseMapper;

    @Autowired
    private ConsumptionMissedDoseService consumptionMissedDoseService;

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

    private MockMvc restConsumptionMissedDoseMockMvc;

    private ConsumptionMissedDose consumptionMissedDose;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsumptionMissedDoseResource consumptionMissedDoseResource = new ConsumptionMissedDoseResource(consumptionMissedDoseService);
        this.restConsumptionMissedDoseMockMvc = MockMvcBuilders.standaloneSetup(consumptionMissedDoseResource)
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
    public static ConsumptionMissedDose createEntity(EntityManager em) {
        ConsumptionMissedDose consumptionMissedDose = new ConsumptionMissedDose()
            .conclusion(DEFAULT_CONCLUSION)
            .missedDose(DEFAULT_MISSED_DOSE);
        return consumptionMissedDose;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsumptionMissedDose createUpdatedEntity(EntityManager em) {
        ConsumptionMissedDose consumptionMissedDose = new ConsumptionMissedDose()
            .conclusion(UPDATED_CONCLUSION)
            .missedDose(UPDATED_MISSED_DOSE);
        return consumptionMissedDose;
    }

    @BeforeEach
    public void initTest() {
        consumptionMissedDose = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsumptionMissedDose() throws Exception {
        int databaseSizeBeforeCreate = consumptionMissedDoseRepository.findAll().size();

        // Create the ConsumptionMissedDose
        ConsumptionMissedDoseDTO consumptionMissedDoseDTO = consumptionMissedDoseMapper.toDto(consumptionMissedDose);
        restConsumptionMissedDoseMockMvc.perform(post("/api/consumption-missed-doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionMissedDoseDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsumptionMissedDose in the database
        List<ConsumptionMissedDose> consumptionMissedDoseList = consumptionMissedDoseRepository.findAll();
        assertThat(consumptionMissedDoseList).hasSize(databaseSizeBeforeCreate + 1);
        ConsumptionMissedDose testConsumptionMissedDose = consumptionMissedDoseList.get(consumptionMissedDoseList.size() - 1);
        assertThat(testConsumptionMissedDose.getConclusion()).isEqualTo(DEFAULT_CONCLUSION);
        assertThat(testConsumptionMissedDose.getMissedDose()).isEqualTo(DEFAULT_MISSED_DOSE);
    }

    @Test
    @Transactional
    public void createConsumptionMissedDoseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consumptionMissedDoseRepository.findAll().size();

        // Create the ConsumptionMissedDose with an existing ID
        consumptionMissedDose.setId(1L);
        ConsumptionMissedDoseDTO consumptionMissedDoseDTO = consumptionMissedDoseMapper.toDto(consumptionMissedDose);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumptionMissedDoseMockMvc.perform(post("/api/consumption-missed-doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionMissedDoseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionMissedDose in the database
        List<ConsumptionMissedDose> consumptionMissedDoseList = consumptionMissedDoseRepository.findAll();
        assertThat(consumptionMissedDoseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConsumptionMissedDoses() throws Exception {
        // Initialize the database
        consumptionMissedDoseRepository.saveAndFlush(consumptionMissedDose);

        // Get all the consumptionMissedDoseList
        restConsumptionMissedDoseMockMvc.perform(get("/api/consumption-missed-doses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumptionMissedDose.getId().intValue())))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION.toString())))
            .andExpect(jsonPath("$.[*].missedDose").value(hasItem(DEFAULT_MISSED_DOSE.toString())));
    }

    @Test
    @Transactional
    public void getConsumptionMissedDose() throws Exception {
        // Initialize the database
        consumptionMissedDoseRepository.saveAndFlush(consumptionMissedDose);

        // Get the consumptionMissedDose
        restConsumptionMissedDoseMockMvc.perform(get("/api/consumption-missed-doses/{id}", consumptionMissedDose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consumptionMissedDose.getId().intValue()))
            .andExpect(jsonPath("$.conclusion").value(DEFAULT_CONCLUSION.toString()))
            .andExpect(jsonPath("$.missedDose").value(DEFAULT_MISSED_DOSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsumptionMissedDose() throws Exception {
        // Get the consumptionMissedDose
        restConsumptionMissedDoseMockMvc.perform(get("/api/consumption-missed-doses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsumptionMissedDose() throws Exception {
        // Initialize the database
        consumptionMissedDoseRepository.saveAndFlush(consumptionMissedDose);

        int databaseSizeBeforeUpdate = consumptionMissedDoseRepository.findAll().size();

        // Update the consumptionMissedDose
        ConsumptionMissedDose updatedConsumptionMissedDose = consumptionMissedDoseRepository.findById(consumptionMissedDose.getId()).get();
        // Disconnect from session so that the updates on updatedConsumptionMissedDose are not directly saved in db
        em.detach(updatedConsumptionMissedDose);
        updatedConsumptionMissedDose
            .conclusion(UPDATED_CONCLUSION)
            .missedDose(UPDATED_MISSED_DOSE);
        ConsumptionMissedDoseDTO consumptionMissedDoseDTO = consumptionMissedDoseMapper.toDto(updatedConsumptionMissedDose);

        restConsumptionMissedDoseMockMvc.perform(put("/api/consumption-missed-doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionMissedDoseDTO)))
            .andExpect(status().isOk());

        // Validate the ConsumptionMissedDose in the database
        List<ConsumptionMissedDose> consumptionMissedDoseList = consumptionMissedDoseRepository.findAll();
        assertThat(consumptionMissedDoseList).hasSize(databaseSizeBeforeUpdate);
        ConsumptionMissedDose testConsumptionMissedDose = consumptionMissedDoseList.get(consumptionMissedDoseList.size() - 1);
        assertThat(testConsumptionMissedDose.getConclusion()).isEqualTo(UPDATED_CONCLUSION);
        assertThat(testConsumptionMissedDose.getMissedDose()).isEqualTo(UPDATED_MISSED_DOSE);
    }

    @Test
    @Transactional
    public void updateNonExistingConsumptionMissedDose() throws Exception {
        int databaseSizeBeforeUpdate = consumptionMissedDoseRepository.findAll().size();

        // Create the ConsumptionMissedDose
        ConsumptionMissedDoseDTO consumptionMissedDoseDTO = consumptionMissedDoseMapper.toDto(consumptionMissedDose);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumptionMissedDoseMockMvc.perform(put("/api/consumption-missed-doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionMissedDoseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionMissedDose in the database
        List<ConsumptionMissedDose> consumptionMissedDoseList = consumptionMissedDoseRepository.findAll();
        assertThat(consumptionMissedDoseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsumptionMissedDose() throws Exception {
        // Initialize the database
        consumptionMissedDoseRepository.saveAndFlush(consumptionMissedDose);

        int databaseSizeBeforeDelete = consumptionMissedDoseRepository.findAll().size();

        // Delete the consumptionMissedDose
        restConsumptionMissedDoseMockMvc.perform(delete("/api/consumption-missed-doses/{id}", consumptionMissedDose.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConsumptionMissedDose> consumptionMissedDoseList = consumptionMissedDoseRepository.findAll();
        assertThat(consumptionMissedDoseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionMissedDose.class);
        ConsumptionMissedDose consumptionMissedDose1 = new ConsumptionMissedDose();
        consumptionMissedDose1.setId(1L);
        ConsumptionMissedDose consumptionMissedDose2 = new ConsumptionMissedDose();
        consumptionMissedDose2.setId(consumptionMissedDose1.getId());
        assertThat(consumptionMissedDose1).isEqualTo(consumptionMissedDose2);
        consumptionMissedDose2.setId(2L);
        assertThat(consumptionMissedDose1).isNotEqualTo(consumptionMissedDose2);
        consumptionMissedDose1.setId(null);
        assertThat(consumptionMissedDose1).isNotEqualTo(consumptionMissedDose2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionMissedDoseDTO.class);
        ConsumptionMissedDoseDTO consumptionMissedDoseDTO1 = new ConsumptionMissedDoseDTO();
        consumptionMissedDoseDTO1.setId(1L);
        ConsumptionMissedDoseDTO consumptionMissedDoseDTO2 = new ConsumptionMissedDoseDTO();
        assertThat(consumptionMissedDoseDTO1).isNotEqualTo(consumptionMissedDoseDTO2);
        consumptionMissedDoseDTO2.setId(consumptionMissedDoseDTO1.getId());
        assertThat(consumptionMissedDoseDTO1).isEqualTo(consumptionMissedDoseDTO2);
        consumptionMissedDoseDTO2.setId(2L);
        assertThat(consumptionMissedDoseDTO1).isNotEqualTo(consumptionMissedDoseDTO2);
        consumptionMissedDoseDTO1.setId(null);
        assertThat(consumptionMissedDoseDTO1).isNotEqualTo(consumptionMissedDoseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consumptionMissedDoseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consumptionMissedDoseMapper.fromId(null)).isNull();
    }
}
