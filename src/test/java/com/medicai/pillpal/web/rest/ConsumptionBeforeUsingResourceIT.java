package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.ConsumptionBeforeUsing;
import com.medicai.pillpal.repository.ConsumptionBeforeUsingRepository;
import com.medicai.pillpal.service.ConsumptionBeforeUsingService;
import com.medicai.pillpal.service.dto.ConsumptionBeforeUsingDTO;
import com.medicai.pillpal.service.mapper.ConsumptionBeforeUsingMapper;
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
 * Integration tests for the {@Link ConsumptionBeforeUsingResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class ConsumptionBeforeUsingResourceIT {

    private static final String DEFAULT_CONCLUSION = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION = "BBBBBBBBBB";

    private static final String DEFAULT_BEFORE_USING = "AAAAAAAAAA";
    private static final String UPDATED_BEFORE_USING = "BBBBBBBBBB";

    @Autowired
    private ConsumptionBeforeUsingRepository consumptionBeforeUsingRepository;

    @Autowired
    private ConsumptionBeforeUsingMapper consumptionBeforeUsingMapper;

    @Autowired
    private ConsumptionBeforeUsingService consumptionBeforeUsingService;

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

    private MockMvc restConsumptionBeforeUsingMockMvc;

    private ConsumptionBeforeUsing consumptionBeforeUsing;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsumptionBeforeUsingResource consumptionBeforeUsingResource = new ConsumptionBeforeUsingResource(consumptionBeforeUsingService);
        this.restConsumptionBeforeUsingMockMvc = MockMvcBuilders.standaloneSetup(consumptionBeforeUsingResource)
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
    public static ConsumptionBeforeUsing createEntity(EntityManager em) {
        ConsumptionBeforeUsing consumptionBeforeUsing = new ConsumptionBeforeUsing()
            .conclusion(DEFAULT_CONCLUSION)
            .beforeUsing(DEFAULT_BEFORE_USING);
        return consumptionBeforeUsing;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsumptionBeforeUsing createUpdatedEntity(EntityManager em) {
        ConsumptionBeforeUsing consumptionBeforeUsing = new ConsumptionBeforeUsing()
            .conclusion(UPDATED_CONCLUSION)
            .beforeUsing(UPDATED_BEFORE_USING);
        return consumptionBeforeUsing;
    }

    @BeforeEach
    public void initTest() {
        consumptionBeforeUsing = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsumptionBeforeUsing() throws Exception {
        int databaseSizeBeforeCreate = consumptionBeforeUsingRepository.findAll().size();

        // Create the ConsumptionBeforeUsing
        ConsumptionBeforeUsingDTO consumptionBeforeUsingDTO = consumptionBeforeUsingMapper.toDto(consumptionBeforeUsing);
        restConsumptionBeforeUsingMockMvc.perform(post("/api/consumption-before-usings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionBeforeUsingDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsumptionBeforeUsing in the database
        List<ConsumptionBeforeUsing> consumptionBeforeUsingList = consumptionBeforeUsingRepository.findAll();
        assertThat(consumptionBeforeUsingList).hasSize(databaseSizeBeforeCreate + 1);
        ConsumptionBeforeUsing testConsumptionBeforeUsing = consumptionBeforeUsingList.get(consumptionBeforeUsingList.size() - 1);
        assertThat(testConsumptionBeforeUsing.getConclusion()).isEqualTo(DEFAULT_CONCLUSION);
        assertThat(testConsumptionBeforeUsing.getBeforeUsing()).isEqualTo(DEFAULT_BEFORE_USING);
    }

    @Test
    @Transactional
    public void createConsumptionBeforeUsingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consumptionBeforeUsingRepository.findAll().size();

        // Create the ConsumptionBeforeUsing with an existing ID
        consumptionBeforeUsing.setId(1L);
        ConsumptionBeforeUsingDTO consumptionBeforeUsingDTO = consumptionBeforeUsingMapper.toDto(consumptionBeforeUsing);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumptionBeforeUsingMockMvc.perform(post("/api/consumption-before-usings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionBeforeUsingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionBeforeUsing in the database
        List<ConsumptionBeforeUsing> consumptionBeforeUsingList = consumptionBeforeUsingRepository.findAll();
        assertThat(consumptionBeforeUsingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConsumptionBeforeUsings() throws Exception {
        // Initialize the database
        consumptionBeforeUsingRepository.saveAndFlush(consumptionBeforeUsing);

        // Get all the consumptionBeforeUsingList
        restConsumptionBeforeUsingMockMvc.perform(get("/api/consumption-before-usings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumptionBeforeUsing.getId().intValue())))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION.toString())))
            .andExpect(jsonPath("$.[*].beforeUsing").value(hasItem(DEFAULT_BEFORE_USING.toString())));
    }

    @Test
    @Transactional
    public void getConsumptionBeforeUsing() throws Exception {
        // Initialize the database
        consumptionBeforeUsingRepository.saveAndFlush(consumptionBeforeUsing);

        // Get the consumptionBeforeUsing
        restConsumptionBeforeUsingMockMvc.perform(get("/api/consumption-before-usings/{id}", consumptionBeforeUsing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consumptionBeforeUsing.getId().intValue()))
            .andExpect(jsonPath("$.conclusion").value(DEFAULT_CONCLUSION.toString()))
            .andExpect(jsonPath("$.beforeUsing").value(DEFAULT_BEFORE_USING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsumptionBeforeUsing() throws Exception {
        // Get the consumptionBeforeUsing
        restConsumptionBeforeUsingMockMvc.perform(get("/api/consumption-before-usings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsumptionBeforeUsing() throws Exception {
        // Initialize the database
        consumptionBeforeUsingRepository.saveAndFlush(consumptionBeforeUsing);

        int databaseSizeBeforeUpdate = consumptionBeforeUsingRepository.findAll().size();

        // Update the consumptionBeforeUsing
        ConsumptionBeforeUsing updatedConsumptionBeforeUsing = consumptionBeforeUsingRepository.findById(consumptionBeforeUsing.getId()).get();
        // Disconnect from session so that the updates on updatedConsumptionBeforeUsing are not directly saved in db
        em.detach(updatedConsumptionBeforeUsing);
        updatedConsumptionBeforeUsing
            .conclusion(UPDATED_CONCLUSION)
            .beforeUsing(UPDATED_BEFORE_USING);
        ConsumptionBeforeUsingDTO consumptionBeforeUsingDTO = consumptionBeforeUsingMapper.toDto(updatedConsumptionBeforeUsing);

        restConsumptionBeforeUsingMockMvc.perform(put("/api/consumption-before-usings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionBeforeUsingDTO)))
            .andExpect(status().isOk());

        // Validate the ConsumptionBeforeUsing in the database
        List<ConsumptionBeforeUsing> consumptionBeforeUsingList = consumptionBeforeUsingRepository.findAll();
        assertThat(consumptionBeforeUsingList).hasSize(databaseSizeBeforeUpdate);
        ConsumptionBeforeUsing testConsumptionBeforeUsing = consumptionBeforeUsingList.get(consumptionBeforeUsingList.size() - 1);
        assertThat(testConsumptionBeforeUsing.getConclusion()).isEqualTo(UPDATED_CONCLUSION);
        assertThat(testConsumptionBeforeUsing.getBeforeUsing()).isEqualTo(UPDATED_BEFORE_USING);
    }

    @Test
    @Transactional
    public void updateNonExistingConsumptionBeforeUsing() throws Exception {
        int databaseSizeBeforeUpdate = consumptionBeforeUsingRepository.findAll().size();

        // Create the ConsumptionBeforeUsing
        ConsumptionBeforeUsingDTO consumptionBeforeUsingDTO = consumptionBeforeUsingMapper.toDto(consumptionBeforeUsing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumptionBeforeUsingMockMvc.perform(put("/api/consumption-before-usings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumptionBeforeUsingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionBeforeUsing in the database
        List<ConsumptionBeforeUsing> consumptionBeforeUsingList = consumptionBeforeUsingRepository.findAll();
        assertThat(consumptionBeforeUsingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsumptionBeforeUsing() throws Exception {
        // Initialize the database
        consumptionBeforeUsingRepository.saveAndFlush(consumptionBeforeUsing);

        int databaseSizeBeforeDelete = consumptionBeforeUsingRepository.findAll().size();

        // Delete the consumptionBeforeUsing
        restConsumptionBeforeUsingMockMvc.perform(delete("/api/consumption-before-usings/{id}", consumptionBeforeUsing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConsumptionBeforeUsing> consumptionBeforeUsingList = consumptionBeforeUsingRepository.findAll();
        assertThat(consumptionBeforeUsingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionBeforeUsing.class);
        ConsumptionBeforeUsing consumptionBeforeUsing1 = new ConsumptionBeforeUsing();
        consumptionBeforeUsing1.setId(1L);
        ConsumptionBeforeUsing consumptionBeforeUsing2 = new ConsumptionBeforeUsing();
        consumptionBeforeUsing2.setId(consumptionBeforeUsing1.getId());
        assertThat(consumptionBeforeUsing1).isEqualTo(consumptionBeforeUsing2);
        consumptionBeforeUsing2.setId(2L);
        assertThat(consumptionBeforeUsing1).isNotEqualTo(consumptionBeforeUsing2);
        consumptionBeforeUsing1.setId(null);
        assertThat(consumptionBeforeUsing1).isNotEqualTo(consumptionBeforeUsing2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionBeforeUsingDTO.class);
        ConsumptionBeforeUsingDTO consumptionBeforeUsingDTO1 = new ConsumptionBeforeUsingDTO();
        consumptionBeforeUsingDTO1.setId(1L);
        ConsumptionBeforeUsingDTO consumptionBeforeUsingDTO2 = new ConsumptionBeforeUsingDTO();
        assertThat(consumptionBeforeUsingDTO1).isNotEqualTo(consumptionBeforeUsingDTO2);
        consumptionBeforeUsingDTO2.setId(consumptionBeforeUsingDTO1.getId());
        assertThat(consumptionBeforeUsingDTO1).isEqualTo(consumptionBeforeUsingDTO2);
        consumptionBeforeUsingDTO2.setId(2L);
        assertThat(consumptionBeforeUsingDTO1).isNotEqualTo(consumptionBeforeUsingDTO2);
        consumptionBeforeUsingDTO1.setId(null);
        assertThat(consumptionBeforeUsingDTO1).isNotEqualTo(consumptionBeforeUsingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consumptionBeforeUsingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consumptionBeforeUsingMapper.fromId(null)).isNull();
    }
}
