package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.BusinessEntityAddressService;
import com.epmserver.gateway.domain.BusinessEntityAddress;
import com.epmserver.gateway.repository.BusinessEntityAddressRepository;
import com.epmserver.gateway.repository.search.BusinessEntityAddressSearchRepository;
import com.epmserver.gateway.service.dto.BusinessEntityAddressDTO;
import com.epmserver.gateway.service.mapper.BusinessEntityAddressMapper;
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
 * Service Implementation for managing {@link BusinessEntityAddress}.
 */
@Service
@Transactional
public class BusinessEntityAddressServiceImpl implements BusinessEntityAddressService {

    private final Logger log = LoggerFactory.getLogger(BusinessEntityAddressServiceImpl.class);

    private final BusinessEntityAddressRepository businessEntityAddressRepository;

    private final BusinessEntityAddressMapper businessEntityAddressMapper;

    private final BusinessEntityAddressSearchRepository businessEntityAddressSearchRepository;

    public BusinessEntityAddressServiceImpl(BusinessEntityAddressRepository businessEntityAddressRepository, BusinessEntityAddressMapper businessEntityAddressMapper, BusinessEntityAddressSearchRepository businessEntityAddressSearchRepository) {
        this.businessEntityAddressRepository = businessEntityAddressRepository;
        this.businessEntityAddressMapper = businessEntityAddressMapper;
        this.businessEntityAddressSearchRepository = businessEntityAddressSearchRepository;
    }

    /**
     * Save a businessEntityAddress.
     *
     * @param businessEntityAddressDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BusinessEntityAddressDTO save(BusinessEntityAddressDTO businessEntityAddressDTO) {
        log.debug("Request to save BusinessEntityAddress : {}", businessEntityAddressDTO);
        BusinessEntityAddress businessEntityAddress = businessEntityAddressMapper.toEntity(businessEntityAddressDTO);
        businessEntityAddress = businessEntityAddressRepository.save(businessEntityAddress);
        BusinessEntityAddressDTO result = businessEntityAddressMapper.toDto(businessEntityAddress);
        businessEntityAddressSearchRepository.save(businessEntityAddress);
        return result;
    }

    /**
     * Get all the businessEntityAddresses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusinessEntityAddressDTO> findAll() {
        log.debug("Request to get all BusinessEntityAddresses");
        return businessEntityAddressRepository.findAll().stream()
            .map(businessEntityAddressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one businessEntityAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessEntityAddressDTO> findOne(Long id) {
        log.debug("Request to get BusinessEntityAddress : {}", id);
        return businessEntityAddressRepository.findById(id)
            .map(businessEntityAddressMapper::toDto);
    }

    /**
     * Delete the businessEntityAddress by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessEntityAddress : {}", id);
        businessEntityAddressRepository.deleteById(id);
        businessEntityAddressSearchRepository.deleteById(id);
    }

    /**
     * Search for the businessEntityAddress corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusinessEntityAddressDTO> search(String query) {
        log.debug("Request to search BusinessEntityAddresses for query {}", query);
        return StreamSupport
            .stream(businessEntityAddressSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(businessEntityAddressMapper::toDto)
            .collect(Collectors.toList());
    }
}
