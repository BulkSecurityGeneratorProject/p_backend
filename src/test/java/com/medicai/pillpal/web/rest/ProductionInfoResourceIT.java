package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.ProductionInfo;
import com.medicai.pillpal.repository.ProductionInfoRepository;
import com.medicai.pillpal.service.ProductionInfoService;
import com.medicai.pillpal.service.dto.ProductionInfoDTO;
import com.medicai.pillpal.service.mapper.ProductionInfoMapper;
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
 * Integration tests for the {@Link ProductionInfoResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class ProductionInfoResourceIT {

    private static final String DEFAULT_PRODUCER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURING_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURING_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private ProductionInfoRepository productionInfoRepository;

    @Autowired
    private ProductionInfoMapper productionInfoMapper;

    @Autowired
    private ProductionInfoService productionInfoService;

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

    private MockMvc restProductionInfoMockMvc;

    private ProductionInfo productionInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductionInfoResource productionInfoResource = new ProductionInfoResource(productionInfoService);
        this.restProductionInfoMockMvc = MockMvcBuilders.standaloneSetup(productionInfoResource)
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
    public static ProductionInfo createEntity(EntityManager em) {
        ProductionInfo productionInfo = new ProductionInfo()
            .producerName(DEFAULT_PRODUCER_NAME)
            .manufacturingCountry(DEFAULT_MANUFACTURING_COUNTRY);
        return productionInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionInfo createUpdatedEntity(EntityManager em) {
        ProductionInfo productionInfo = new ProductionInfo()
            .producerName(UPDATED_PRODUCER_NAME)
            .manufacturingCountry(UPDATED_MANUFACTURING_COUNTRY);
        return productionInfo;
    }

    @BeforeEach
    public void initTest() {
        productionInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductionInfo() throws Exception {
        int databaseSizeBeforeCreate = productionInfoRepository.findAll().size();

        // Create the ProductionInfo
        ProductionInfoDTO productionInfoDTO = productionInfoMapper.toDto(productionInfo);
        restProductionInfoMockMvc.perform(post("/api/production-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductionInfo in the database
        List<ProductionInfo> productionInfoList = productionInfoRepository.findAll();
        assertThat(productionInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ProductionInfo testProductionInfo = productionInfoList.get(productionInfoList.size() - 1);
        assertThat(testProductionInfo.getProducerName()).isEqualTo(DEFAULT_PRODUCER_NAME);
        assertThat(testProductionInfo.getManufacturingCountry()).isEqualTo(DEFAULT_MANUFACTURING_COUNTRY);
    }

    @Test
    @Transactional
    public void createProductionInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productionInfoRepository.findAll().size();

        // Create the ProductionInfo with an existing ID
        productionInfo.setId(1L);
        ProductionInfoDTO productionInfoDTO = productionInfoMapper.toDto(productionInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionInfoMockMvc.perform(post("/api/production-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionInfo in the database
        List<ProductionInfo> productionInfoList = productionInfoRepository.findAll();
        assertThat(productionInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductionInfos() throws Exception {
        // Initialize the database
        productionInfoRepository.saveAndFlush(productionInfo);

        // Get all the productionInfoList
        restProductionInfoMockMvc.perform(get("/api/production-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].producerName").value(hasItem(DEFAULT_PRODUCER_NAME.toString())))
            .andExpect(jsonPath("$.[*].manufacturingCountry").value(hasItem(DEFAULT_MANUFACTURING_COUNTRY.toString())));
    }
    
    @Test
    @Transactional
    public void getProductionInfo() throws Exception {
        // Initialize the database
        productionInfoRepository.saveAndFlush(productionInfo);

        // Get the productionInfo
        restProductionInfoMockMvc.perform(get("/api/production-infos/{id}", productionInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productionInfo.getId().intValue()))
            .andExpect(jsonPath("$.producerName").value(DEFAULT_PRODUCER_NAME.toString()))
            .andExpect(jsonPath("$.manufacturingCountry").value(DEFAULT_MANUFACTURING_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductionInfo() throws Exception {
        // Get the productionInfo
        restProductionInfoMockMvc.perform(get("/api/production-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductionInfo() throws Exception {
        // Initialize the database
        productionInfoRepository.saveAndFlush(productionInfo);

        int databaseSizeBeforeUpdate = productionInfoRepository.findAll().size();

        // Update the productionInfo
        ProductionInfo updatedProductionInfo = productionInfoRepository.findById(productionInfo.getId()).get();
        // Disconnect from session so that the updates on updatedProductionInfo are not directly saved in db
        em.detach(updatedProductionInfo);
        updatedProductionInfo
            .producerName(UPDATED_PRODUCER_NAME)
            .manufacturingCountry(UPDATED_MANUFACTURING_COUNTRY);
        ProductionInfoDTO productionInfoDTO = productionInfoMapper.toDto(updatedProductionInfo);

        restProductionInfoMockMvc.perform(put("/api/production-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionInfoDTO)))
            .andExpect(status().isOk());

        // Validate the ProductionInfo in the database
        List<ProductionInfo> productionInfoList = productionInfoRepository.findAll();
        assertThat(productionInfoList).hasSize(databaseSizeBeforeUpdate);
        ProductionInfo testProductionInfo = productionInfoList.get(productionInfoList.size() - 1);
        assertThat(testProductionInfo.getProducerName()).isEqualTo(UPDATED_PRODUCER_NAME);
        assertThat(testProductionInfo.getManufacturingCountry()).isEqualTo(UPDATED_MANUFACTURING_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingProductionInfo() throws Exception {
        int databaseSizeBeforeUpdate = productionInfoRepository.findAll().size();

        // Create the ProductionInfo
        ProductionInfoDTO productionInfoDTO = productionInfoMapper.toDto(productionInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionInfoMockMvc.perform(put("/api/production-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionInfo in the database
        List<ProductionInfo> productionInfoList = productionInfoRepository.findAll();
        assertThat(productionInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductionInfo() throws Exception {
        // Initialize the database
        productionInfoRepository.saveAndFlush(productionInfo);

        int databaseSizeBeforeDelete = productionInfoRepository.findAll().size();

        // Delete the productionInfo
        restProductionInfoMockMvc.perform(delete("/api/production-infos/{id}", productionInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductionInfo> productionInfoList = productionInfoRepository.findAll();
        assertThat(productionInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionInfo.class);
        ProductionInfo productionInfo1 = new ProductionInfo();
        productionInfo1.setId(1L);
        ProductionInfo productionInfo2 = new ProductionInfo();
        productionInfo2.setId(productionInfo1.getId());
        assertThat(productionInfo1).isEqualTo(productionInfo2);
        productionInfo2.setId(2L);
        assertThat(productionInfo1).isNotEqualTo(productionInfo2);
        productionInfo1.setId(null);
        assertThat(productionInfo1).isNotEqualTo(productionInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionInfoDTO.class);
        ProductionInfoDTO productionInfoDTO1 = new ProductionInfoDTO();
        productionInfoDTO1.setId(1L);
        ProductionInfoDTO productionInfoDTO2 = new ProductionInfoDTO();
        assertThat(productionInfoDTO1).isNotEqualTo(productionInfoDTO2);
        productionInfoDTO2.setId(productionInfoDTO1.getId());
        assertThat(productionInfoDTO1).isEqualTo(productionInfoDTO2);
        productionInfoDTO2.setId(2L);
        assertThat(productionInfoDTO1).isNotEqualTo(productionInfoDTO2);
        productionInfoDTO1.setId(null);
        assertThat(productionInfoDTO1).isNotEqualTo(productionInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productionInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productionInfoMapper.fromId(null)).isNull();
    }
}
