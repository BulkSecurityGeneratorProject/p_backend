package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.BreastFeeding;
import com.medicai.pillpal.repository.BreastFeedingRepository;
import com.medicai.pillpal.service.BreastFeedingService;
import com.medicai.pillpal.service.dto.BreastFeedingDTO;
import com.medicai.pillpal.service.mapper.BreastFeedingMapper;
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
 * Integration tests for the {@Link BreastFeedingResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class BreastFeedingResourceIT {

    private static final String DEFAULT_BREAST_FEEDING = "AAAAAAAAAA";
    private static final String UPDATED_BREAST_FEEDING = "BBBBBBBBBB";

    @Autowired
    private BreastFeedingRepository breastFeedingRepository;

    @Autowired
    private BreastFeedingMapper breastFeedingMapper;

    @Autowired
    private BreastFeedingService breastFeedingService;

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

    private MockMvc restBreastFeedingMockMvc;

    private BreastFeeding breastFeeding;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BreastFeedingResource breastFeedingResource = new BreastFeedingResource(breastFeedingService);
        this.restBreastFeedingMockMvc = MockMvcBuilders.standaloneSetup(breastFeedingResource)
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
    public static BreastFeeding createEntity(EntityManager em) {
        BreastFeeding breastFeeding = new BreastFeeding()
            .breastFeeding(DEFAULT_BREAST_FEEDING);
        return breastFeeding;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BreastFeeding createUpdatedEntity(EntityManager em) {
        BreastFeeding breastFeeding = new BreastFeeding()
            .breastFeeding(UPDATED_BREAST_FEEDING);
        return breastFeeding;
    }

    @BeforeEach
    public void initTest() {
        breastFeeding = createEntity(em);
    }

    @Test
    @Transactional
    public void createBreastFeeding() throws Exception {
        int databaseSizeBeforeCreate = breastFeedingRepository.findAll().size();

        // Create the BreastFeeding
        BreastFeedingDTO breastFeedingDTO = breastFeedingMapper.toDto(breastFeeding);
        restBreastFeedingMockMvc.perform(post("/api/breast-feedings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breastFeedingDTO)))
            .andExpect(status().isCreated());

        // Validate the BreastFeeding in the database
        List<BreastFeeding> breastFeedingList = breastFeedingRepository.findAll();
        assertThat(breastFeedingList).hasSize(databaseSizeBeforeCreate + 1);
        BreastFeeding testBreastFeeding = breastFeedingList.get(breastFeedingList.size() - 1);
        assertThat(testBreastFeeding.getBreastFeeding()).isEqualTo(DEFAULT_BREAST_FEEDING);
    }

    @Test
    @Transactional
    public void createBreastFeedingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = breastFeedingRepository.findAll().size();

        // Create the BreastFeeding with an existing ID
        breastFeeding.setId(1L);
        BreastFeedingDTO breastFeedingDTO = breastFeedingMapper.toDto(breastFeeding);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBreastFeedingMockMvc.perform(post("/api/breast-feedings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breastFeedingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BreastFeeding in the database
        List<BreastFeeding> breastFeedingList = breastFeedingRepository.findAll();
        assertThat(breastFeedingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBreastFeedings() throws Exception {
        // Initialize the database
        breastFeedingRepository.saveAndFlush(breastFeeding);

        // Get all the breastFeedingList
        restBreastFeedingMockMvc.perform(get("/api/breast-feedings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(breastFeeding.getId().intValue())))
            .andExpect(jsonPath("$.[*].breastFeeding").value(hasItem(DEFAULT_BREAST_FEEDING.toString())));
    }
    
    @Test
    @Transactional
    public void getBreastFeeding() throws Exception {
        // Initialize the database
        breastFeedingRepository.saveAndFlush(breastFeeding);

        // Get the breastFeeding
        restBreastFeedingMockMvc.perform(get("/api/breast-feedings/{id}", breastFeeding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(breastFeeding.getId().intValue()))
            .andExpect(jsonPath("$.breastFeeding").value(DEFAULT_BREAST_FEEDING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBreastFeeding() throws Exception {
        // Get the breastFeeding
        restBreastFeedingMockMvc.perform(get("/api/breast-feedings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBreastFeeding() throws Exception {
        // Initialize the database
        breastFeedingRepository.saveAndFlush(breastFeeding);

        int databaseSizeBeforeUpdate = breastFeedingRepository.findAll().size();

        // Update the breastFeeding
        BreastFeeding updatedBreastFeeding = breastFeedingRepository.findById(breastFeeding.getId()).get();
        // Disconnect from session so that the updates on updatedBreastFeeding are not directly saved in db
        em.detach(updatedBreastFeeding);
        updatedBreastFeeding
            .breastFeeding(UPDATED_BREAST_FEEDING);
        BreastFeedingDTO breastFeedingDTO = breastFeedingMapper.toDto(updatedBreastFeeding);

        restBreastFeedingMockMvc.perform(put("/api/breast-feedings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breastFeedingDTO)))
            .andExpect(status().isOk());

        // Validate the BreastFeeding in the database
        List<BreastFeeding> breastFeedingList = breastFeedingRepository.findAll();
        assertThat(breastFeedingList).hasSize(databaseSizeBeforeUpdate);
        BreastFeeding testBreastFeeding = breastFeedingList.get(breastFeedingList.size() - 1);
        assertThat(testBreastFeeding.getBreastFeeding()).isEqualTo(UPDATED_BREAST_FEEDING);
    }

    @Test
    @Transactional
    public void updateNonExistingBreastFeeding() throws Exception {
        int databaseSizeBeforeUpdate = breastFeedingRepository.findAll().size();

        // Create the BreastFeeding
        BreastFeedingDTO breastFeedingDTO = breastFeedingMapper.toDto(breastFeeding);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBreastFeedingMockMvc.perform(put("/api/breast-feedings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breastFeedingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BreastFeeding in the database
        List<BreastFeeding> breastFeedingList = breastFeedingRepository.findAll();
        assertThat(breastFeedingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBreastFeeding() throws Exception {
        // Initialize the database
        breastFeedingRepository.saveAndFlush(breastFeeding);

        int databaseSizeBeforeDelete = breastFeedingRepository.findAll().size();

        // Delete the breastFeeding
        restBreastFeedingMockMvc.perform(delete("/api/breast-feedings/{id}", breastFeeding.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BreastFeeding> breastFeedingList = breastFeedingRepository.findAll();
        assertThat(breastFeedingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BreastFeeding.class);
        BreastFeeding breastFeeding1 = new BreastFeeding();
        breastFeeding1.setId(1L);
        BreastFeeding breastFeeding2 = new BreastFeeding();
        breastFeeding2.setId(breastFeeding1.getId());
        assertThat(breastFeeding1).isEqualTo(breastFeeding2);
        breastFeeding2.setId(2L);
        assertThat(breastFeeding1).isNotEqualTo(breastFeeding2);
        breastFeeding1.setId(null);
        assertThat(breastFeeding1).isNotEqualTo(breastFeeding2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BreastFeedingDTO.class);
        BreastFeedingDTO breastFeedingDTO1 = new BreastFeedingDTO();
        breastFeedingDTO1.setId(1L);
        BreastFeedingDTO breastFeedingDTO2 = new BreastFeedingDTO();
        assertThat(breastFeedingDTO1).isNotEqualTo(breastFeedingDTO2);
        breastFeedingDTO2.setId(breastFeedingDTO1.getId());
        assertThat(breastFeedingDTO1).isEqualTo(breastFeedingDTO2);
        breastFeedingDTO2.setId(2L);
        assertThat(breastFeedingDTO1).isNotEqualTo(breastFeedingDTO2);
        breastFeedingDTO1.setId(null);
        assertThat(breastFeedingDTO1).isNotEqualTo(breastFeedingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(breastFeedingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(breastFeedingMapper.fromId(null)).isNull();
    }
}
