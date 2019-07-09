package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.PillpalApp;
import com.medicai.pillpal.domain.Interaction;
import com.medicai.pillpal.domain.enumeration.RecommendationType;
import com.medicai.pillpal.repository.InteractionRepository;
import com.medicai.pillpal.service.InteractionService;
import com.medicai.pillpal.service.dto.InteractionDTO;
import com.medicai.pillpal.service.mapper.InteractionMapper;
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
 * Integration tests for the {@Link InteractionResource} REST controller.
 */
@SpringBootTest(classes = PillpalApp.class)
public class InteractionResourceIT {

    private static final RecommendationType DEFAULT_RECOMMENDATION_TYPE = RecommendationType.NOT_RECOMMENDED;
    private static final RecommendationType UPDATED_RECOMMENDATION_TYPE = RecommendationType.USUALLY_NOT_RECOMMENDED;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private InteractionRepository interactionRepository;

    @Autowired
    private InteractionMapper interactionMapper;

    @Autowired
    private InteractionService interactionService;

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

    private MockMvc restInteractionMockMvc;

    private Interaction interaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InteractionResource interactionResource = new InteractionResource(interactionService);
        this.restInteractionMockMvc = MockMvcBuilders.standaloneSetup(interactionResource)
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
    public static Interaction createEntity(EntityManager em) {
        Interaction interaction = new Interaction()
            .recommendationType(DEFAULT_RECOMMENDATION_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return interaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interaction createUpdatedEntity(EntityManager em) {
        Interaction interaction = new Interaction()
            .recommendationType(UPDATED_RECOMMENDATION_TYPE)
            .description(UPDATED_DESCRIPTION);
        return interaction;
    }

    @BeforeEach
    public void initTest() {
        interaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createInteraction() throws Exception {
        int databaseSizeBeforeCreate = interactionRepository.findAll().size();

        // Create the Interaction
        InteractionDTO interactionDTO = interactionMapper.toDto(interaction);
        restInteractionMockMvc.perform(post("/api/interactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interactionDTO)))
            .andExpect(status().isCreated());

        // Validate the Interaction in the database
        List<Interaction> interactionList = interactionRepository.findAll();
        assertThat(interactionList).hasSize(databaseSizeBeforeCreate + 1);
        Interaction testInteraction = interactionList.get(interactionList.size() - 1);
        assertThat(testInteraction.getRecommendationType()).isEqualTo(DEFAULT_RECOMMENDATION_TYPE);
        assertThat(testInteraction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createInteractionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interactionRepository.findAll().size();

        // Create the Interaction with an existing ID
        interaction.setId(1L);
        InteractionDTO interactionDTO = interactionMapper.toDto(interaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInteractionMockMvc.perform(post("/api/interactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Interaction in the database
        List<Interaction> interactionList = interactionRepository.findAll();
        assertThat(interactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRecommendationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = interactionRepository.findAll().size();
        // set the field null
        interaction.setRecommendationType(null);

        // Create the Interaction, which fails.
        InteractionDTO interactionDTO = interactionMapper.toDto(interaction);

        restInteractionMockMvc.perform(post("/api/interactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interactionDTO)))
            .andExpect(status().isBadRequest());

        List<Interaction> interactionList = interactionRepository.findAll();
        assertThat(interactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInteractions() throws Exception {
        // Initialize the database
        interactionRepository.saveAndFlush(interaction);

        // Get all the interactionList
        restInteractionMockMvc.perform(get("/api/interactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].recommendationType").value(hasItem(DEFAULT_RECOMMENDATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getInteraction() throws Exception {
        // Initialize the database
        interactionRepository.saveAndFlush(interaction);

        // Get the interaction
        restInteractionMockMvc.perform(get("/api/interactions/{id}", interaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(interaction.getId().intValue()))
            .andExpect(jsonPath("$.recommendationType").value(DEFAULT_RECOMMENDATION_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInteraction() throws Exception {
        // Get the interaction
        restInteractionMockMvc.perform(get("/api/interactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInteraction() throws Exception {
        // Initialize the database
        interactionRepository.saveAndFlush(interaction);

        int databaseSizeBeforeUpdate = interactionRepository.findAll().size();

        // Update the interaction
        Interaction updatedInteraction = interactionRepository.findById(interaction.getId()).get();
        // Disconnect from session so that the updates on updatedInteraction are not directly saved in db
        em.detach(updatedInteraction);
        updatedInteraction
            .recommendationType(UPDATED_RECOMMENDATION_TYPE)
            .description(UPDATED_DESCRIPTION);
        InteractionDTO interactionDTO = interactionMapper.toDto(updatedInteraction);

        restInteractionMockMvc.perform(put("/api/interactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interactionDTO)))
            .andExpect(status().isOk());

        // Validate the Interaction in the database
        List<Interaction> interactionList = interactionRepository.findAll();
        assertThat(interactionList).hasSize(databaseSizeBeforeUpdate);
        Interaction testInteraction = interactionList.get(interactionList.size() - 1);
        assertThat(testInteraction.getRecommendationType()).isEqualTo(UPDATED_RECOMMENDATION_TYPE);
        assertThat(testInteraction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingInteraction() throws Exception {
        int databaseSizeBeforeUpdate = interactionRepository.findAll().size();

        // Create the Interaction
        InteractionDTO interactionDTO = interactionMapper.toDto(interaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInteractionMockMvc.perform(put("/api/interactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Interaction in the database
        List<Interaction> interactionList = interactionRepository.findAll();
        assertThat(interactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInteraction() throws Exception {
        // Initialize the database
        interactionRepository.saveAndFlush(interaction);

        int databaseSizeBeforeDelete = interactionRepository.findAll().size();

        // Delete the interaction
        restInteractionMockMvc.perform(delete("/api/interactions/{id}", interaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Interaction> interactionList = interactionRepository.findAll();
        assertThat(interactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Interaction.class);
        Interaction interaction1 = new Interaction();
        interaction1.setId(1L);
        Interaction interaction2 = new Interaction();
        interaction2.setId(interaction1.getId());
        assertThat(interaction1).isEqualTo(interaction2);
        interaction2.setId(2L);
        assertThat(interaction1).isNotEqualTo(interaction2);
        interaction1.setId(null);
        assertThat(interaction1).isNotEqualTo(interaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InteractionDTO.class);
        InteractionDTO interactionDTO1 = new InteractionDTO();
        interactionDTO1.setId(1L);
        InteractionDTO interactionDTO2 = new InteractionDTO();
        assertThat(interactionDTO1).isNotEqualTo(interactionDTO2);
        interactionDTO2.setId(interactionDTO1.getId());
        assertThat(interactionDTO1).isEqualTo(interactionDTO2);
        interactionDTO2.setId(2L);
        assertThat(interactionDTO1).isNotEqualTo(interactionDTO2);
        interactionDTO1.setId(null);
        assertThat(interactionDTO1).isNotEqualTo(interactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(interactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(interactionMapper.fromId(null)).isNull();
    }
}
