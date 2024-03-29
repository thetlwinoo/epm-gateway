package com.epmserver.gateway.web.rest;

import com.epmserver.gateway.EpmgatewayApp;
import com.epmserver.gateway.domain.ContactType;
import com.epmserver.gateway.repository.ContactTypeRepository;
import com.epmserver.gateway.repository.search.ContactTypeSearchRepository;
import com.epmserver.gateway.service.ContactTypeService;
import com.epmserver.gateway.service.dto.ContactTypeDTO;
import com.epmserver.gateway.service.mapper.ContactTypeMapper;
import com.epmserver.gateway.web.rest.errors.ExceptionTranslator;
import com.epmserver.gateway.service.dto.ContactTypeCriteria;
import com.epmserver.gateway.service.ContactTypeQueryService;

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
 * Integration tests for the {@link ContactTypeResource} REST controller.
 */
@SpringBootTest(classes = EpmgatewayApp.class)
public class ContactTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ContactTypeRepository contactTypeRepository;

    @Autowired
    private ContactTypeMapper contactTypeMapper;

    @Autowired
    private ContactTypeService contactTypeService;

    /**
     * This repository is mocked in the com.epmserver.gateway.repository.search test package.
     *
     * @see com.epmserver.gateway.repository.search.ContactTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContactTypeSearchRepository mockContactTypeSearchRepository;

    @Autowired
    private ContactTypeQueryService contactTypeQueryService;

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

    private MockMvc restContactTypeMockMvc;

    private ContactType contactType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactTypeResource contactTypeResource = new ContactTypeResource(contactTypeService, contactTypeQueryService);
        this.restContactTypeMockMvc = MockMvcBuilders.standaloneSetup(contactTypeResource)
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
    public static ContactType createEntity(EntityManager em) {
        ContactType contactType = new ContactType()
            .name(DEFAULT_NAME);
        return contactType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactType createUpdatedEntity(EntityManager em) {
        ContactType contactType = new ContactType()
            .name(UPDATED_NAME);
        return contactType;
    }

    @BeforeEach
    public void initTest() {
        contactType = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactType() throws Exception {
        int databaseSizeBeforeCreate = contactTypeRepository.findAll().size();

        // Create the ContactType
        ContactTypeDTO contactTypeDTO = contactTypeMapper.toDto(contactType);
        restContactTypeMockMvc.perform(post("/api/contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactType in the database
        List<ContactType> contactTypeList = contactTypeRepository.findAll();
        assertThat(contactTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ContactType testContactType = contactTypeList.get(contactTypeList.size() - 1);
        assertThat(testContactType.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the ContactType in Elasticsearch
        verify(mockContactTypeSearchRepository, times(1)).save(testContactType);
    }

    @Test
    @Transactional
    public void createContactTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactTypeRepository.findAll().size();

        // Create the ContactType with an existing ID
        contactType.setId(1L);
        ContactTypeDTO contactTypeDTO = contactTypeMapper.toDto(contactType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactTypeMockMvc.perform(post("/api/contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactType in the database
        List<ContactType> contactTypeList = contactTypeRepository.findAll();
        assertThat(contactTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ContactType in Elasticsearch
        verify(mockContactTypeSearchRepository, times(0)).save(contactType);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactTypeRepository.findAll().size();
        // set the field null
        contactType.setName(null);

        // Create the ContactType, which fails.
        ContactTypeDTO contactTypeDTO = contactTypeMapper.toDto(contactType);

        restContactTypeMockMvc.perform(post("/api/contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ContactType> contactTypeList = contactTypeRepository.findAll();
        assertThat(contactTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactTypes() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList
        restContactTypeMockMvc.perform(get("/api/contact-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getContactType() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get the contactType
        restContactTypeMockMvc.perform(get("/api/contact-types/{id}", contactType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getAllContactTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList where name equals to DEFAULT_NAME
        defaultContactTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contactTypeList where name equals to UPDATED_NAME
        defaultContactTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContactTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList where name not equals to DEFAULT_NAME
        defaultContactTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the contactTypeList where name not equals to UPDATED_NAME
        defaultContactTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContactTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContactTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contactTypeList where name equals to UPDATED_NAME
        defaultContactTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContactTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList where name is not null
        defaultContactTypeShouldBeFound("name.specified=true");

        // Get all the contactTypeList where name is null
        defaultContactTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllContactTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList where name contains DEFAULT_NAME
        defaultContactTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the contactTypeList where name contains UPDATED_NAME
        defaultContactTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContactTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList where name does not contain DEFAULT_NAME
        defaultContactTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the contactTypeList where name does not contain UPDATED_NAME
        defaultContactTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContactTypeShouldBeFound(String filter) throws Exception {
        restContactTypeMockMvc.perform(get("/api/contact-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restContactTypeMockMvc.perform(get("/api/contact-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContactTypeShouldNotBeFound(String filter) throws Exception {
        restContactTypeMockMvc.perform(get("/api/contact-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContactTypeMockMvc.perform(get("/api/contact-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingContactType() throws Exception {
        // Get the contactType
        restContactTypeMockMvc.perform(get("/api/contact-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactType() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        int databaseSizeBeforeUpdate = contactTypeRepository.findAll().size();

        // Update the contactType
        ContactType updatedContactType = contactTypeRepository.findById(contactType.getId()).get();
        // Disconnect from session so that the updates on updatedContactType are not directly saved in db
        em.detach(updatedContactType);
        updatedContactType
            .name(UPDATED_NAME);
        ContactTypeDTO contactTypeDTO = contactTypeMapper.toDto(updatedContactType);

        restContactTypeMockMvc.perform(put("/api/contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ContactType in the database
        List<ContactType> contactTypeList = contactTypeRepository.findAll();
        assertThat(contactTypeList).hasSize(databaseSizeBeforeUpdate);
        ContactType testContactType = contactTypeList.get(contactTypeList.size() - 1);
        assertThat(testContactType.getName()).isEqualTo(UPDATED_NAME);

        // Validate the ContactType in Elasticsearch
        verify(mockContactTypeSearchRepository, times(1)).save(testContactType);
    }

    @Test
    @Transactional
    public void updateNonExistingContactType() throws Exception {
        int databaseSizeBeforeUpdate = contactTypeRepository.findAll().size();

        // Create the ContactType
        ContactTypeDTO contactTypeDTO = contactTypeMapper.toDto(contactType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactTypeMockMvc.perform(put("/api/contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactType in the database
        List<ContactType> contactTypeList = contactTypeRepository.findAll();
        assertThat(contactTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ContactType in Elasticsearch
        verify(mockContactTypeSearchRepository, times(0)).save(contactType);
    }

    @Test
    @Transactional
    public void deleteContactType() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        int databaseSizeBeforeDelete = contactTypeRepository.findAll().size();

        // Delete the contactType
        restContactTypeMockMvc.perform(delete("/api/contact-types/{id}", contactType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactType> contactTypeList = contactTypeRepository.findAll();
        assertThat(contactTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ContactType in Elasticsearch
        verify(mockContactTypeSearchRepository, times(1)).deleteById(contactType.getId());
    }

    @Test
    @Transactional
    public void searchContactType() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);
        when(mockContactTypeSearchRepository.search(queryStringQuery("id:" + contactType.getId())))
            .thenReturn(Collections.singletonList(contactType));
        // Search the contactType
        restContactTypeMockMvc.perform(get("/api/_search/contact-types?query=id:" + contactType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactType.class);
        ContactType contactType1 = new ContactType();
        contactType1.setId(1L);
        ContactType contactType2 = new ContactType();
        contactType2.setId(contactType1.getId());
        assertThat(contactType1).isEqualTo(contactType2);
        contactType2.setId(2L);
        assertThat(contactType1).isNotEqualTo(contactType2);
        contactType1.setId(null);
        assertThat(contactType1).isNotEqualTo(contactType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactTypeDTO.class);
        ContactTypeDTO contactTypeDTO1 = new ContactTypeDTO();
        contactTypeDTO1.setId(1L);
        ContactTypeDTO contactTypeDTO2 = new ContactTypeDTO();
        assertThat(contactTypeDTO1).isNotEqualTo(contactTypeDTO2);
        contactTypeDTO2.setId(contactTypeDTO1.getId());
        assertThat(contactTypeDTO1).isEqualTo(contactTypeDTO2);
        contactTypeDTO2.setId(2L);
        assertThat(contactTypeDTO1).isNotEqualTo(contactTypeDTO2);
        contactTypeDTO1.setId(null);
        assertThat(contactTypeDTO1).isNotEqualTo(contactTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contactTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contactTypeMapper.fromId(null)).isNull();
    }
}
