package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.Allergy;
import com.medicai.pillpal.repository.AllergyRepository;
import com.medicai.pillpal.service.AllergyService;
import com.medicai.pillpal.service.dto.AllergyDTO;
import com.medicai.pillpal.service.mapper.AllergyMapper;
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
 * Integration tests for the {@Link AllergyResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class AllergyResourceIT {

    private static final String DEFAULT_ALLERGY = "AAAAAAAAAA";
    private static final String UPDATED_ALLERGY = "BBBBBBBBBB";

    @Autowired
    private AllergyRepository allergyRepository;

    @Autowired
    private AllergyMapper allergyMapper;

    @Autowired
    private AllergyService allergyService;

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

    private MockMvc restAllergyMockMvc;

    private Allergy allergy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllergyResource allergyResource = new AllergyResource(allergyService);
        this.restAllergyMockMvc = MockMvcBuilders.standaloneSetup(allergyResource)
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
    public static Allergy createEntity(EntityManager em) {
        Allergy allergy = new Allergy()
            .allergy(DEFAULT_ALLERGY);
        return allergy;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Allergy createUpdatedEntity(EntityManager em) {
        Allergy allergy = new Allergy()
            .allergy(UPDATED_ALLERGY);
        return allergy;
    }

    @BeforeEach
    public void initTest() {
        allergy = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllergy() throws Exception {
        int databaseSizeBeforeCreate = allergyRepository.findAll().size();

        // Create the Allergy
        AllergyDTO allergyDTO = allergyMapper.toDto(allergy);
        restAllergyMockMvc.perform(post("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
            .andExpect(status().isCreated());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeCreate + 1);
        Allergy testAllergy = allergyList.get(allergyList.size() - 1);
        assertThat(testAllergy.getAllergy()).isEqualTo(DEFAULT_ALLERGY);
    }

    @Test
    @Transactional
    public void createAllergyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allergyRepository.findAll().size();

        // Create the Allergy with an existing ID
        allergy.setId(1L);
        AllergyDTO allergyDTO = allergyMapper.toDto(allergy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllergyMockMvc.perform(post("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAllergies() throws Exception {
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);

        // Get all the allergyList
        restAllergyMockMvc.perform(get("/api/allergies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergy.getId().intValue())))
            .andExpect(jsonPath("$.[*].allergy").value(hasItem(DEFAULT_ALLERGY.toString())));
    }

    @Test
    @Transactional
    public void getAllergy() throws Exception {
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);

        // Get the allergy
        restAllergyMockMvc.perform(get("/api/allergies/{id}", allergy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allergy.getId().intValue()))
            .andExpect(jsonPath("$.allergy").value(DEFAULT_ALLERGY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAllergy() throws Exception {
        // Get the allergy
        restAllergyMockMvc.perform(get("/api/allergies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllergy() throws Exception {
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);

        int databaseSizeBeforeUpdate = allergyRepository.findAll().size();

        // Update the allergy
        Allergy updatedAllergy = allergyRepository.findById(allergy.getId()).get();
        // Disconnect from session so that the updates on updatedAllergy are not directly saved in db
        em.detach(updatedAllergy);
        updatedAllergy
            .allergy(UPDATED_ALLERGY);
        AllergyDTO allergyDTO = allergyMapper.toDto(updatedAllergy);

        restAllergyMockMvc.perform(put("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
            .andExpect(status().isOk());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeUpdate);
        Allergy testAllergy = allergyList.get(allergyList.size() - 1);
        assertThat(testAllergy.getAllergy()).isEqualTo(UPDATED_ALLERGY);
    }

    @Test
    @Transactional
    public void updateNonExistingAllergy() throws Exception {
        int databaseSizeBeforeUpdate = allergyRepository.findAll().size();

        // Create the Allergy
        AllergyDTO allergyDTO = allergyMapper.toDto(allergy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllergyMockMvc.perform(put("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAllergy() throws Exception {
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);

        int databaseSizeBeforeDelete = allergyRepository.findAll().size();

        // Delete the allergy
        restAllergyMockMvc.perform(delete("/api/allergies/{id}", allergy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Allergy.class);
        Allergy allergy1 = new Allergy();
        allergy1.setId(1L);
        Allergy allergy2 = new Allergy();
        allergy2.setId(allergy1.getId());
        assertThat(allergy1).isEqualTo(allergy2);
        allergy2.setId(2L);
        assertThat(allergy1).isNotEqualTo(allergy2);
        allergy1.setId(null);
        assertThat(allergy1).isNotEqualTo(allergy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllergyDTO.class);
        AllergyDTO allergyDTO1 = new AllergyDTO();
        allergyDTO1.setId(1L);
        AllergyDTO allergyDTO2 = new AllergyDTO();
        assertThat(allergyDTO1).isNotEqualTo(allergyDTO2);
        allergyDTO2.setId(allergyDTO1.getId());
        assertThat(allergyDTO1).isEqualTo(allergyDTO2);
        allergyDTO2.setId(2L);
        assertThat(allergyDTO1).isNotEqualTo(allergyDTO2);
        allergyDTO1.setId(null);
        assertThat(allergyDTO1).isNotEqualTo(allergyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(allergyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(allergyMapper.fromId(null)).isNull();
    }
}
