package com.epmserver.gateway.web.rest;

import com.epmserver.gateway.EpmgatewayApp;
import com.epmserver.gateway.domain.BusinessEntityContact;
import com.epmserver.gateway.domain.People;
import com.epmserver.gateway.domain.ContactType;
import com.epmserver.gateway.repository.BusinessEntityContactRepository;
import com.epmserver.gateway.repository.search.BusinessEntityContactSearchRepository;
import com.epmserver.gateway.service.BusinessEntityContactService;
import com.epmserver.gateway.service.dto.BusinessEntityContactDTO;
import com.epmserver.gateway.service.mapper.BusinessEntityContactMapper;
import com.epmserver.gateway.web.rest.errors.ExceptionTranslator;
import com.epmserver.gateway.service.dto.BusinessEntityContactCriteria;
import com.epmserver.gateway.service.BusinessEntityContactQueryService;

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
 * Integration tests for the {@link BusinessEntityContactResource} REST controller.
 */
@SpringBootTest(classes = EpmgatewayApp.class)
public class BusinessEntityContactResourceIT {

    @Autowired
    private BusinessEntityContactRepository businessEntityContactRepository;

    @Autowired
    private BusinessEntityContactMapper businessEntityContactMapper;

    @Autowired
    private BusinessEntityContactService businessEntityContactService;

    /**
     * This repository is mocked in the com.epmserver.gateway.repository.search test package.
     *
     * @see com.epmserver.gateway.repository.search.BusinessEntityContactSearchRepositoryMockConfiguration
     */
    @Autowired
    private BusinessEntityContactSearchRepository mockBusinessEntityContactSearchRepository;

    @Autowired
    private BusinessEntityContactQueryService businessEntityContactQueryService;

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

    private MockMvc restBusinessEntityContactMockMvc;

    private BusinessEntityContact businessEntityContact;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessEntityContactResource businessEntityContactResource = new BusinessEntityContactResource(businessEntityContactService, businessEntityContactQueryService);
        this.restBusinessEntityContactMockMvc = MockMvcBuilders.standaloneSetup(businessEntityContactResource)
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
    public static BusinessEntityContact createEntity(EntityManager em) {
        BusinessEntityContact businessEntityContact = new BusinessEntityContact();
        return businessEntityContact;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessEntityContact createUpdatedEntity(EntityManager em) {
        BusinessEntityContact businessEntityContact = new BusinessEntityContact();
        return businessEntityContact;
    }

    @BeforeEach
    public void initTest() {
        businessEntityContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessEntityContact() throws Exception {
        int databaseSizeBeforeCreate = businessEntityContactRepository.findAll().size();

        // Create the BusinessEntityContact
        BusinessEntityContactDTO businessEntityContactDTO = businessEntityContactMapper.toDto(businessEntityContact);
        restBusinessEntityContactMockMvc.perform(post("/api/business-entity-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityContactDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessEntityContact in the database
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessEntityContact testBusinessEntityContact = businessEntityContactList.get(businessEntityContactList.size() - 1);

        // Validate the BusinessEntityContact in Elasticsearch
        verify(mockBusinessEntityContactSearchRepository, times(1)).save(testBusinessEntityContact);
    }

    @Test
    @Transactional
    public void createBusinessEntityContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessEntityContactRepository.findAll().size();

        // Create the BusinessEntityContact with an existing ID
        businessEntityContact.setId(1L);
        BusinessEntityContactDTO businessEntityContactDTO = businessEntityContactMapper.toDto(businessEntityContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessEntityContactMockMvc.perform(post("/api/business-entity-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessEntityContact in the database
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeCreate);

        // Validate the BusinessEntityContact in Elasticsearch
        verify(mockBusinessEntityContactSearchRepository, times(0)).save(businessEntityContact);
    }


    @Test
    @Transactional
    public void getAllBusinessEntityContacts() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        // Get all the businessEntityContactList
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessEntityContact.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getBusinessEntityContact() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        // Get the businessEntityContact
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts/{id}", businessEntityContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessEntityContact.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllBusinessEntityContactsByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);
        People person = PeopleResourceIT.createEntity(em);
        em.persist(person);
        em.flush();
        businessEntityContact.setPerson(person);
        businessEntityContactRepository.saveAndFlush(businessEntityContact);
        Long personId = person.getId();

        // Get all the businessEntityContactList where person equals to personId
        defaultBusinessEntityContactShouldBeFound("personId.equals=" + personId);

        // Get all the businessEntityContactList where person equals to personId + 1
        defaultBusinessEntityContactShouldNotBeFound("personId.equals=" + (personId + 1));
    }


    @Test
    @Transactional
    public void getAllBusinessEntityContactsByContactTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);
        ContactType contactType = ContactTypeResourceIT.createEntity(em);
        em.persist(contactType);
        em.flush();
        businessEntityContact.setContactType(contactType);
        businessEntityContactRepository.saveAndFlush(businessEntityContact);
        Long contactTypeId = contactType.getId();

        // Get all the businessEntityContactList where contactType equals to contactTypeId
        defaultBusinessEntityContactShouldBeFound("contactTypeId.equals=" + contactTypeId);

        // Get all the businessEntityContactList where contactType equals to contactTypeId + 1
        defaultBusinessEntityContactShouldNotBeFound("contactTypeId.equals=" + (contactTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBusinessEntityContactShouldBeFound(String filter) throws Exception {
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessEntityContact.getId().intValue())));

        // Check, that the count call also returns 1
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBusinessEntityContactShouldNotBeFound(String filter) throws Exception {
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBusinessEntityContact() throws Exception {
        // Get the businessEntityContact
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessEntityContact() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        int databaseSizeBeforeUpdate = businessEntityContactRepository.findAll().size();

        // Update the businessEntityContact
        BusinessEntityContact updatedBusinessEntityContact = businessEntityContactRepository.findById(businessEntityContact.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessEntityContact are not directly saved in db
        em.detach(updatedBusinessEntityContact);
        BusinessEntityContactDTO businessEntityContactDTO = businessEntityContactMapper.toDto(updatedBusinessEntityContact);

        restBusinessEntityContactMockMvc.perform(put("/api/business-entity-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityContactDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessEntityContact in the database
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeUpdate);
        BusinessEntityContact testBusinessEntityContact = businessEntityContactList.get(businessEntityContactList.size() - 1);

        // Validate the BusinessEntityContact in Elasticsearch
        verify(mockBusinessEntityContactSearchRepository, times(1)).save(testBusinessEntityContact);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessEntityContact() throws Exception {
        int databaseSizeBeforeUpdate = businessEntityContactRepository.findAll().size();

        // Create the BusinessEntityContact
        BusinessEntityContactDTO businessEntityContactDTO = businessEntityContactMapper.toDto(businessEntityContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessEntityContactMockMvc.perform(put("/api/business-entity-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessEntityContact in the database
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BusinessEntityContact in Elasticsearch
        verify(mockBusinessEntityContactSearchRepository, times(0)).save(businessEntityContact);
    }

    @Test
    @Transactional
    public void deleteBusinessEntityContact() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        int databaseSizeBeforeDelete = businessEntityContactRepository.findAll().size();

        // Delete the businessEntityContact
        restBusinessEntityContactMockMvc.perform(delete("/api/business-entity-contacts/{id}", businessEntityContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BusinessEntityContact in Elasticsearch
        verify(mockBusinessEntityContactSearchRepository, times(1)).deleteById(businessEntityContact.getId());
    }

    @Test
    @Transactional
    public void searchBusinessEntityContact() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);
        when(mockBusinessEntityContactSearchRepository.search(queryStringQuery("id:" + businessEntityContact.getId())))
            .thenReturn(Collections.singletonList(businessEntityContact));
        // Search the businessEntityContact
        restBusinessEntityContactMockMvc.perform(get("/api/_search/business-entity-contacts?query=id:" + businessEntityContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessEntityContact.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessEntityContact.class);
        BusinessEntityContact businessEntityContact1 = new BusinessEntityContact();
        businessEntityContact1.setId(1L);
        BusinessEntityContact businessEntityContact2 = new BusinessEntityContact();
        businessEntityContact2.setId(businessEntityContact1.getId());
        assertThat(businessEntityContact1).isEqualTo(businessEntityContact2);
        businessEntityContact2.setId(2L);
        assertThat(businessEntityContact1).isNotEqualTo(businessEntityContact2);
        businessEntityContact1.setId(null);
        assertThat(businessEntityContact1).isNotEqualTo(businessEntityContact2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessEntityContactDTO.class);
        BusinessEntityContactDTO businessEntityContactDTO1 = new BusinessEntityContactDTO();
        businessEntityContactDTO1.setId(1L);
        BusinessEntityContactDTO businessEntityContactDTO2 = new BusinessEntityContactDTO();
        assertThat(businessEntityContactDTO1).isNotEqualTo(businessEntityContactDTO2);
        businessEntityContactDTO2.setId(businessEntityContactDTO1.getId());
        assertThat(businessEntityContactDTO1).isEqualTo(businessEntityContactDTO2);
        businessEntityContactDTO2.setId(2L);
        assertThat(businessEntityContactDTO1).isNotEqualTo(businessEntityContactDTO2);
        businessEntityContactDTO1.setId(null);
        assertThat(businessEntityContactDTO1).isNotEqualTo(businessEntityContactDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(businessEntityContactMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(businessEntityContactMapper.fromId(null)).isNull();
    }
}
