package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.PharmaceuticalCode;
import com.medicai.pillpal.repository.PharmaceuticalCodeRepository;
import com.medicai.pillpal.service.PharmaceuticalCodeService;
import com.medicai.pillpal.service.dto.PharmaceuticalCodeDTO;
import com.medicai.pillpal.service.mapper.PharmaceuticalCodeMapper;
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
 * Integration tests for the {@Link PharmaceuticalCodeResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class PharmaceuticalCodeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private PharmaceuticalCodeRepository pharmaceuticalCodeRepository;

    @Autowired
    private PharmaceuticalCodeMapper pharmaceuticalCodeMapper;

    @Autowired
    private PharmaceuticalCodeService pharmaceuticalCodeService;

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

    private MockMvc restPharmaceuticalCodeMockMvc;

    private PharmaceuticalCode pharmaceuticalCode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PharmaceuticalCodeResource pharmaceuticalCodeResource = new PharmaceuticalCodeResource(pharmaceuticalCodeService);
        this.restPharmaceuticalCodeMockMvc = MockMvcBuilders.standaloneSetup(pharmaceuticalCodeResource)
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
    public static PharmaceuticalCode createEntity(EntityManager em) {
        PharmaceuticalCode pharmaceuticalCode = new PharmaceuticalCode()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return pharmaceuticalCode;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PharmaceuticalCode createUpdatedEntity(EntityManager em) {
        PharmaceuticalCode pharmaceuticalCode = new PharmaceuticalCode()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return pharmaceuticalCode;
    }

    @BeforeEach
    public void initTest() {
        pharmaceuticalCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createPharmaceuticalCode() throws Exception {
        int databaseSizeBeforeCreate = pharmaceuticalCodeRepository.findAll().size();

        // Create the PharmaceuticalCode
        PharmaceuticalCodeDTO pharmaceuticalCodeDTO = pharmaceuticalCodeMapper.toDto(pharmaceuticalCode);
        restPharmaceuticalCodeMockMvc.perform(post("/api/pharmaceutical-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmaceuticalCodeDTO)))
            .andExpect(status().isCreated());

        // Validate the PharmaceuticalCode in the database
        List<PharmaceuticalCode> pharmaceuticalCodeList = pharmaceuticalCodeRepository.findAll();
        assertThat(pharmaceuticalCodeList).hasSize(databaseSizeBeforeCreate + 1);
        PharmaceuticalCode testPharmaceuticalCode = pharmaceuticalCodeList.get(pharmaceuticalCodeList.size() - 1);
        assertThat(testPharmaceuticalCode.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPharmaceuticalCode.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPharmaceuticalCode.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createPharmaceuticalCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pharmaceuticalCodeRepository.findAll().size();

        // Create the PharmaceuticalCode with an existing ID
        pharmaceuticalCode.setId(1L);
        PharmaceuticalCodeDTO pharmaceuticalCodeDTO = pharmaceuticalCodeMapper.toDto(pharmaceuticalCode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPharmaceuticalCodeMockMvc.perform(post("/api/pharmaceutical-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmaceuticalCodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PharmaceuticalCode in the database
        List<PharmaceuticalCode> pharmaceuticalCodeList = pharmaceuticalCodeRepository.findAll();
        assertThat(pharmaceuticalCodeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPharmaceuticalCodes() throws Exception {
        // Initialize the database
        pharmaceuticalCodeRepository.saveAndFlush(pharmaceuticalCode);

        // Get all the pharmaceuticalCodeList
        restPharmaceuticalCodeMockMvc.perform(get("/api/pharmaceutical-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmaceuticalCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getPharmaceuticalCode() throws Exception {
        // Initialize the database
        pharmaceuticalCodeRepository.saveAndFlush(pharmaceuticalCode);

        // Get the pharmaceuticalCode
        restPharmaceuticalCodeMockMvc.perform(get("/api/pharmaceutical-codes/{id}", pharmaceuticalCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pharmaceuticalCode.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPharmaceuticalCode() throws Exception {
        // Get the pharmaceuticalCode
        restPharmaceuticalCodeMockMvc.perform(get("/api/pharmaceutical-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePharmaceuticalCode() throws Exception {
        // Initialize the database
        pharmaceuticalCodeRepository.saveAndFlush(pharmaceuticalCode);

        int databaseSizeBeforeUpdate = pharmaceuticalCodeRepository.findAll().size();

        // Update the pharmaceuticalCode
        PharmaceuticalCode updatedPharmaceuticalCode = pharmaceuticalCodeRepository.findById(pharmaceuticalCode.getId()).get();
        // Disconnect from session so that the updates on updatedPharmaceuticalCode are not directly saved in db
        em.detach(updatedPharmaceuticalCode);
        updatedPharmaceuticalCode
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        PharmaceuticalCodeDTO pharmaceuticalCodeDTO = pharmaceuticalCodeMapper.toDto(updatedPharmaceuticalCode);

        restPharmaceuticalCodeMockMvc.perform(put("/api/pharmaceutical-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmaceuticalCodeDTO)))
            .andExpect(status().isOk());

        // Validate the PharmaceuticalCode in the database
        List<PharmaceuticalCode> pharmaceuticalCodeList = pharmaceuticalCodeRepository.findAll();
        assertThat(pharmaceuticalCodeList).hasSize(databaseSizeBeforeUpdate);
        PharmaceuticalCode testPharmaceuticalCode = pharmaceuticalCodeList.get(pharmaceuticalCodeList.size() - 1);
        assertThat(testPharmaceuticalCode.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPharmaceuticalCode.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPharmaceuticalCode.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingPharmaceuticalCode() throws Exception {
        int databaseSizeBeforeUpdate = pharmaceuticalCodeRepository.findAll().size();

        // Create the PharmaceuticalCode
        PharmaceuticalCodeDTO pharmaceuticalCodeDTO = pharmaceuticalCodeMapper.toDto(pharmaceuticalCode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPharmaceuticalCodeMockMvc.perform(put("/api/pharmaceutical-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmaceuticalCodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PharmaceuticalCode in the database
        List<PharmaceuticalCode> pharmaceuticalCodeList = pharmaceuticalCodeRepository.findAll();
        assertThat(pharmaceuticalCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePharmaceuticalCode() throws Exception {
        // Initialize the database
        pharmaceuticalCodeRepository.saveAndFlush(pharmaceuticalCode);

        int databaseSizeBeforeDelete = pharmaceuticalCodeRepository.findAll().size();

        // Delete the pharmaceuticalCode
        restPharmaceuticalCodeMockMvc.perform(delete("/api/pharmaceutical-codes/{id}", pharmaceuticalCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PharmaceuticalCode> pharmaceuticalCodeList = pharmaceuticalCodeRepository.findAll();
        assertThat(pharmaceuticalCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PharmaceuticalCode.class);
        PharmaceuticalCode pharmaceuticalCode1 = new PharmaceuticalCode();
        pharmaceuticalCode1.setId(1L);
        PharmaceuticalCode pharmaceuticalCode2 = new PharmaceuticalCode();
        pharmaceuticalCode2.setId(pharmaceuticalCode1.getId());
        assertThat(pharmaceuticalCode1).isEqualTo(pharmaceuticalCode2);
        pharmaceuticalCode2.setId(2L);
        assertThat(pharmaceuticalCode1).isNotEqualTo(pharmaceuticalCode2);
        pharmaceuticalCode1.setId(null);
        assertThat(pharmaceuticalCode1).isNotEqualTo(pharmaceuticalCode2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PharmaceuticalCodeDTO.class);
        PharmaceuticalCodeDTO pharmaceuticalCodeDTO1 = new PharmaceuticalCodeDTO();
        pharmaceuticalCodeDTO1.setId(1L);
        PharmaceuticalCodeDTO pharmaceuticalCodeDTO2 = new PharmaceuticalCodeDTO();
        assertThat(pharmaceuticalCodeDTO1).isNotEqualTo(pharmaceuticalCodeDTO2);
        pharmaceuticalCodeDTO2.setId(pharmaceuticalCodeDTO1.getId());
        assertThat(pharmaceuticalCodeDTO1).isEqualTo(pharmaceuticalCodeDTO2);
        pharmaceuticalCodeDTO2.setId(2L);
        assertThat(pharmaceuticalCodeDTO1).isNotEqualTo(pharmaceuticalCodeDTO2);
        pharmaceuticalCodeDTO1.setId(null);
        assertThat(pharmaceuticalCodeDTO1).isNotEqualTo(pharmaceuticalCodeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pharmaceuticalCodeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pharmaceuticalCodeMapper.fromId(null)).isNull();
    }
}
