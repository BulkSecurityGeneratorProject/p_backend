package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.ApplicationInfo;
import com.medicai.pillpal.repository.ApplicationInfoRepository;
import com.medicai.pillpal.service.ApplicationInfoService;
import com.medicai.pillpal.service.dto.ApplicationInfoDTO;
import com.medicai.pillpal.service.mapper.ApplicationInfoMapper;
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

import com.medicai.pillpal.domain.enumeration.Form;
import com.medicai.pillpal.domain.enumeration.RoutsOfAdministration;
/**
 * Integration tests for the {@Link ApplicationInfoResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class ApplicationInfoResourceIT {

    private static final String DEFAULT_FDA_APPLICATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_FDA_APPLICATION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENERIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GENERIC_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OVER_VIEW = "AAAAAAAAAA";
    private static final String UPDATED_OVER_VIEW = "BBBBBBBBBB";

    private static final Float DEFAULT_STRENGTH_AMOUNT = 1F;
    private static final Float UPDATED_STRENGTH_AMOUNT = 2F;

    private static final String DEFAULT_STRENGTH_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_STRENGTH_UNIT = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRODUCT_NUMBER = 1;
    private static final Integer UPDATED_PRODUCT_NUMBER = 2;

    private static final String DEFAULT_ACTIVE_INGREDIENT = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVE_INGREDIENT = "BBBBBBBBBB";

    private static final Form DEFAULT_FORM = Form.AEROSOL;
    private static final Form UPDATED_FORM = Form.AEROSOL;

    private static final RoutsOfAdministration DEFAULT_ROUTS_OF_ADMINISTRATION = RoutsOfAdministration.BUCCAL_SUBLINGUAL;
    private static final RoutsOfAdministration UPDATED_ROUTS_OF_ADMINISTRATION = RoutsOfAdministration.BUCCAL;

    @Autowired
    private ApplicationInfoRepository applicationInfoRepository;

    @Autowired
    private ApplicationInfoMapper applicationInfoMapper;

    @Autowired
    private ApplicationInfoService applicationInfoService;

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

    private MockMvc restApplicationInfoMockMvc;

    private ApplicationInfo applicationInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationInfoResource applicationInfoResource = new ApplicationInfoResource(applicationInfoService);
        this.restApplicationInfoMockMvc = MockMvcBuilders.standaloneSetup(applicationInfoResource)
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
    public static ApplicationInfo createEntity(EntityManager em) {
        ApplicationInfo applicationInfo = new ApplicationInfo()
            .fdaApplicationNo(DEFAULT_FDA_APPLICATION_NO)
            .name(DEFAULT_NAME)
            .genericName(DEFAULT_GENERIC_NAME)
            .brandName(DEFAULT_BRAND_NAME)
            .overView(DEFAULT_OVER_VIEW)
            .strengthAmount(DEFAULT_STRENGTH_AMOUNT)
            .strengthUnit(DEFAULT_STRENGTH_UNIT)
            .productNumber(DEFAULT_PRODUCT_NUMBER)
            .activeIngredient(DEFAULT_ACTIVE_INGREDIENT)
            .form(DEFAULT_FORM)
            .routsOfAdministration(DEFAULT_ROUTS_OF_ADMINISTRATION);
        return applicationInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationInfo createUpdatedEntity(EntityManager em) {
        ApplicationInfo applicationInfo = new ApplicationInfo()
            .fdaApplicationNo(UPDATED_FDA_APPLICATION_NO)
            .name(UPDATED_NAME)
            .genericName(UPDATED_GENERIC_NAME)
            .brandName(UPDATED_BRAND_NAME)
            .overView(UPDATED_OVER_VIEW)
            .strengthAmount(UPDATED_STRENGTH_AMOUNT)
            .strengthUnit(UPDATED_STRENGTH_UNIT)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .activeIngredient(UPDATED_ACTIVE_INGREDIENT)
            .form(UPDATED_FORM)
            .routsOfAdministration(UPDATED_ROUTS_OF_ADMINISTRATION);
        return applicationInfo;
    }

    @BeforeEach
    public void initTest() {
        applicationInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationInfo() throws Exception {
        int databaseSizeBeforeCreate = applicationInfoRepository.findAll().size();

        // Create the ApplicationInfo
        ApplicationInfoDTO applicationInfoDTO = applicationInfoMapper.toDto(applicationInfo);
        restApplicationInfoMockMvc.perform(post("/api/application-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationInfo in the database
        List<ApplicationInfo> applicationInfoList = applicationInfoRepository.findAll();
        assertThat(applicationInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationInfo testApplicationInfo = applicationInfoList.get(applicationInfoList.size() - 1);
        assertThat(testApplicationInfo.getFdaApplicationNo()).isEqualTo(DEFAULT_FDA_APPLICATION_NO);
        assertThat(testApplicationInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicationInfo.getGenericName()).isEqualTo(DEFAULT_GENERIC_NAME);
        assertThat(testApplicationInfo.getBrandName()).isEqualTo(DEFAULT_BRAND_NAME);
        assertThat(testApplicationInfo.getOverView()).isEqualTo(DEFAULT_OVER_VIEW);
        assertThat(testApplicationInfo.getStrengthAmount()).isEqualTo(DEFAULT_STRENGTH_AMOUNT);
        assertThat(testApplicationInfo.getStrengthUnit()).isEqualTo(DEFAULT_STRENGTH_UNIT);
        assertThat(testApplicationInfo.getProductNumber()).isEqualTo(DEFAULT_PRODUCT_NUMBER);
        assertThat(testApplicationInfo.getActiveIngredient()).isEqualTo(DEFAULT_ACTIVE_INGREDIENT);
        assertThat(testApplicationInfo.getForm()).isEqualTo(DEFAULT_FORM);
        assertThat(testApplicationInfo.getRoutsOfAdministration()).isEqualTo(DEFAULT_ROUTS_OF_ADMINISTRATION);
    }

    @Test
    @Transactional
    public void createApplicationInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationInfoRepository.findAll().size();

        // Create the ApplicationInfo with an existing ID
        applicationInfo.setId(1L);
        ApplicationInfoDTO applicationInfoDTO = applicationInfoMapper.toDto(applicationInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationInfoMockMvc.perform(post("/api/application-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationInfo in the database
        List<ApplicationInfo> applicationInfoList = applicationInfoRepository.findAll();
        assertThat(applicationInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFdaApplicationNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationInfoRepository.findAll().size();
        // set the field null
        applicationInfo.setFdaApplicationNo(null);

        // Create the ApplicationInfo, which fails.
        ApplicationInfoDTO applicationInfoDTO = applicationInfoMapper.toDto(applicationInfo);

        restApplicationInfoMockMvc.perform(post("/api/application-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationInfo> applicationInfoList = applicationInfoRepository.findAll();
        assertThat(applicationInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationInfos() throws Exception {
        // Initialize the database
        applicationInfoRepository.saveAndFlush(applicationInfo);

        // Get all the applicationInfoList
        restApplicationInfoMockMvc.perform(get("/api/application-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fdaApplicationNo").value(hasItem(DEFAULT_FDA_APPLICATION_NO.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].genericName").value(hasItem(DEFAULT_GENERIC_NAME.toString())))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME.toString())))
            .andExpect(jsonPath("$.[*].overView").value(hasItem(DEFAULT_OVER_VIEW.toString())))
            .andExpect(jsonPath("$.[*].strengthAmount").value(hasItem(DEFAULT_STRENGTH_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].strengthUnit").value(hasItem(DEFAULT_STRENGTH_UNIT.toString())))
            .andExpect(jsonPath("$.[*].productNumber").value(hasItem(DEFAULT_PRODUCT_NUMBER)))
            .andExpect(jsonPath("$.[*].activeIngredient").value(hasItem(DEFAULT_ACTIVE_INGREDIENT.toString())))
            .andExpect(jsonPath("$.[*].form").value(hasItem(DEFAULT_FORM.toString())))
            .andExpect(jsonPath("$.[*].routsOfAdministration").value(hasItem(DEFAULT_ROUTS_OF_ADMINISTRATION.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicationInfo() throws Exception {
        // Initialize the database
        applicationInfoRepository.saveAndFlush(applicationInfo);

        // Get the applicationInfo
        restApplicationInfoMockMvc.perform(get("/api/application-infos/{id}", applicationInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationInfo.getId().intValue()))
            .andExpect(jsonPath("$.fdaApplicationNo").value(DEFAULT_FDA_APPLICATION_NO.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.genericName").value(DEFAULT_GENERIC_NAME.toString()))
            .andExpect(jsonPath("$.brandName").value(DEFAULT_BRAND_NAME.toString()))
            .andExpect(jsonPath("$.overView").value(DEFAULT_OVER_VIEW.toString()))
            .andExpect(jsonPath("$.strengthAmount").value(DEFAULT_STRENGTH_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.strengthUnit").value(DEFAULT_STRENGTH_UNIT.toString()))
            .andExpect(jsonPath("$.productNumber").value(DEFAULT_PRODUCT_NUMBER))
            .andExpect(jsonPath("$.activeIngredient").value(DEFAULT_ACTIVE_INGREDIENT.toString()))
            .andExpect(jsonPath("$.form").value(DEFAULT_FORM.toString()))
            .andExpect(jsonPath("$.routsOfAdministration").value(DEFAULT_ROUTS_OF_ADMINISTRATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationInfo() throws Exception {
        // Get the applicationInfo
        restApplicationInfoMockMvc.perform(get("/api/application-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationInfo() throws Exception {
        // Initialize the database
        applicationInfoRepository.saveAndFlush(applicationInfo);

        int databaseSizeBeforeUpdate = applicationInfoRepository.findAll().size();

        // Update the applicationInfo
        ApplicationInfo updatedApplicationInfo = applicationInfoRepository.findById(applicationInfo.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationInfo are not directly saved in db
        em.detach(updatedApplicationInfo);
        updatedApplicationInfo
            .fdaApplicationNo(UPDATED_FDA_APPLICATION_NO)
            .name(UPDATED_NAME)
            .genericName(UPDATED_GENERIC_NAME)
            .brandName(UPDATED_BRAND_NAME)
            .overView(UPDATED_OVER_VIEW)
            .strengthAmount(UPDATED_STRENGTH_AMOUNT)
            .strengthUnit(UPDATED_STRENGTH_UNIT)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .activeIngredient(UPDATED_ACTIVE_INGREDIENT)
            .form(UPDATED_FORM)
            .routsOfAdministration(UPDATED_ROUTS_OF_ADMINISTRATION);
        ApplicationInfoDTO applicationInfoDTO = applicationInfoMapper.toDto(updatedApplicationInfo);

        restApplicationInfoMockMvc.perform(put("/api/application-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationInfoDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationInfo in the database
        List<ApplicationInfo> applicationInfoList = applicationInfoRepository.findAll();
        assertThat(applicationInfoList).hasSize(databaseSizeBeforeUpdate);
        ApplicationInfo testApplicationInfo = applicationInfoList.get(applicationInfoList.size() - 1);
        assertThat(testApplicationInfo.getFdaApplicationNo()).isEqualTo(UPDATED_FDA_APPLICATION_NO);
        assertThat(testApplicationInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicationInfo.getGenericName()).isEqualTo(UPDATED_GENERIC_NAME);
        assertThat(testApplicationInfo.getBrandName()).isEqualTo(UPDATED_BRAND_NAME);
        assertThat(testApplicationInfo.getOverView()).isEqualTo(UPDATED_OVER_VIEW);
        assertThat(testApplicationInfo.getStrengthAmount()).isEqualTo(UPDATED_STRENGTH_AMOUNT);
        assertThat(testApplicationInfo.getStrengthUnit()).isEqualTo(UPDATED_STRENGTH_UNIT);
        assertThat(testApplicationInfo.getProductNumber()).isEqualTo(UPDATED_PRODUCT_NUMBER);
        assertThat(testApplicationInfo.getActiveIngredient()).isEqualTo(UPDATED_ACTIVE_INGREDIENT);
        assertThat(testApplicationInfo.getForm()).isEqualTo(UPDATED_FORM);
        assertThat(testApplicationInfo.getRoutsOfAdministration()).isEqualTo(UPDATED_ROUTS_OF_ADMINISTRATION);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationInfo() throws Exception {
        int databaseSizeBeforeUpdate = applicationInfoRepository.findAll().size();

        // Create the ApplicationInfo
        ApplicationInfoDTO applicationInfoDTO = applicationInfoMapper.toDto(applicationInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationInfoMockMvc.perform(put("/api/application-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationInfo in the database
        List<ApplicationInfo> applicationInfoList = applicationInfoRepository.findAll();
        assertThat(applicationInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationInfo() throws Exception {
        // Initialize the database
        applicationInfoRepository.saveAndFlush(applicationInfo);

        int databaseSizeBeforeDelete = applicationInfoRepository.findAll().size();

        // Delete the applicationInfo
        restApplicationInfoMockMvc.perform(delete("/api/application-infos/{id}", applicationInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationInfo> applicationInfoList = applicationInfoRepository.findAll();
        assertThat(applicationInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationInfo.class);
        ApplicationInfo applicationInfo1 = new ApplicationInfo();
        applicationInfo1.setId(1L);
        ApplicationInfo applicationInfo2 = new ApplicationInfo();
        applicationInfo2.setId(applicationInfo1.getId());
        assertThat(applicationInfo1).isEqualTo(applicationInfo2);
        applicationInfo2.setId(2L);
        assertThat(applicationInfo1).isNotEqualTo(applicationInfo2);
        applicationInfo1.setId(null);
        assertThat(applicationInfo1).isNotEqualTo(applicationInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationInfoDTO.class);
        ApplicationInfoDTO applicationInfoDTO1 = new ApplicationInfoDTO();
        applicationInfoDTO1.setId(1L);
        ApplicationInfoDTO applicationInfoDTO2 = new ApplicationInfoDTO();
        assertThat(applicationInfoDTO1).isNotEqualTo(applicationInfoDTO2);
        applicationInfoDTO2.setId(applicationInfoDTO1.getId());
        assertThat(applicationInfoDTO1).isEqualTo(applicationInfoDTO2);
        applicationInfoDTO2.setId(2L);
        assertThat(applicationInfoDTO1).isNotEqualTo(applicationInfoDTO2);
        applicationInfoDTO1.setId(null);
        assertThat(applicationInfoDTO1).isNotEqualTo(applicationInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationInfoMapper.fromId(null)).isNull();
    }
}
