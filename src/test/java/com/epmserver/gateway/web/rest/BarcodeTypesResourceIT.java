package com.epmserver.gateway.web.rest;

import com.epmserver.gateway.EpmgatewayApp;
import com.epmserver.gateway.domain.BarcodeTypes;
import com.epmserver.gateway.repository.BarcodeTypesRepository;
import com.epmserver.gateway.repository.search.BarcodeTypesSearchRepository;
import com.epmserver.gateway.service.BarcodeTypesService;
import com.epmserver.gateway.service.dto.BarcodeTypesDTO;
import com.epmserver.gateway.service.mapper.BarcodeTypesMapper;
import com.epmserver.gateway.web.rest.errors.ExceptionTranslator;
import com.epmserver.gateway.service.dto.BarcodeTypesCriteria;
import com.epmserver.gateway.service.BarcodeTypesQueryService;

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
import java.util.Collections;
import java.util.List;

import static com.epmserver.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BarcodeTypesResource} REST controller.
 */
@SpringBootTest(classes = EpmgatewayApp.class)
public class BarcodeTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BarcodeTypesRepository barcodeTypesRepository;

    @Autowired
    private BarcodeTypesMapper barcodeTypesMapper;

    @Autowired
    private BarcodeTypesService barcodeTypesService;

    /**
     * This repository is mocked in the com.epmserver.gateway.repository.search test package.
     *
     * @see com.epmserver.gateway.repository.search.BarcodeTypesSearchRepositoryMockConfiguration
     */
    @Autowired
    private BarcodeTypesSearchRepository mockBarcodeTypesSearchRepository;

    @Autowired
    private BarcodeTypesQueryService barcodeTypesQueryService;

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

    private MockMvc restBarcodeTypesMockMvc;

    private BarcodeTypes barcodeTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BarcodeTypesResource barcodeTypesResource = new BarcodeTypesResource(barcodeTypesService, barcodeTypesQueryService);
        this.restBarcodeTypesMockMvc = MockMvcBuilders.standaloneSetup(barcodeTypesResource)
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
    public static BarcodeTypes createEntity(EntityManager em) {
        BarcodeTypes barcodeTypes = new BarcodeTypes()
            .name(DEFAULT_NAME);
        return barcodeTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BarcodeTypes createUpdatedEntity(EntityManager em) {
        BarcodeTypes barcodeTypes = new BarcodeTypes()
            .name(UPDATED_NAME);
        return barcodeTypes;
    }

    @BeforeEach
    public void initTest() {
        barcodeTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createBarcodeTypes() throws Exception {
        int databaseSizeBeforeCreate = barcodeTypesRepository.findAll().size();

        // Create the BarcodeTypes
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(barcodeTypes);
        restBarcodeTypesMockMvc.perform(post("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the BarcodeTypes in the database
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeCreate + 1);
        BarcodeTypes testBarcodeTypes = barcodeTypesList.get(barcodeTypesList.size() - 1);
        assertThat(testBarcodeTypes.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the BarcodeTypes in Elasticsearch
        verify(mockBarcodeTypesSearchRepository, times(1)).save(testBarcodeTypes);
    }

    @Test
    @Transactional
    public void createBarcodeTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = barcodeTypesRepository.findAll().size();

        // Create the BarcodeTypes with an existing ID
        barcodeTypes.setId(1L);
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(barcodeTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBarcodeTypesMockMvc.perform(post("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BarcodeTypes in the database
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeCreate);

        // Validate the BarcodeTypes in Elasticsearch
        verify(mockBarcodeTypesSearchRepository, times(0)).save(barcodeTypes);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = barcodeTypesRepository.findAll().size();
        // set the field null
        barcodeTypes.setName(null);

        // Create the BarcodeTypes, which fails.
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(barcodeTypes);

        restBarcodeTypesMockMvc.perform(post("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isBadRequest());

        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barcodeTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get the barcodeTypes
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types/{id}", barcodeTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(barcodeTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where name equals to DEFAULT_NAME
        defaultBarcodeTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the barcodeTypesList where name equals to UPDATED_NAME
        defaultBarcodeTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where name not equals to DEFAULT_NAME
        defaultBarcodeTypesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the barcodeTypesList where name not equals to UPDATED_NAME
        defaultBarcodeTypesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBarcodeTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the barcodeTypesList where name equals to UPDATED_NAME
        defaultBarcodeTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where name is not null
        defaultBarcodeTypesShouldBeFound("name.specified=true");

        // Get all the barcodeTypesList where name is null
        defaultBarcodeTypesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllBarcodeTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where name contains DEFAULT_NAME
        defaultBarcodeTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the barcodeTypesList where name contains UPDATED_NAME
        defaultBarcodeTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where name does not contain DEFAULT_NAME
        defaultBarcodeTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the barcodeTypesList where name does not contain UPDATED_NAME
        defaultBarcodeTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBarcodeTypesShouldBeFound(String filter) throws Exception {
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barcodeTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBarcodeTypesShouldNotBeFound(String filter) throws Exception {
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBarcodeTypes() throws Exception {
        // Get the barcodeTypes
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        int databaseSizeBeforeUpdate = barcodeTypesRepository.findAll().size();

        // Update the barcodeTypes
        BarcodeTypes updatedBarcodeTypes = barcodeTypesRepository.findById(barcodeTypes.getId()).get();
        // Disconnect from session so that the updates on updatedBarcodeTypes are not directly saved in db
        em.detach(updatedBarcodeTypes);
        updatedBarcodeTypes
            .name(UPDATED_NAME);
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(updatedBarcodeTypes);

        restBarcodeTypesMockMvc.perform(put("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isOk());

        // Validate the BarcodeTypes in the database
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeUpdate);
        BarcodeTypes testBarcodeTypes = barcodeTypesList.get(barcodeTypesList.size() - 1);
        assertThat(testBarcodeTypes.getName()).isEqualTo(UPDATED_NAME);

        // Validate the BarcodeTypes in Elasticsearch
        verify(mockBarcodeTypesSearchRepository, times(1)).save(testBarcodeTypes);
    }

    @Test
    @Transactional
    public void updateNonExistingBarcodeTypes() throws Exception {
        int databaseSizeBeforeUpdate = barcodeTypesRepository.findAll().size();

        // Create the BarcodeTypes
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(barcodeTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBarcodeTypesMockMvc.perform(put("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BarcodeTypes in the database
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BarcodeTypes in Elasticsearch
        verify(mockBarcodeTypesSearchRepository, times(0)).save(barcodeTypes);
    }

    @Test
    @Transactional
    public void deleteBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        int databaseSizeBeforeDelete = barcodeTypesRepository.findAll().size();

        // Delete the barcodeTypes
        restBarcodeTypesMockMvc.perform(delete("/api/barcode-types/{id}", barcodeTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BarcodeTypes in Elasticsearch
        verify(mockBarcodeTypesSearchRepository, times(1)).deleteById(barcodeTypes.getId());
    }

    @Test
    @Transactional
    public void searchBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);
        when(mockBarcodeTypesSearchRepository.search(queryStringQuery("id:" + barcodeTypes.getId())))
            .thenReturn(Collections.singletonList(barcodeTypes));
        // Search the barcodeTypes
        restBarcodeTypesMockMvc.perform(get("/api/_search/barcode-types?query=id:" + barcodeTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barcodeTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BarcodeTypes.class);
        BarcodeTypes barcodeTypes1 = new BarcodeTypes();
        barcodeTypes1.setId(1L);
        BarcodeTypes barcodeTypes2 = new BarcodeTypes();
        barcodeTypes2.setId(barcodeTypes1.getId());
        assertThat(barcodeTypes1).isEqualTo(barcodeTypes2);
        barcodeTypes2.setId(2L);
        assertThat(barcodeTypes1).isNotEqualTo(barcodeTypes2);
        barcodeTypes1.setId(null);
        assertThat(barcodeTypes1).isNotEqualTo(barcodeTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BarcodeTypesDTO.class);
        BarcodeTypesDTO barcodeTypesDTO1 = new BarcodeTypesDTO();
        barcodeTypesDTO1.setId(1L);
        BarcodeTypesDTO barcodeTypesDTO2 = new BarcodeTypesDTO();
        assertThat(barcodeTypesDTO1).isNotEqualTo(barcodeTypesDTO2);
        barcodeTypesDTO2.setId(barcodeTypesDTO1.getId());
        assertThat(barcodeTypesDTO1).isEqualTo(barcodeTypesDTO2);
        barcodeTypesDTO2.setId(2L);
        assertThat(barcodeTypesDTO1).isNotEqualTo(barcodeTypesDTO2);
        barcodeTypesDTO1.setId(null);
        assertThat(barcodeTypesDTO1).isNotEqualTo(barcodeTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(barcodeTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(barcodeTypesMapper.fromId(null)).isNull();
    }
}
