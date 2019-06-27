package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.UseAndStorage;
import com.medicai.pillpal.repository.UseAndStorageRepository;
import com.medicai.pillpal.service.UseAndStorageService;
import com.medicai.pillpal.service.dto.UseAndStorageDTO;
import com.medicai.pillpal.service.mapper.UseAndStorageMapper;
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
 * Integration tests for the {@Link UseAndStorageResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class UseAndStorageResourceIT {

    private static final String DEFAULT_CONCLUSION = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION = "BBBBBBBBBB";

    private static final String DEFAULT_USE_AND_STORAGE = "AAAAAAAAAA";
    private static final String UPDATED_USE_AND_STORAGE = "BBBBBBBBBB";

    @Autowired
    private UseAndStorageRepository useAndStorageRepository;

    @Autowired
    private UseAndStorageMapper useAndStorageMapper;

    @Autowired
    private UseAndStorageService useAndStorageService;

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

    private MockMvc restUseAndStorageMockMvc;

    private UseAndStorage useAndStorage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UseAndStorageResource useAndStorageResource = new UseAndStorageResource(useAndStorageService);
        this.restUseAndStorageMockMvc = MockMvcBuilders.standaloneSetup(useAndStorageResource)
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
    public static UseAndStorage createEntity(EntityManager em) {
        UseAndStorage useAndStorage = new UseAndStorage()
            .conclusion(DEFAULT_CONCLUSION)
            .useAndStorage(DEFAULT_USE_AND_STORAGE);
        return useAndStorage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UseAndStorage createUpdatedEntity(EntityManager em) {
        UseAndStorage useAndStorage = new UseAndStorage()
            .conclusion(UPDATED_CONCLUSION)
            .useAndStorage(UPDATED_USE_AND_STORAGE);
        return useAndStorage;
    }

    @BeforeEach
    public void initTest() {
        useAndStorage = createEntity(em);
    }

    @Test
    @Transactional
    public void createUseAndStorage() throws Exception {
        int databaseSizeBeforeCreate = useAndStorageRepository.findAll().size();

        // Create the UseAndStorage
        UseAndStorageDTO useAndStorageDTO = useAndStorageMapper.toDto(useAndStorage);
        restUseAndStorageMockMvc.perform(post("/api/use-and-storages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(useAndStorageDTO)))
            .andExpect(status().isCreated());

        // Validate the UseAndStorage in the database
        List<UseAndStorage> useAndStorageList = useAndStorageRepository.findAll();
        assertThat(useAndStorageList).hasSize(databaseSizeBeforeCreate + 1);
        UseAndStorage testUseAndStorage = useAndStorageList.get(useAndStorageList.size() - 1);
        assertThat(testUseAndStorage.getConclusion()).isEqualTo(DEFAULT_CONCLUSION);
        assertThat(testUseAndStorage.getUseAndStorage()).isEqualTo(DEFAULT_USE_AND_STORAGE);
    }

    @Test
    @Transactional
    public void createUseAndStorageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = useAndStorageRepository.findAll().size();

        // Create the UseAndStorage with an existing ID
        useAndStorage.setId(1L);
        UseAndStorageDTO useAndStorageDTO = useAndStorageMapper.toDto(useAndStorage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUseAndStorageMockMvc.perform(post("/api/use-and-storages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(useAndStorageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UseAndStorage in the database
        List<UseAndStorage> useAndStorageList = useAndStorageRepository.findAll();
        assertThat(useAndStorageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUseAndStorages() throws Exception {
        // Initialize the database
        useAndStorageRepository.saveAndFlush(useAndStorage);

        // Get all the useAndStorageList
        restUseAndStorageMockMvc.perform(get("/api/use-and-storages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(useAndStorage.getId().intValue())))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION.toString())))
            .andExpect(jsonPath("$.[*].useAndStorage").value(hasItem(DEFAULT_USE_AND_STORAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getUseAndStorage() throws Exception {
        // Initialize the database
        useAndStorageRepository.saveAndFlush(useAndStorage);

        // Get the useAndStorage
        restUseAndStorageMockMvc.perform(get("/api/use-and-storages/{id}", useAndStorage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(useAndStorage.getId().intValue()))
            .andExpect(jsonPath("$.conclusion").value(DEFAULT_CONCLUSION.toString()))
            .andExpect(jsonPath("$.useAndStorage").value(DEFAULT_USE_AND_STORAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUseAndStorage() throws Exception {
        // Get the useAndStorage
        restUseAndStorageMockMvc.perform(get("/api/use-and-storages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUseAndStorage() throws Exception {
        // Initialize the database
        useAndStorageRepository.saveAndFlush(useAndStorage);

        int databaseSizeBeforeUpdate = useAndStorageRepository.findAll().size();

        // Update the useAndStorage
        UseAndStorage updatedUseAndStorage = useAndStorageRepository.findById(useAndStorage.getId()).get();
        // Disconnect from session so that the updates on updatedUseAndStorage are not directly saved in db
        em.detach(updatedUseAndStorage);
        updatedUseAndStorage
            .conclusion(UPDATED_CONCLUSION)
            .useAndStorage(UPDATED_USE_AND_STORAGE);
        UseAndStorageDTO useAndStorageDTO = useAndStorageMapper.toDto(updatedUseAndStorage);

        restUseAndStorageMockMvc.perform(put("/api/use-and-storages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(useAndStorageDTO)))
            .andExpect(status().isOk());

        // Validate the UseAndStorage in the database
        List<UseAndStorage> useAndStorageList = useAndStorageRepository.findAll();
        assertThat(useAndStorageList).hasSize(databaseSizeBeforeUpdate);
        UseAndStorage testUseAndStorage = useAndStorageList.get(useAndStorageList.size() - 1);
        assertThat(testUseAndStorage.getConclusion()).isEqualTo(UPDATED_CONCLUSION);
        assertThat(testUseAndStorage.getUseAndStorage()).isEqualTo(UPDATED_USE_AND_STORAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingUseAndStorage() throws Exception {
        int databaseSizeBeforeUpdate = useAndStorageRepository.findAll().size();

        // Create the UseAndStorage
        UseAndStorageDTO useAndStorageDTO = useAndStorageMapper.toDto(useAndStorage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUseAndStorageMockMvc.perform(put("/api/use-and-storages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(useAndStorageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UseAndStorage in the database
        List<UseAndStorage> useAndStorageList = useAndStorageRepository.findAll();
        assertThat(useAndStorageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUseAndStorage() throws Exception {
        // Initialize the database
        useAndStorageRepository.saveAndFlush(useAndStorage);

        int databaseSizeBeforeDelete = useAndStorageRepository.findAll().size();

        // Delete the useAndStorage
        restUseAndStorageMockMvc.perform(delete("/api/use-and-storages/{id}", useAndStorage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UseAndStorage> useAndStorageList = useAndStorageRepository.findAll();
        assertThat(useAndStorageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UseAndStorage.class);
        UseAndStorage useAndStorage1 = new UseAndStorage();
        useAndStorage1.setId(1L);
        UseAndStorage useAndStorage2 = new UseAndStorage();
        useAndStorage2.setId(useAndStorage1.getId());
        assertThat(useAndStorage1).isEqualTo(useAndStorage2);
        useAndStorage2.setId(2L);
        assertThat(useAndStorage1).isNotEqualTo(useAndStorage2);
        useAndStorage1.setId(null);
        assertThat(useAndStorage1).isNotEqualTo(useAndStorage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UseAndStorageDTO.class);
        UseAndStorageDTO useAndStorageDTO1 = new UseAndStorageDTO();
        useAndStorageDTO1.setId(1L);
        UseAndStorageDTO useAndStorageDTO2 = new UseAndStorageDTO();
        assertThat(useAndStorageDTO1).isNotEqualTo(useAndStorageDTO2);
        useAndStorageDTO2.setId(useAndStorageDTO1.getId());
        assertThat(useAndStorageDTO1).isEqualTo(useAndStorageDTO2);
        useAndStorageDTO2.setId(2L);
        assertThat(useAndStorageDTO1).isNotEqualTo(useAndStorageDTO2);
        useAndStorageDTO1.setId(null);
        assertThat(useAndStorageDTO1).isNotEqualTo(useAndStorageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(useAndStorageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(useAndStorageMapper.fromId(null)).isNull();
    }
}
