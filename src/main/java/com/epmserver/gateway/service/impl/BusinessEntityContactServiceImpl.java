package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.BusinessEntityContactService;
import com.epmserver.gateway.domain.BusinessEntityContact;
import com.epmserver.gateway.repository.BusinessEntityContactRepository;
import com.epmserver.gateway.repository.search.BusinessEntityContactSearchRepository;
import com.epmserver.gateway.service.dto.BusinessEntityContactDTO;
import com.epmserver.gateway.service.mapper.BusinessEntityContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link BusinessEntityContact}.
 */
@Service
@Transactional
public class BusinessEntityContactServiceImpl implements BusinessEntityContactService {

    private final Logger log = LoggerFactory.getLogger(BusinessEntityContactServiceImpl.class);

    private final BusinessEntityContactRepository businessEntityContactRepository;

    private final BusinessEntityContactMapper businessEntityContactMapper;

    private final BusinessEntityContactSearchRepository businessEntityContactSearchRepository;

    public BusinessEntityContactServiceImpl(BusinessEntityContactRepository businessEntityContactRepository, BusinessEntityContactMapper businessEntityContactMapper, BusinessEntityContactSearchRepository businessEntityContactSearchRepository) {
        this.businessEntityContactRepository = businessEntityContactRepository;
        this.businessEntityContactMapper = businessEntityContactMapper;
        this.businessEntityContactSearchRepository = businessEntityContactSearchRepository;
    }

    /**
     * Save a businessEntityContact.
     *
     * @param businessEntityContactDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BusinessEntityContactDTO save(BusinessEntityContactDTO businessEntityContactDTO) {
        log.debug("Request to save BusinessEntityContact : {}", businessEntityContactDTO);
        BusinessEntityContact businessEntityContact = businessEntityContactMapper.toEntity(businessEntityContactDTO);
        businessEntityContact = businessEntityContactRepository.save(businessEntityContact);
        BusinessEntityContactDTO result = businessEntityContactMapper.toDto(businessEntityContact);
        businessEntityContactSearchRepository.save(businessEntityContact);
        return result;
    }

    /**
     * Get all the businessEntityContacts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusinessEntityContactDTO> findAll() {
        log.debug("Request to get all BusinessEntityContacts");
        return businessEntityContactRepository.findAll().stream()
            .map(businessEntityContactMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one businessEntityContact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessEntityContactDTO> findOne(Long id) {
        log.debug("Request to get BusinessEntityContact : {}", id);
        return businessEntityContactRepository.findById(id)
            .map(businessEntityContactMapper::toDto);
    }

    /**
     * Delete the businessEntityContact by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessEntityContact : {}", id);
        businessEntityContactRepository.deleteById(id);
        businessEntityContactSearchRepository.deleteById(id);
    }

    /**
     * Search for the businessEntityContact corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusinessEntityContactDTO> search(String query) {
        log.debug("Request to search BusinessEntityContacts for query {}", query);
        return StreamSupport
            .stream(businessEntityContactSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(businessEntityContactMapper::toDto)
            .collect(Collectors.toList());
    }
}
