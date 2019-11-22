package com.epmserver.gateway.web.rest;

import com.epmserver.gateway.EpmgatewayApp;
import com.epmserver.gateway.domain.ProductDocument;
import com.epmserver.gateway.domain.WarrantyTypes;
import com.epmserver.gateway.domain.Culture;
import com.epmserver.gateway.repository.ProductDocumentRepository;
import com.epmserver.gateway.repository.search.ProductDocumentSearchRepository;
import com.epmserver.gateway.service.ProductDocumentService;
import com.epmserver.gateway.service.dto.ProductDocumentDTO;
import com.epmserver.gateway.service.mapper.ProductDocumentMapper;
import com.epmserver.gateway.web.rest.errors.ExceptionTranslator;
import com.epmserver.gateway.service.dto.ProductDocumentCriteria;
import com.epmserver.gateway.service.ProductDocumentQueryService;

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
 * Integration tests for the {@link ProductDocumentResource} REST controller.
 */
@SpringBootTest(classes = EpmgatewayApp.class)
public class ProductDocumentResourceIT {

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_HIGHLIGHTS_URL = "AAAAAAAAAA";
    private static final String UPDATED_HIGHLIGHTS_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION_URL = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DESCRIPTION_URL = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_URL = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CARE_INSTRUCTIONS_URL = "AAAAAAAAAA";
    private static final String UPDATED_CARE_INSTRUCTIONS_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIAL_FEATURES_URL = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_FEATURES_URL = "BBBBBBBBBB";

    private static final String DEFAULT_USAGE_AND_SIDE_EFFECTS_URL = "AAAAAAAAAA";
    private static final String UPDATED_USAGE_AND_SIDE_EFFECTS_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SAFETY_WARNNING_URL = "AAAAAAAAAA";
    private static final String UPDATED_SAFETY_WARNNING_URL = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FABRIC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FABRIC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GENUINE_AND_LEGAL = false;
    private static final Boolean UPDATED_GENUINE_AND_LEGAL = true;

    private static final String DEFAULT_COUNTRY_OF_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_OF_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_WARRANTY_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_WARRANTY_POLICY = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_POLICY = "BBBBBBBBBB";

    @Autowired
    private ProductDocumentRepository productDocumentRepository;

    @Autowired
    private ProductDocumentMapper productDocumentMapper;

    @Autowired
    private ProductDocumentService productDocumentService;

    /**
     * This repository is mocked in the com.epmserver.gateway.repository.search test package.
     *
     * @see com.epmserver.gateway.repository.search.ProductDocumentSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductDocumentSearchRepository mockProductDocumentSearchRepository;

    @Autowired
    private ProductDocumentQueryService productDocumentQueryService;

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

    private MockMvc restProductDocumentMockMvc;

    private ProductDocument productDocument;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductDocumentResource productDocumentResource = new ProductDocumentResource(productDocumentService, productDocumentQueryService);
        this.restProductDocumentMockMvc = MockMvcBuilders.standaloneSetup(productDocumentResource)
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
    public static ProductDocument createEntity(EntityManager em) {
        ProductDocument productDocument = new ProductDocument()
            .videoUrl(DEFAULT_VIDEO_URL)
            .highlightsUrl(DEFAULT_HIGHLIGHTS_URL)
            .longDescriptionUrl(DEFAULT_LONG_DESCRIPTION_URL)
            .shortDescriptionUrl(DEFAULT_SHORT_DESCRIPTION_URL)
            .descriptionUrl(DEFAULT_DESCRIPTION_URL)
            .careInstructionsUrl(DEFAULT_CARE_INSTRUCTIONS_URL)
            .specialFeaturesUrl(DEFAULT_SPECIAL_FEATURES_URL)
            .usageAndSideEffectsUrl(DEFAULT_USAGE_AND_SIDE_EFFECTS_URL)
            .safetyWarnningUrl(DEFAULT_SAFETY_WARNNING_URL)
            .productType(DEFAULT_PRODUCT_TYPE)
            .modelName(DEFAULT_MODEL_NAME)
            .modelNumber(DEFAULT_MODEL_NUMBER)
            .fabricType(DEFAULT_FABRIC_TYPE)
            .productComplianceCertificate(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(DEFAULT_GENUINE_AND_LEGAL)
            .countryOfOrigin(DEFAULT_COUNTRY_OF_ORIGIN)
            .warrantyPeriod(DEFAULT_WARRANTY_PERIOD)
            .warrantyPolicy(DEFAULT_WARRANTY_POLICY);
        return productDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDocument createUpdatedEntity(EntityManager em) {
        ProductDocument productDocument = new ProductDocument()
            .videoUrl(UPDATED_VIDEO_URL)
            .highlightsUrl(UPDATED_HIGHLIGHTS_URL)
            .longDescriptionUrl(UPDATED_LONG_DESCRIPTION_URL)
            .shortDescriptionUrl(UPDATED_SHORT_DESCRIPTION_URL)
            .descriptionUrl(UPDATED_DESCRIPTION_URL)
            .careInstructionsUrl(UPDATED_CARE_INSTRUCTIONS_URL)
            .specialFeaturesUrl(UPDATED_SPECIAL_FEATURES_URL)
            .usageAndSideEffectsUrl(UPDATED_USAGE_AND_SIDE_EFFECTS_URL)
            .safetyWarnningUrl(UPDATED_SAFETY_WARNNING_URL)
            .productType(UPDATED_PRODUCT_TYPE)
            .modelName(UPDATED_MODEL_NAME)
            .modelNumber(UPDATED_MODEL_NUMBER)
            .fabricType(UPDATED_FABRIC_TYPE)
            .productComplianceCertificate(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(UPDATED_GENUINE_AND_LEGAL)
            .countryOfOrigin(UPDATED_COUNTRY_OF_ORIGIN)
            .warrantyPeriod(UPDATED_WARRANTY_PERIOD)
            .warrantyPolicy(UPDATED_WARRANTY_POLICY);
        return productDocument;
    }

    @BeforeEach
    public void initTest() {
        productDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductDocument() throws Exception {
        int databaseSizeBeforeCreate = productDocumentRepository.findAll().size();

        // Create the ProductDocument
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(productDocument);
        restProductDocumentMockMvc.perform(post("/api/product-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDocument testProductDocument = productDocumentList.get(productDocumentList.size() - 1);
        assertThat(testProductDocument.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testProductDocument.getHighlightsUrl()).isEqualTo(DEFAULT_HIGHLIGHTS_URL);
        assertThat(testProductDocument.getLongDescriptionUrl()).isEqualTo(DEFAULT_LONG_DESCRIPTION_URL);
        assertThat(testProductDocument.getShortDescriptionUrl()).isEqualTo(DEFAULT_SHORT_DESCRIPTION_URL);
        assertThat(testProductDocument.getDescriptionUrl()).isEqualTo(DEFAULT_DESCRIPTION_URL);
        assertThat(testProductDocument.getCareInstructionsUrl()).isEqualTo(DEFAULT_CARE_INSTRUCTIONS_URL);
        assertThat(testProductDocument.getSpecialFeaturesUrl()).isEqualTo(DEFAULT_SPECIAL_FEATURES_URL);
        assertThat(testProductDocument.getUsageAndSideEffectsUrl()).isEqualTo(DEFAULT_USAGE_AND_SIDE_EFFECTS_URL);
        assertThat(testProductDocument.getSafetyWarnningUrl()).isEqualTo(DEFAULT_SAFETY_WARNNING_URL);
        assertThat(testProductDocument.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testProductDocument.getModelName()).isEqualTo(DEFAULT_MODEL_NAME);
        assertThat(testProductDocument.getModelNumber()).isEqualTo(DEFAULT_MODEL_NUMBER);
        assertThat(testProductDocument.getFabricType()).isEqualTo(DEFAULT_FABRIC_TYPE);
        assertThat(testProductDocument.getProductComplianceCertificate()).isEqualTo(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);
        assertThat(testProductDocument.isGenuineAndLegal()).isEqualTo(DEFAULT_GENUINE_AND_LEGAL);
        assertThat(testProductDocument.getCountryOfOrigin()).isEqualTo(DEFAULT_COUNTRY_OF_ORIGIN);
        assertThat(testProductDocument.getWarrantyPeriod()).isEqualTo(DEFAULT_WARRANTY_PERIOD);
        assertThat(testProductDocument.getWarrantyPolicy()).isEqualTo(DEFAULT_WARRANTY_POLICY);

        // Validate the ProductDocument in Elasticsearch
        verify(mockProductDocumentSearchRepository, times(1)).save(testProductDocument);
    }

    @Test
    @Transactional
    public void createProductDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productDocumentRepository.findAll().size();

        // Create the ProductDocument with an existing ID
        productDocument.setId(1L);
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(productDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDocumentMockMvc.perform(post("/api/product-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductDocument in Elasticsearch
        verify(mockProductDocumentSearchRepository, times(0)).save(productDocument);
    }


    @Test
    @Transactional
    public void getAllProductDocuments() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList
        restProductDocumentMockMvc.perform(get("/api/product-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].highlightsUrl").value(hasItem(DEFAULT_HIGHLIGHTS_URL)))
            .andExpect(jsonPath("$.[*].longDescriptionUrl").value(hasItem(DEFAULT_LONG_DESCRIPTION_URL)))
            .andExpect(jsonPath("$.[*].shortDescriptionUrl").value(hasItem(DEFAULT_SHORT_DESCRIPTION_URL)))
            .andExpect(jsonPath("$.[*].descriptionUrl").value(hasItem(DEFAULT_DESCRIPTION_URL)))
            .andExpect(jsonPath("$.[*].careInstructionsUrl").value(hasItem(DEFAULT_CARE_INSTRUCTIONS_URL)))
            .andExpect(jsonPath("$.[*].specialFeaturesUrl").value(hasItem(DEFAULT_SPECIAL_FEATURES_URL)))
            .andExpect(jsonPath("$.[*].usageAndSideEffectsUrl").value(hasItem(DEFAULT_USAGE_AND_SIDE_EFFECTS_URL)))
            .andExpect(jsonPath("$.[*].safetyWarnningUrl").value(hasItem(DEFAULT_SAFETY_WARNNING_URL)))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE)))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME)))
            .andExpect(jsonPath("$.[*].modelNumber").value(hasItem(DEFAULT_MODEL_NUMBER)))
            .andExpect(jsonPath("$.[*].fabricType").value(hasItem(DEFAULT_FABRIC_TYPE)))
            .andExpect(jsonPath("$.[*].productComplianceCertificate").value(hasItem(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE)))
            .andExpect(jsonPath("$.[*].genuineAndLegal").value(hasItem(DEFAULT_GENUINE_AND_LEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].countryOfOrigin").value(hasItem(DEFAULT_COUNTRY_OF_ORIGIN)))
            .andExpect(jsonPath("$.[*].warrantyPeriod").value(hasItem(DEFAULT_WARRANTY_PERIOD)))
            .andExpect(jsonPath("$.[*].warrantyPolicy").value(hasItem(DEFAULT_WARRANTY_POLICY)));
    }
    
    @Test
    @Transactional
    public void getProductDocument() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get the productDocument
        restProductDocumentMockMvc.perform(get("/api/product-documents/{id}", productDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productDocument.getId().intValue()))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL))
            .andExpect(jsonPath("$.highlightsUrl").value(DEFAULT_HIGHLIGHTS_URL))
            .andExpect(jsonPath("$.longDescriptionUrl").value(DEFAULT_LONG_DESCRIPTION_URL))
            .andExpect(jsonPath("$.shortDescriptionUrl").value(DEFAULT_SHORT_DESCRIPTION_URL))
            .andExpect(jsonPath("$.descriptionUrl").value(DEFAULT_DESCRIPTION_URL))
            .andExpect(jsonPath("$.careInstructionsUrl").value(DEFAULT_CARE_INSTRUCTIONS_URL))
            .andExpect(jsonPath("$.specialFeaturesUrl").value(DEFAULT_SPECIAL_FEATURES_URL))
            .andExpect(jsonPath("$.usageAndSideEffectsUrl").value(DEFAULT_USAGE_AND_SIDE_EFFECTS_URL))
            .andExpect(jsonPath("$.safetyWarnningUrl").value(DEFAULT_SAFETY_WARNNING_URL))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE))
            .andExpect(jsonPath("$.modelName").value(DEFAULT_MODEL_NAME))
            .andExpect(jsonPath("$.modelNumber").value(DEFAULT_MODEL_NUMBER))
            .andExpect(jsonPath("$.fabricType").value(DEFAULT_FABRIC_TYPE))
            .andExpect(jsonPath("$.productComplianceCertificate").value(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE))
            .andExpect(jsonPath("$.genuineAndLegal").value(DEFAULT_GENUINE_AND_LEGAL.booleanValue()))
            .andExpect(jsonPath("$.countryOfOrigin").value(DEFAULT_COUNTRY_OF_ORIGIN))
            .andExpect(jsonPath("$.warrantyPeriod").value(DEFAULT_WARRANTY_PERIOD))
            .andExpect(jsonPath("$.warrantyPolicy").value(DEFAULT_WARRANTY_POLICY));
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where videoUrl equals to DEFAULT_VIDEO_URL
        defaultProductDocumentShouldBeFound("videoUrl.equals=" + DEFAULT_VIDEO_URL);

        // Get all the productDocumentList where videoUrl equals to UPDATED_VIDEO_URL
        defaultProductDocumentShouldNotBeFound("videoUrl.equals=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where videoUrl not equals to DEFAULT_VIDEO_URL
        defaultProductDocumentShouldNotBeFound("videoUrl.notEquals=" + DEFAULT_VIDEO_URL);

        // Get all the productDocumentList where videoUrl not equals to UPDATED_VIDEO_URL
        defaultProductDocumentShouldBeFound("videoUrl.notEquals=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where videoUrl in DEFAULT_VIDEO_URL or UPDATED_VIDEO_URL
        defaultProductDocumentShouldBeFound("videoUrl.in=" + DEFAULT_VIDEO_URL + "," + UPDATED_VIDEO_URL);

        // Get all the productDocumentList where videoUrl equals to UPDATED_VIDEO_URL
        defaultProductDocumentShouldNotBeFound("videoUrl.in=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where videoUrl is not null
        defaultProductDocumentShouldBeFound("videoUrl.specified=true");

        // Get all the productDocumentList where videoUrl is null
        defaultProductDocumentShouldNotBeFound("videoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where videoUrl contains DEFAULT_VIDEO_URL
        defaultProductDocumentShouldBeFound("videoUrl.contains=" + DEFAULT_VIDEO_URL);

        // Get all the productDocumentList where videoUrl contains UPDATED_VIDEO_URL
        defaultProductDocumentShouldNotBeFound("videoUrl.contains=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where videoUrl does not contain DEFAULT_VIDEO_URL
        defaultProductDocumentShouldNotBeFound("videoUrl.doesNotContain=" + DEFAULT_VIDEO_URL);

        // Get all the productDocumentList where videoUrl does not contain UPDATED_VIDEO_URL
        defaultProductDocumentShouldBeFound("videoUrl.doesNotContain=" + UPDATED_VIDEO_URL);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByHighlightsUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where highlightsUrl equals to DEFAULT_HIGHLIGHTS_URL
        defaultProductDocumentShouldBeFound("highlightsUrl.equals=" + DEFAULT_HIGHLIGHTS_URL);

        // Get all the productDocumentList where highlightsUrl equals to UPDATED_HIGHLIGHTS_URL
        defaultProductDocumentShouldNotBeFound("highlightsUrl.equals=" + UPDATED_HIGHLIGHTS_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByHighlightsUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where highlightsUrl not equals to DEFAULT_HIGHLIGHTS_URL
        defaultProductDocumentShouldNotBeFound("highlightsUrl.notEquals=" + DEFAULT_HIGHLIGHTS_URL);

        // Get all the productDocumentList where highlightsUrl not equals to UPDATED_HIGHLIGHTS_URL
        defaultProductDocumentShouldBeFound("highlightsUrl.notEquals=" + UPDATED_HIGHLIGHTS_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByHighlightsUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where highlightsUrl in DEFAULT_HIGHLIGHTS_URL or UPDATED_HIGHLIGHTS_URL
        defaultProductDocumentShouldBeFound("highlightsUrl.in=" + DEFAULT_HIGHLIGHTS_URL + "," + UPDATED_HIGHLIGHTS_URL);

        // Get all the productDocumentList where highlightsUrl equals to UPDATED_HIGHLIGHTS_URL
        defaultProductDocumentShouldNotBeFound("highlightsUrl.in=" + UPDATED_HIGHLIGHTS_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByHighlightsUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where highlightsUrl is not null
        defaultProductDocumentShouldBeFound("highlightsUrl.specified=true");

        // Get all the productDocumentList where highlightsUrl is null
        defaultProductDocumentShouldNotBeFound("highlightsUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByHighlightsUrlContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where highlightsUrl contains DEFAULT_HIGHLIGHTS_URL
        defaultProductDocumentShouldBeFound("highlightsUrl.contains=" + DEFAULT_HIGHLIGHTS_URL);

        // Get all the productDocumentList where highlightsUrl contains UPDATED_HIGHLIGHTS_URL
        defaultProductDocumentShouldNotBeFound("highlightsUrl.contains=" + UPDATED_HIGHLIGHTS_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByHighlightsUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where highlightsUrl does not contain DEFAULT_HIGHLIGHTS_URL
        defaultProductDocumentShouldNotBeFound("highlightsUrl.doesNotContain=" + DEFAULT_HIGHLIGHTS_URL);

        // Get all the productDocumentList where highlightsUrl does not contain UPDATED_HIGHLIGHTS_URL
        defaultProductDocumentShouldBeFound("highlightsUrl.doesNotContain=" + UPDATED_HIGHLIGHTS_URL);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByLongDescriptionUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where longDescriptionUrl equals to DEFAULT_LONG_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("longDescriptionUrl.equals=" + DEFAULT_LONG_DESCRIPTION_URL);

        // Get all the productDocumentList where longDescriptionUrl equals to UPDATED_LONG_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("longDescriptionUrl.equals=" + UPDATED_LONG_DESCRIPTION_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLongDescriptionUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where longDescriptionUrl not equals to DEFAULT_LONG_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("longDescriptionUrl.notEquals=" + DEFAULT_LONG_DESCRIPTION_URL);

        // Get all the productDocumentList where longDescriptionUrl not equals to UPDATED_LONG_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("longDescriptionUrl.notEquals=" + UPDATED_LONG_DESCRIPTION_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLongDescriptionUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where longDescriptionUrl in DEFAULT_LONG_DESCRIPTION_URL or UPDATED_LONG_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("longDescriptionUrl.in=" + DEFAULT_LONG_DESCRIPTION_URL + "," + UPDATED_LONG_DESCRIPTION_URL);

        // Get all the productDocumentList where longDescriptionUrl equals to UPDATED_LONG_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("longDescriptionUrl.in=" + UPDATED_LONG_DESCRIPTION_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLongDescriptionUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where longDescriptionUrl is not null
        defaultProductDocumentShouldBeFound("longDescriptionUrl.specified=true");

        // Get all the productDocumentList where longDescriptionUrl is null
        defaultProductDocumentShouldNotBeFound("longDescriptionUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByLongDescriptionUrlContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where longDescriptionUrl contains DEFAULT_LONG_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("longDescriptionUrl.contains=" + DEFAULT_LONG_DESCRIPTION_URL);

        // Get all the productDocumentList where longDescriptionUrl contains UPDATED_LONG_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("longDescriptionUrl.contains=" + UPDATED_LONG_DESCRIPTION_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLongDescriptionUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where longDescriptionUrl does not contain DEFAULT_LONG_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("longDescriptionUrl.doesNotContain=" + DEFAULT_LONG_DESCRIPTION_URL);

        // Get all the productDocumentList where longDescriptionUrl does not contain UPDATED_LONG_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("longDescriptionUrl.doesNotContain=" + UPDATED_LONG_DESCRIPTION_URL);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByShortDescriptionUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where shortDescriptionUrl equals to DEFAULT_SHORT_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("shortDescriptionUrl.equals=" + DEFAULT_SHORT_DESCRIPTION_URL);

        // Get all the productDocumentList where shortDescriptionUrl equals to UPDATED_SHORT_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("shortDescriptionUrl.equals=" + UPDATED_SHORT_DESCRIPTION_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByShortDescriptionUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where shortDescriptionUrl not equals to DEFAULT_SHORT_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("shortDescriptionUrl.notEquals=" + DEFAULT_SHORT_DESCRIPTION_URL);

        // Get all the productDocumentList where shortDescriptionUrl not equals to UPDATED_SHORT_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("shortDescriptionUrl.notEquals=" + UPDATED_SHORT_DESCRIPTION_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByShortDescriptionUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where shortDescriptionUrl in DEFAULT_SHORT_DESCRIPTION_URL or UPDATED_SHORT_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("shortDescriptionUrl.in=" + DEFAULT_SHORT_DESCRIPTION_URL + "," + UPDATED_SHORT_DESCRIPTION_URL);

        // Get all the productDocumentList where shortDescriptionUrl equals to UPDATED_SHORT_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("shortDescriptionUrl.in=" + UPDATED_SHORT_DESCRIPTION_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByShortDescriptionUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where shortDescriptionUrl is not null
        defaultProductDocumentShouldBeFound("shortDescriptionUrl.specified=true");

        // Get all the productDocumentList where shortDescriptionUrl is null
        defaultProductDocumentShouldNotBeFound("shortDescriptionUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByShortDescriptionUrlContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where shortDescriptionUrl contains DEFAULT_SHORT_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("shortDescriptionUrl.contains=" + DEFAULT_SHORT_DESCRIPTION_URL);

        // Get all the productDocumentList where shortDescriptionUrl contains UPDATED_SHORT_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("shortDescriptionUrl.contains=" + UPDATED_SHORT_DESCRIPTION_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByShortDescriptionUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where shortDescriptionUrl does not contain DEFAULT_SHORT_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("shortDescriptionUrl.doesNotContain=" + DEFAULT_SHORT_DESCRIPTION_URL);

        // Get all the productDocumentList where shortDescriptionUrl does not contain UPDATED_SHORT_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("shortDescriptionUrl.doesNotContain=" + UPDATED_SHORT_DESCRIPTION_URL);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByDescriptionUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where descriptionUrl equals to DEFAULT_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("descriptionUrl.equals=" + DEFAULT_DESCRIPTION_URL);

        // Get all the productDocumentList where descriptionUrl equals to UPDATED_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("descriptionUrl.equals=" + UPDATED_DESCRIPTION_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByDescriptionUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where descriptionUrl not equals to DEFAULT_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("descriptionUrl.notEquals=" + DEFAULT_DESCRIPTION_URL);

        // Get all the productDocumentList where descriptionUrl not equals to UPDATED_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("descriptionUrl.notEquals=" + UPDATED_DESCRIPTION_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByDescriptionUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where descriptionUrl in DEFAULT_DESCRIPTION_URL or UPDATED_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("descriptionUrl.in=" + DEFAULT_DESCRIPTION_URL + "," + UPDATED_DESCRIPTION_URL);

        // Get all the productDocumentList where descriptionUrl equals to UPDATED_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("descriptionUrl.in=" + UPDATED_DESCRIPTION_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByDescriptionUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where descriptionUrl is not null
        defaultProductDocumentShouldBeFound("descriptionUrl.specified=true");

        // Get all the productDocumentList where descriptionUrl is null
        defaultProductDocumentShouldNotBeFound("descriptionUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByDescriptionUrlContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where descriptionUrl contains DEFAULT_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("descriptionUrl.contains=" + DEFAULT_DESCRIPTION_URL);

        // Get all the productDocumentList where descriptionUrl contains UPDATED_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("descriptionUrl.contains=" + UPDATED_DESCRIPTION_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByDescriptionUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where descriptionUrl does not contain DEFAULT_DESCRIPTION_URL
        defaultProductDocumentShouldNotBeFound("descriptionUrl.doesNotContain=" + DEFAULT_DESCRIPTION_URL);

        // Get all the productDocumentList where descriptionUrl does not contain UPDATED_DESCRIPTION_URL
        defaultProductDocumentShouldBeFound("descriptionUrl.doesNotContain=" + UPDATED_DESCRIPTION_URL);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByCareInstructionsUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where careInstructionsUrl equals to DEFAULT_CARE_INSTRUCTIONS_URL
        defaultProductDocumentShouldBeFound("careInstructionsUrl.equals=" + DEFAULT_CARE_INSTRUCTIONS_URL);

        // Get all the productDocumentList where careInstructionsUrl equals to UPDATED_CARE_INSTRUCTIONS_URL
        defaultProductDocumentShouldNotBeFound("careInstructionsUrl.equals=" + UPDATED_CARE_INSTRUCTIONS_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCareInstructionsUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where careInstructionsUrl not equals to DEFAULT_CARE_INSTRUCTIONS_URL
        defaultProductDocumentShouldNotBeFound("careInstructionsUrl.notEquals=" + DEFAULT_CARE_INSTRUCTIONS_URL);

        // Get all the productDocumentList where careInstructionsUrl not equals to UPDATED_CARE_INSTRUCTIONS_URL
        defaultProductDocumentShouldBeFound("careInstructionsUrl.notEquals=" + UPDATED_CARE_INSTRUCTIONS_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCareInstructionsUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where careInstructionsUrl in DEFAULT_CARE_INSTRUCTIONS_URL or UPDATED_CARE_INSTRUCTIONS_URL
        defaultProductDocumentShouldBeFound("careInstructionsUrl.in=" + DEFAULT_CARE_INSTRUCTIONS_URL + "," + UPDATED_CARE_INSTRUCTIONS_URL);

        // Get all the productDocumentList where careInstructionsUrl equals to UPDATED_CARE_INSTRUCTIONS_URL
        defaultProductDocumentShouldNotBeFound("careInstructionsUrl.in=" + UPDATED_CARE_INSTRUCTIONS_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCareInstructionsUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where careInstructionsUrl is not null
        defaultProductDocumentShouldBeFound("careInstructionsUrl.specified=true");

        // Get all the productDocumentList where careInstructionsUrl is null
        defaultProductDocumentShouldNotBeFound("careInstructionsUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByCareInstructionsUrlContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where careInstructionsUrl contains DEFAULT_CARE_INSTRUCTIONS_URL
        defaultProductDocumentShouldBeFound("careInstructionsUrl.contains=" + DEFAULT_CARE_INSTRUCTIONS_URL);

        // Get all the productDocumentList where careInstructionsUrl contains UPDATED_CARE_INSTRUCTIONS_URL
        defaultProductDocumentShouldNotBeFound("careInstructionsUrl.contains=" + UPDATED_CARE_INSTRUCTIONS_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCareInstructionsUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where careInstructionsUrl does not contain DEFAULT_CARE_INSTRUCTIONS_URL
        defaultProductDocumentShouldNotBeFound("careInstructionsUrl.doesNotContain=" + DEFAULT_CARE_INSTRUCTIONS_URL);

        // Get all the productDocumentList where careInstructionsUrl does not contain UPDATED_CARE_INSTRUCTIONS_URL
        defaultProductDocumentShouldBeFound("careInstructionsUrl.doesNotContain=" + UPDATED_CARE_INSTRUCTIONS_URL);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsBySpecialFeaturesUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where specialFeaturesUrl equals to DEFAULT_SPECIAL_FEATURES_URL
        defaultProductDocumentShouldBeFound("specialFeaturesUrl.equals=" + DEFAULT_SPECIAL_FEATURES_URL);

        // Get all the productDocumentList where specialFeaturesUrl equals to UPDATED_SPECIAL_FEATURES_URL
        defaultProductDocumentShouldNotBeFound("specialFeaturesUrl.equals=" + UPDATED_SPECIAL_FEATURES_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsBySpecialFeaturesUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where specialFeaturesUrl not equals to DEFAULT_SPECIAL_FEATURES_URL
        defaultProductDocumentShouldNotBeFound("specialFeaturesUrl.notEquals=" + DEFAULT_SPECIAL_FEATURES_URL);

        // Get all the productDocumentList where specialFeaturesUrl not equals to UPDATED_SPECIAL_FEATURES_URL
        defaultProductDocumentShouldBeFound("specialFeaturesUrl.notEquals=" + UPDATED_SPECIAL_FEATURES_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsBySpecialFeaturesUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where specialFeaturesUrl in DEFAULT_SPECIAL_FEATURES_URL or UPDATED_SPECIAL_FEATURES_URL
        defaultProductDocumentShouldBeFound("specialFeaturesUrl.in=" + DEFAULT_SPECIAL_FEATURES_URL + "," + UPDATED_SPECIAL_FEATURES_URL);

        // Get all the productDocumentList where specialFeaturesUrl equals to UPDATED_SPECIAL_FEATURES_URL
        defaultProductDocumentShouldNotBeFound("specialFeaturesUrl.in=" + UPDATED_SPECIAL_FEATURES_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsBySpecialFeaturesUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where specialFeaturesUrl is not null
        defaultProductDocumentShouldBeFound("specialFeaturesUrl.specified=true");

        // Get all the productDocumentList where specialFeaturesUrl is null
        defaultProductDocumentShouldNotBeFound("specialFeaturesUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsBySpecialFeaturesUrlContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where specialFeaturesUrl contains DEFAULT_SPECIAL_FEATURES_URL
        defaultProductDocumentShouldBeFound("specialFeaturesUrl.contains=" + DEFAULT_SPECIAL_FEATURES_URL);

        // Get all the productDocumentList where specialFeaturesUrl contains UPDATED_SPECIAL_FEATURES_URL
        defaultProductDocumentShouldNotBeFound("specialFeaturesUrl.contains=" + UPDATED_SPECIAL_FEATURES_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsBySpecialFeaturesUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where specialFeaturesUrl does not contain DEFAULT_SPECIAL_FEATURES_URL
        defaultProductDocumentShouldNotBeFound("specialFeaturesUrl.doesNotContain=" + DEFAULT_SPECIAL_FEATURES_URL);

        // Get all the productDocumentList where specialFeaturesUrl does not contain UPDATED_SPECIAL_FEATURES_URL
        defaultProductDocumentShouldBeFound("specialFeaturesUrl.doesNotContain=" + UPDATED_SPECIAL_FEATURES_URL);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByUsageAndSideEffectsUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where usageAndSideEffectsUrl equals to DEFAULT_USAGE_AND_SIDE_EFFECTS_URL
        defaultProductDocumentShouldBeFound("usageAndSideEffectsUrl.equals=" + DEFAULT_USAGE_AND_SIDE_EFFECTS_URL);

        // Get all the productDocumentList where usageAndSideEffectsUrl equals to UPDATED_USAGE_AND_SIDE_EFFECTS_URL
        defaultProductDocumentShouldNotBeFound("usageAndSideEffectsUrl.equals=" + UPDATED_USAGE_AND_SIDE_EFFECTS_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByUsageAndSideEffectsUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where usageAndSideEffectsUrl not equals to DEFAULT_USAGE_AND_SIDE_EFFECTS_URL
        defaultProductDocumentShouldNotBeFound("usageAndSideEffectsUrl.notEquals=" + DEFAULT_USAGE_AND_SIDE_EFFECTS_URL);

        // Get all the productDocumentList where usageAndSideEffectsUrl not equals to UPDATED_USAGE_AND_SIDE_EFFECTS_URL
        defaultProductDocumentShouldBeFound("usageAndSideEffectsUrl.notEquals=" + UPDATED_USAGE_AND_SIDE_EFFECTS_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByUsageAndSideEffectsUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where usageAndSideEffectsUrl in DEFAULT_USAGE_AND_SIDE_EFFECTS_URL or UPDATED_USAGE_AND_SIDE_EFFECTS_URL
        defaultProductDocumentShouldBeFound("usageAndSideEffectsUrl.in=" + DEFAULT_USAGE_AND_SIDE_EFFECTS_URL + "," + UPDATED_USAGE_AND_SIDE_EFFECTS_URL);

        // Get all the productDocumentList where usageAndSideEffectsUrl equals to UPDATED_USAGE_AND_SIDE_EFFECTS_URL
        defaultProductDocumentShouldNotBeFound("usageAndSideEffectsUrl.in=" + UPDATED_USAGE_AND_SIDE_EFFECTS_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByUsageAndSideEffectsUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where usageAndSideEffectsUrl is not null
        defaultProductDocumentShouldBeFound("usageAndSideEffectsUrl.specified=true");

        // Get all the productDocumentList where usageAndSideEffectsUrl is null
        defaultProductDocumentShouldNotBeFound("usageAndSideEffectsUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByUsageAndSideEffectsUrlContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where usageAndSideEffectsUrl contains DEFAULT_USAGE_AND_SIDE_EFFECTS_URL
        defaultProductDocumentShouldBeFound("usageAndSideEffectsUrl.contains=" + DEFAULT_USAGE_AND_SIDE_EFFECTS_URL);

        // Get all the productDocumentList where usageAndSideEffectsUrl contains UPDATED_USAGE_AND_SIDE_EFFECTS_URL
        defaultProductDocumentShouldNotBeFound("usageAndSideEffectsUrl.contains=" + UPDATED_USAGE_AND_SIDE_EFFECTS_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByUsageAndSideEffectsUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where usageAndSideEffectsUrl does not contain DEFAULT_USAGE_AND_SIDE_EFFECTS_URL
        defaultProductDocumentShouldNotBeFound("usageAndSideEffectsUrl.doesNotContain=" + DEFAULT_USAGE_AND_SIDE_EFFECTS_URL);

        // Get all the productDocumentList where usageAndSideEffectsUrl does not contain UPDATED_USAGE_AND_SIDE_EFFECTS_URL
        defaultProductDocumentShouldBeFound("usageAndSideEffectsUrl.doesNotContain=" + UPDATED_USAGE_AND_SIDE_EFFECTS_URL);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsBySafetyWarnningUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where safetyWarnningUrl equals to DEFAULT_SAFETY_WARNNING_URL
        defaultProductDocumentShouldBeFound("safetyWarnningUrl.equals=" + DEFAULT_SAFETY_WARNNING_URL);

        // Get all the productDocumentList where safetyWarnningUrl equals to UPDATED_SAFETY_WARNNING_URL
        defaultProductDocumentShouldNotBeFound("safetyWarnningUrl.equals=" + UPDATED_SAFETY_WARNNING_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsBySafetyWarnningUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where safetyWarnningUrl not equals to DEFAULT_SAFETY_WARNNING_URL
        defaultProductDocumentShouldNotBeFound("safetyWarnningUrl.notEquals=" + DEFAULT_SAFETY_WARNNING_URL);

        // Get all the productDocumentList where safetyWarnningUrl not equals to UPDATED_SAFETY_WARNNING_URL
        defaultProductDocumentShouldBeFound("safetyWarnningUrl.notEquals=" + UPDATED_SAFETY_WARNNING_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsBySafetyWarnningUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where safetyWarnningUrl in DEFAULT_SAFETY_WARNNING_URL or UPDATED_SAFETY_WARNNING_URL
        defaultProductDocumentShouldBeFound("safetyWarnningUrl.in=" + DEFAULT_SAFETY_WARNNING_URL + "," + UPDATED_SAFETY_WARNNING_URL);

        // Get all the productDocumentList where safetyWarnningUrl equals to UPDATED_SAFETY_WARNNING_URL
        defaultProductDocumentShouldNotBeFound("safetyWarnningUrl.in=" + UPDATED_SAFETY_WARNNING_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsBySafetyWarnningUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where safetyWarnningUrl is not null
        defaultProductDocumentShouldBeFound("safetyWarnningUrl.specified=true");

        // Get all the productDocumentList where safetyWarnningUrl is null
        defaultProductDocumentShouldNotBeFound("safetyWarnningUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsBySafetyWarnningUrlContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where safetyWarnningUrl contains DEFAULT_SAFETY_WARNNING_URL
        defaultProductDocumentShouldBeFound("safetyWarnningUrl.contains=" + DEFAULT_SAFETY_WARNNING_URL);

        // Get all the productDocumentList where safetyWarnningUrl contains UPDATED_SAFETY_WARNNING_URL
        defaultProductDocumentShouldNotBeFound("safetyWarnningUrl.contains=" + UPDATED_SAFETY_WARNNING_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsBySafetyWarnningUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where safetyWarnningUrl does not contain DEFAULT_SAFETY_WARNNING_URL
        defaultProductDocumentShouldNotBeFound("safetyWarnningUrl.doesNotContain=" + DEFAULT_SAFETY_WARNNING_URL);

        // Get all the productDocumentList where safetyWarnningUrl does not contain UPDATED_SAFETY_WARNNING_URL
        defaultProductDocumentShouldBeFound("safetyWarnningUrl.doesNotContain=" + UPDATED_SAFETY_WARNNING_URL);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productType equals to DEFAULT_PRODUCT_TYPE
        defaultProductDocumentShouldBeFound("productType.equals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productDocumentList where productType equals to UPDATED_PRODUCT_TYPE
        defaultProductDocumentShouldNotBeFound("productType.equals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productType not equals to DEFAULT_PRODUCT_TYPE
        defaultProductDocumentShouldNotBeFound("productType.notEquals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productDocumentList where productType not equals to UPDATED_PRODUCT_TYPE
        defaultProductDocumentShouldBeFound("productType.notEquals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productType in DEFAULT_PRODUCT_TYPE or UPDATED_PRODUCT_TYPE
        defaultProductDocumentShouldBeFound("productType.in=" + DEFAULT_PRODUCT_TYPE + "," + UPDATED_PRODUCT_TYPE);

        // Get all the productDocumentList where productType equals to UPDATED_PRODUCT_TYPE
        defaultProductDocumentShouldNotBeFound("productType.in=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productType is not null
        defaultProductDocumentShouldBeFound("productType.specified=true");

        // Get all the productDocumentList where productType is null
        defaultProductDocumentShouldNotBeFound("productType.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productType contains DEFAULT_PRODUCT_TYPE
        defaultProductDocumentShouldBeFound("productType.contains=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productDocumentList where productType contains UPDATED_PRODUCT_TYPE
        defaultProductDocumentShouldNotBeFound("productType.contains=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productType does not contain DEFAULT_PRODUCT_TYPE
        defaultProductDocumentShouldNotBeFound("productType.doesNotContain=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productDocumentList where productType does not contain UPDATED_PRODUCT_TYPE
        defaultProductDocumentShouldBeFound("productType.doesNotContain=" + UPDATED_PRODUCT_TYPE);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelName equals to DEFAULT_MODEL_NAME
        defaultProductDocumentShouldBeFound("modelName.equals=" + DEFAULT_MODEL_NAME);

        // Get all the productDocumentList where modelName equals to UPDATED_MODEL_NAME
        defaultProductDocumentShouldNotBeFound("modelName.equals=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelName not equals to DEFAULT_MODEL_NAME
        defaultProductDocumentShouldNotBeFound("modelName.notEquals=" + DEFAULT_MODEL_NAME);

        // Get all the productDocumentList where modelName not equals to UPDATED_MODEL_NAME
        defaultProductDocumentShouldBeFound("modelName.notEquals=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelName in DEFAULT_MODEL_NAME or UPDATED_MODEL_NAME
        defaultProductDocumentShouldBeFound("modelName.in=" + DEFAULT_MODEL_NAME + "," + UPDATED_MODEL_NAME);

        // Get all the productDocumentList where modelName equals to UPDATED_MODEL_NAME
        defaultProductDocumentShouldNotBeFound("modelName.in=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelName is not null
        defaultProductDocumentShouldBeFound("modelName.specified=true");

        // Get all the productDocumentList where modelName is null
        defaultProductDocumentShouldNotBeFound("modelName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByModelNameContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelName contains DEFAULT_MODEL_NAME
        defaultProductDocumentShouldBeFound("modelName.contains=" + DEFAULT_MODEL_NAME);

        // Get all the productDocumentList where modelName contains UPDATED_MODEL_NAME
        defaultProductDocumentShouldNotBeFound("modelName.contains=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelName does not contain DEFAULT_MODEL_NAME
        defaultProductDocumentShouldNotBeFound("modelName.doesNotContain=" + DEFAULT_MODEL_NAME);

        // Get all the productDocumentList where modelName does not contain UPDATED_MODEL_NAME
        defaultProductDocumentShouldBeFound("modelName.doesNotContain=" + UPDATED_MODEL_NAME);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelNumber equals to DEFAULT_MODEL_NUMBER
        defaultProductDocumentShouldBeFound("modelNumber.equals=" + DEFAULT_MODEL_NUMBER);

        // Get all the productDocumentList where modelNumber equals to UPDATED_MODEL_NUMBER
        defaultProductDocumentShouldNotBeFound("modelNumber.equals=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelNumber not equals to DEFAULT_MODEL_NUMBER
        defaultProductDocumentShouldNotBeFound("modelNumber.notEquals=" + DEFAULT_MODEL_NUMBER);

        // Get all the productDocumentList where modelNumber not equals to UPDATED_MODEL_NUMBER
        defaultProductDocumentShouldBeFound("modelNumber.notEquals=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelNumber in DEFAULT_MODEL_NUMBER or UPDATED_MODEL_NUMBER
        defaultProductDocumentShouldBeFound("modelNumber.in=" + DEFAULT_MODEL_NUMBER + "," + UPDATED_MODEL_NUMBER);

        // Get all the productDocumentList where modelNumber equals to UPDATED_MODEL_NUMBER
        defaultProductDocumentShouldNotBeFound("modelNumber.in=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelNumber is not null
        defaultProductDocumentShouldBeFound("modelNumber.specified=true");

        // Get all the productDocumentList where modelNumber is null
        defaultProductDocumentShouldNotBeFound("modelNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelNumber contains DEFAULT_MODEL_NUMBER
        defaultProductDocumentShouldBeFound("modelNumber.contains=" + DEFAULT_MODEL_NUMBER);

        // Get all the productDocumentList where modelNumber contains UPDATED_MODEL_NUMBER
        defaultProductDocumentShouldNotBeFound("modelNumber.contains=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelNumber does not contain DEFAULT_MODEL_NUMBER
        defaultProductDocumentShouldNotBeFound("modelNumber.doesNotContain=" + DEFAULT_MODEL_NUMBER);

        // Get all the productDocumentList where modelNumber does not contain UPDATED_MODEL_NUMBER
        defaultProductDocumentShouldBeFound("modelNumber.doesNotContain=" + UPDATED_MODEL_NUMBER);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where fabricType equals to DEFAULT_FABRIC_TYPE
        defaultProductDocumentShouldBeFound("fabricType.equals=" + DEFAULT_FABRIC_TYPE);

        // Get all the productDocumentList where fabricType equals to UPDATED_FABRIC_TYPE
        defaultProductDocumentShouldNotBeFound("fabricType.equals=" + UPDATED_FABRIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where fabricType not equals to DEFAULT_FABRIC_TYPE
        defaultProductDocumentShouldNotBeFound("fabricType.notEquals=" + DEFAULT_FABRIC_TYPE);

        // Get all the productDocumentList where fabricType not equals to UPDATED_FABRIC_TYPE
        defaultProductDocumentShouldBeFound("fabricType.notEquals=" + UPDATED_FABRIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where fabricType in DEFAULT_FABRIC_TYPE or UPDATED_FABRIC_TYPE
        defaultProductDocumentShouldBeFound("fabricType.in=" + DEFAULT_FABRIC_TYPE + "," + UPDATED_FABRIC_TYPE);

        // Get all the productDocumentList where fabricType equals to UPDATED_FABRIC_TYPE
        defaultProductDocumentShouldNotBeFound("fabricType.in=" + UPDATED_FABRIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where fabricType is not null
        defaultProductDocumentShouldBeFound("fabricType.specified=true");

        // Get all the productDocumentList where fabricType is null
        defaultProductDocumentShouldNotBeFound("fabricType.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where fabricType contains DEFAULT_FABRIC_TYPE
        defaultProductDocumentShouldBeFound("fabricType.contains=" + DEFAULT_FABRIC_TYPE);

        // Get all the productDocumentList where fabricType contains UPDATED_FABRIC_TYPE
        defaultProductDocumentShouldNotBeFound("fabricType.contains=" + UPDATED_FABRIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where fabricType does not contain DEFAULT_FABRIC_TYPE
        defaultProductDocumentShouldNotBeFound("fabricType.doesNotContain=" + DEFAULT_FABRIC_TYPE);

        // Get all the productDocumentList where fabricType does not contain UPDATED_FABRIC_TYPE
        defaultProductDocumentShouldBeFound("fabricType.doesNotContain=" + UPDATED_FABRIC_TYPE);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productComplianceCertificate equals to DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldBeFound("productComplianceCertificate.equals=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentList where productComplianceCertificate equals to UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldNotBeFound("productComplianceCertificate.equals=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productComplianceCertificate not equals to DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldNotBeFound("productComplianceCertificate.notEquals=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentList where productComplianceCertificate not equals to UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldBeFound("productComplianceCertificate.notEquals=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productComplianceCertificate in DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE or UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldBeFound("productComplianceCertificate.in=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE + "," + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentList where productComplianceCertificate equals to UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldNotBeFound("productComplianceCertificate.in=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productComplianceCertificate is not null
        defaultProductDocumentShouldBeFound("productComplianceCertificate.specified=true");

        // Get all the productDocumentList where productComplianceCertificate is null
        defaultProductDocumentShouldNotBeFound("productComplianceCertificate.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productComplianceCertificate contains DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldBeFound("productComplianceCertificate.contains=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentList where productComplianceCertificate contains UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldNotBeFound("productComplianceCertificate.contains=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productComplianceCertificate does not contain DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldNotBeFound("productComplianceCertificate.doesNotContain=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentList where productComplianceCertificate does not contain UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldBeFound("productComplianceCertificate.doesNotContain=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByGenuineAndLegalIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where genuineAndLegal equals to DEFAULT_GENUINE_AND_LEGAL
        defaultProductDocumentShouldBeFound("genuineAndLegal.equals=" + DEFAULT_GENUINE_AND_LEGAL);

        // Get all the productDocumentList where genuineAndLegal equals to UPDATED_GENUINE_AND_LEGAL
        defaultProductDocumentShouldNotBeFound("genuineAndLegal.equals=" + UPDATED_GENUINE_AND_LEGAL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByGenuineAndLegalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where genuineAndLegal not equals to DEFAULT_GENUINE_AND_LEGAL
        defaultProductDocumentShouldNotBeFound("genuineAndLegal.notEquals=" + DEFAULT_GENUINE_AND_LEGAL);

        // Get all the productDocumentList where genuineAndLegal not equals to UPDATED_GENUINE_AND_LEGAL
        defaultProductDocumentShouldBeFound("genuineAndLegal.notEquals=" + UPDATED_GENUINE_AND_LEGAL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByGenuineAndLegalIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where genuineAndLegal in DEFAULT_GENUINE_AND_LEGAL or UPDATED_GENUINE_AND_LEGAL
        defaultProductDocumentShouldBeFound("genuineAndLegal.in=" + DEFAULT_GENUINE_AND_LEGAL + "," + UPDATED_GENUINE_AND_LEGAL);

        // Get all the productDocumentList where genuineAndLegal equals to UPDATED_GENUINE_AND_LEGAL
        defaultProductDocumentShouldNotBeFound("genuineAndLegal.in=" + UPDATED_GENUINE_AND_LEGAL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByGenuineAndLegalIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where genuineAndLegal is not null
        defaultProductDocumentShouldBeFound("genuineAndLegal.specified=true");

        // Get all the productDocumentList where genuineAndLegal is null
        defaultProductDocumentShouldNotBeFound("genuineAndLegal.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where countryOfOrigin equals to DEFAULT_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldBeFound("countryOfOrigin.equals=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentList where countryOfOrigin equals to UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldNotBeFound("countryOfOrigin.equals=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where countryOfOrigin not equals to DEFAULT_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldNotBeFound("countryOfOrigin.notEquals=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentList where countryOfOrigin not equals to UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldBeFound("countryOfOrigin.notEquals=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where countryOfOrigin in DEFAULT_COUNTRY_OF_ORIGIN or UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldBeFound("countryOfOrigin.in=" + DEFAULT_COUNTRY_OF_ORIGIN + "," + UPDATED_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentList where countryOfOrigin equals to UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldNotBeFound("countryOfOrigin.in=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where countryOfOrigin is not null
        defaultProductDocumentShouldBeFound("countryOfOrigin.specified=true");

        // Get all the productDocumentList where countryOfOrigin is null
        defaultProductDocumentShouldNotBeFound("countryOfOrigin.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where countryOfOrigin contains DEFAULT_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldBeFound("countryOfOrigin.contains=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentList where countryOfOrigin contains UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldNotBeFound("countryOfOrigin.contains=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where countryOfOrigin does not contain DEFAULT_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldNotBeFound("countryOfOrigin.doesNotContain=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentList where countryOfOrigin does not contain UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldBeFound("countryOfOrigin.doesNotContain=" + UPDATED_COUNTRY_OF_ORIGIN);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPeriod equals to DEFAULT_WARRANTY_PERIOD
        defaultProductDocumentShouldBeFound("warrantyPeriod.equals=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productDocumentList where warrantyPeriod equals to UPDATED_WARRANTY_PERIOD
        defaultProductDocumentShouldNotBeFound("warrantyPeriod.equals=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPeriod not equals to DEFAULT_WARRANTY_PERIOD
        defaultProductDocumentShouldNotBeFound("warrantyPeriod.notEquals=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productDocumentList where warrantyPeriod not equals to UPDATED_WARRANTY_PERIOD
        defaultProductDocumentShouldBeFound("warrantyPeriod.notEquals=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPeriod in DEFAULT_WARRANTY_PERIOD or UPDATED_WARRANTY_PERIOD
        defaultProductDocumentShouldBeFound("warrantyPeriod.in=" + DEFAULT_WARRANTY_PERIOD + "," + UPDATED_WARRANTY_PERIOD);

        // Get all the productDocumentList where warrantyPeriod equals to UPDATED_WARRANTY_PERIOD
        defaultProductDocumentShouldNotBeFound("warrantyPeriod.in=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPeriod is not null
        defaultProductDocumentShouldBeFound("warrantyPeriod.specified=true");

        // Get all the productDocumentList where warrantyPeriod is null
        defaultProductDocumentShouldNotBeFound("warrantyPeriod.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPeriod contains DEFAULT_WARRANTY_PERIOD
        defaultProductDocumentShouldBeFound("warrantyPeriod.contains=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productDocumentList where warrantyPeriod contains UPDATED_WARRANTY_PERIOD
        defaultProductDocumentShouldNotBeFound("warrantyPeriod.contains=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPeriod does not contain DEFAULT_WARRANTY_PERIOD
        defaultProductDocumentShouldNotBeFound("warrantyPeriod.doesNotContain=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productDocumentList where warrantyPeriod does not contain UPDATED_WARRANTY_PERIOD
        defaultProductDocumentShouldBeFound("warrantyPeriod.doesNotContain=" + UPDATED_WARRANTY_PERIOD);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPolicy equals to DEFAULT_WARRANTY_POLICY
        defaultProductDocumentShouldBeFound("warrantyPolicy.equals=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productDocumentList where warrantyPolicy equals to UPDATED_WARRANTY_POLICY
        defaultProductDocumentShouldNotBeFound("warrantyPolicy.equals=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPolicy not equals to DEFAULT_WARRANTY_POLICY
        defaultProductDocumentShouldNotBeFound("warrantyPolicy.notEquals=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productDocumentList where warrantyPolicy not equals to UPDATED_WARRANTY_POLICY
        defaultProductDocumentShouldBeFound("warrantyPolicy.notEquals=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPolicy in DEFAULT_WARRANTY_POLICY or UPDATED_WARRANTY_POLICY
        defaultProductDocumentShouldBeFound("warrantyPolicy.in=" + DEFAULT_WARRANTY_POLICY + "," + UPDATED_WARRANTY_POLICY);

        // Get all the productDocumentList where warrantyPolicy equals to UPDATED_WARRANTY_POLICY
        defaultProductDocumentShouldNotBeFound("warrantyPolicy.in=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPolicy is not null
        defaultProductDocumentShouldBeFound("warrantyPolicy.specified=true");

        // Get all the productDocumentList where warrantyPolicy is null
        defaultProductDocumentShouldNotBeFound("warrantyPolicy.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPolicy contains DEFAULT_WARRANTY_POLICY
        defaultProductDocumentShouldBeFound("warrantyPolicy.contains=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productDocumentList where warrantyPolicy contains UPDATED_WARRANTY_POLICY
        defaultProductDocumentShouldNotBeFound("warrantyPolicy.contains=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPolicy does not contain DEFAULT_WARRANTY_POLICY
        defaultProductDocumentShouldNotBeFound("warrantyPolicy.doesNotContain=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productDocumentList where warrantyPolicy does not contain UPDATED_WARRANTY_POLICY
        defaultProductDocumentShouldBeFound("warrantyPolicy.doesNotContain=" + UPDATED_WARRANTY_POLICY);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);
        WarrantyTypes warrantyType = WarrantyTypesResourceIT.createEntity(em);
        em.persist(warrantyType);
        em.flush();
        productDocument.setWarrantyType(warrantyType);
        productDocumentRepository.saveAndFlush(productDocument);
        Long warrantyTypeId = warrantyType.getId();

        // Get all the productDocumentList where warrantyType equals to warrantyTypeId
        defaultProductDocumentShouldBeFound("warrantyTypeId.equals=" + warrantyTypeId);

        // Get all the productDocumentList where warrantyType equals to warrantyTypeId + 1
        defaultProductDocumentShouldNotBeFound("warrantyTypeId.equals=" + (warrantyTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        productDocument.setCulture(culture);
        productDocumentRepository.saveAndFlush(productDocument);
        Long cultureId = culture.getId();

        // Get all the productDocumentList where culture equals to cultureId
        defaultProductDocumentShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the productDocumentList where culture equals to cultureId + 1
        defaultProductDocumentShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductDocumentShouldBeFound(String filter) throws Exception {
        restProductDocumentMockMvc.perform(get("/api/product-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].highlightsUrl").value(hasItem(DEFAULT_HIGHLIGHTS_URL)))
            .andExpect(jsonPath("$.[*].longDescriptionUrl").value(hasItem(DEFAULT_LONG_DESCRIPTION_URL)))
            .andExpect(jsonPath("$.[*].shortDescriptionUrl").value(hasItem(DEFAULT_SHORT_DESCRIPTION_URL)))
            .andExpect(jsonPath("$.[*].descriptionUrl").value(hasItem(DEFAULT_DESCRIPTION_URL)))
            .andExpect(jsonPath("$.[*].careInstructionsUrl").value(hasItem(DEFAULT_CARE_INSTRUCTIONS_URL)))
            .andExpect(jsonPath("$.[*].specialFeaturesUrl").value(hasItem(DEFAULT_SPECIAL_FEATURES_URL)))
            .andExpect(jsonPath("$.[*].usageAndSideEffectsUrl").value(hasItem(DEFAULT_USAGE_AND_SIDE_EFFECTS_URL)))
            .andExpect(jsonPath("$.[*].safetyWarnningUrl").value(hasItem(DEFAULT_SAFETY_WARNNING_URL)))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE)))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME)))
            .andExpect(jsonPath("$.[*].modelNumber").value(hasItem(DEFAULT_MODEL_NUMBER)))
            .andExpect(jsonPath("$.[*].fabricType").value(hasItem(DEFAULT_FABRIC_TYPE)))
            .andExpect(jsonPath("$.[*].productComplianceCertificate").value(hasItem(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE)))
            .andExpect(jsonPath("$.[*].genuineAndLegal").value(hasItem(DEFAULT_GENUINE_AND_LEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].countryOfOrigin").value(hasItem(DEFAULT_COUNTRY_OF_ORIGIN)))
            .andExpect(jsonPath("$.[*].warrantyPeriod").value(hasItem(DEFAULT_WARRANTY_PERIOD)))
            .andExpect(jsonPath("$.[*].warrantyPolicy").value(hasItem(DEFAULT_WARRANTY_POLICY)));

        // Check, that the count call also returns 1
        restProductDocumentMockMvc.perform(get("/api/product-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductDocumentShouldNotBeFound(String filter) throws Exception {
        restProductDocumentMockMvc.perform(get("/api/product-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductDocumentMockMvc.perform(get("/api/product-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductDocument() throws Exception {
        // Get the productDocument
        restProductDocumentMockMvc.perform(get("/api/product-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductDocument() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        int databaseSizeBeforeUpdate = productDocumentRepository.findAll().size();

        // Update the productDocument
        ProductDocument updatedProductDocument = productDocumentRepository.findById(productDocument.getId()).get();
        // Disconnect from session so that the updates on updatedProductDocument are not directly saved in db
        em.detach(updatedProductDocument);
        updatedProductDocument
            .videoUrl(UPDATED_VIDEO_URL)
            .highlightsUrl(UPDATED_HIGHLIGHTS_URL)
            .longDescriptionUrl(UPDATED_LONG_DESCRIPTION_URL)
            .shortDescriptionUrl(UPDATED_SHORT_DESCRIPTION_URL)
            .descriptionUrl(UPDATED_DESCRIPTION_URL)
            .careInstructionsUrl(UPDATED_CARE_INSTRUCTIONS_URL)
            .specialFeaturesUrl(UPDATED_SPECIAL_FEATURES_URL)
            .usageAndSideEffectsUrl(UPDATED_USAGE_AND_SIDE_EFFECTS_URL)
            .safetyWarnningUrl(UPDATED_SAFETY_WARNNING_URL)
            .productType(UPDATED_PRODUCT_TYPE)
            .modelName(UPDATED_MODEL_NAME)
            .modelNumber(UPDATED_MODEL_NUMBER)
            .fabricType(UPDATED_FABRIC_TYPE)
            .productComplianceCertificate(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(UPDATED_GENUINE_AND_LEGAL)
            .countryOfOrigin(UPDATED_COUNTRY_OF_ORIGIN)
            .warrantyPeriod(UPDATED_WARRANTY_PERIOD)
            .warrantyPolicy(UPDATED_WARRANTY_POLICY);
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(updatedProductDocument);

        restProductDocumentMockMvc.perform(put("/api/product-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeUpdate);
        ProductDocument testProductDocument = productDocumentList.get(productDocumentList.size() - 1);
        assertThat(testProductDocument.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testProductDocument.getHighlightsUrl()).isEqualTo(UPDATED_HIGHLIGHTS_URL);
        assertThat(testProductDocument.getLongDescriptionUrl()).isEqualTo(UPDATED_LONG_DESCRIPTION_URL);
        assertThat(testProductDocument.getShortDescriptionUrl()).isEqualTo(UPDATED_SHORT_DESCRIPTION_URL);
        assertThat(testProductDocument.getDescriptionUrl()).isEqualTo(UPDATED_DESCRIPTION_URL);
        assertThat(testProductDocument.getCareInstructionsUrl()).isEqualTo(UPDATED_CARE_INSTRUCTIONS_URL);
        assertThat(testProductDocument.getSpecialFeaturesUrl()).isEqualTo(UPDATED_SPECIAL_FEATURES_URL);
        assertThat(testProductDocument.getUsageAndSideEffectsUrl()).isEqualTo(UPDATED_USAGE_AND_SIDE_EFFECTS_URL);
        assertThat(testProductDocument.getSafetyWarnningUrl()).isEqualTo(UPDATED_SAFETY_WARNNING_URL);
        assertThat(testProductDocument.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testProductDocument.getModelName()).isEqualTo(UPDATED_MODEL_NAME);
        assertThat(testProductDocument.getModelNumber()).isEqualTo(UPDATED_MODEL_NUMBER);
        assertThat(testProductDocument.getFabricType()).isEqualTo(UPDATED_FABRIC_TYPE);
        assertThat(testProductDocument.getProductComplianceCertificate()).isEqualTo(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
        assertThat(testProductDocument.isGenuineAndLegal()).isEqualTo(UPDATED_GENUINE_AND_LEGAL);
        assertThat(testProductDocument.getCountryOfOrigin()).isEqualTo(UPDATED_COUNTRY_OF_ORIGIN);
        assertThat(testProductDocument.getWarrantyPeriod()).isEqualTo(UPDATED_WARRANTY_PERIOD);
        assertThat(testProductDocument.getWarrantyPolicy()).isEqualTo(UPDATED_WARRANTY_POLICY);

        // Validate the ProductDocument in Elasticsearch
        verify(mockProductDocumentSearchRepository, times(1)).save(testProductDocument);
    }

    @Test
    @Transactional
    public void updateNonExistingProductDocument() throws Exception {
        int databaseSizeBeforeUpdate = productDocumentRepository.findAll().size();

        // Create the ProductDocument
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(productDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDocumentMockMvc.perform(put("/api/product-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductDocument in Elasticsearch
        verify(mockProductDocumentSearchRepository, times(0)).save(productDocument);
    }

    @Test
    @Transactional
    public void deleteProductDocument() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        int databaseSizeBeforeDelete = productDocumentRepository.findAll().size();

        // Delete the productDocument
        restProductDocumentMockMvc.perform(delete("/api/product-documents/{id}", productDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductDocument in Elasticsearch
        verify(mockProductDocumentSearchRepository, times(1)).deleteById(productDocument.getId());
    }

    @Test
    @Transactional
    public void searchProductDocument() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);
        when(mockProductDocumentSearchRepository.search(queryStringQuery("id:" + productDocument.getId())))
            .thenReturn(Collections.singletonList(productDocument));
        // Search the productDocument
        restProductDocumentMockMvc.perform(get("/api/_search/product-documents?query=id:" + productDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].highlightsUrl").value(hasItem(DEFAULT_HIGHLIGHTS_URL)))
            .andExpect(jsonPath("$.[*].longDescriptionUrl").value(hasItem(DEFAULT_LONG_DESCRIPTION_URL)))
            .andExpect(jsonPath("$.[*].shortDescriptionUrl").value(hasItem(DEFAULT_SHORT_DESCRIPTION_URL)))
            .andExpect(jsonPath("$.[*].descriptionUrl").value(hasItem(DEFAULT_DESCRIPTION_URL)))
            .andExpect(jsonPath("$.[*].careInstructionsUrl").value(hasItem(DEFAULT_CARE_INSTRUCTIONS_URL)))
            .andExpect(jsonPath("$.[*].specialFeaturesUrl").value(hasItem(DEFAULT_SPECIAL_FEATURES_URL)))
            .andExpect(jsonPath("$.[*].usageAndSideEffectsUrl").value(hasItem(DEFAULT_USAGE_AND_SIDE_EFFECTS_URL)))
            .andExpect(jsonPath("$.[*].safetyWarnningUrl").value(hasItem(DEFAULT_SAFETY_WARNNING_URL)))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE)))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME)))
            .andExpect(jsonPath("$.[*].modelNumber").value(hasItem(DEFAULT_MODEL_NUMBER)))
            .andExpect(jsonPath("$.[*].fabricType").value(hasItem(DEFAULT_FABRIC_TYPE)))
            .andExpect(jsonPath("$.[*].productComplianceCertificate").value(hasItem(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE)))
            .andExpect(jsonPath("$.[*].genuineAndLegal").value(hasItem(DEFAULT_GENUINE_AND_LEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].countryOfOrigin").value(hasItem(DEFAULT_COUNTRY_OF_ORIGIN)))
            .andExpect(jsonPath("$.[*].warrantyPeriod").value(hasItem(DEFAULT_WARRANTY_PERIOD)))
            .andExpect(jsonPath("$.[*].warrantyPolicy").value(hasItem(DEFAULT_WARRANTY_POLICY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDocument.class);
        ProductDocument productDocument1 = new ProductDocument();
        productDocument1.setId(1L);
        ProductDocument productDocument2 = new ProductDocument();
        productDocument2.setId(productDocument1.getId());
        assertThat(productDocument1).isEqualTo(productDocument2);
        productDocument2.setId(2L);
        assertThat(productDocument1).isNotEqualTo(productDocument2);
        productDocument1.setId(null);
        assertThat(productDocument1).isNotEqualTo(productDocument2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDocumentDTO.class);
        ProductDocumentDTO productDocumentDTO1 = new ProductDocumentDTO();
        productDocumentDTO1.setId(1L);
        ProductDocumentDTO productDocumentDTO2 = new ProductDocumentDTO();
        assertThat(productDocumentDTO1).isNotEqualTo(productDocumentDTO2);
        productDocumentDTO2.setId(productDocumentDTO1.getId());
        assertThat(productDocumentDTO1).isEqualTo(productDocumentDTO2);
        productDocumentDTO2.setId(2L);
        assertThat(productDocumentDTO1).isNotEqualTo(productDocumentDTO2);
        productDocumentDTO1.setId(null);
        assertThat(productDocumentDTO1).isNotEqualTo(productDocumentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productDocumentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productDocumentMapper.fromId(null)).isNull();
    }
}
