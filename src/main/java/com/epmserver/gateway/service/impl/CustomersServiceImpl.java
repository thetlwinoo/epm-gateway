package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.CustomersService;
import com.epmserver.gateway.domain.Customers;
import com.epmserver.gateway.repository.CustomersRepository;
import com.epmserver.gateway.repository.search.CustomersSearchRepository;
import com.epmserver.gateway.service.dto.CustomersDTO;
import com.epmserver.gateway.service.mapper.CustomersMapper;
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
 * Service Implementation for managing {@link Customers}.
 */
@Service
@Transactional
public class CustomersServiceImpl implements CustomersService {

    private final Logger log = LoggerFactory.getLogger(CustomersServiceImpl.class);

    private final CustomersRepository customersRepository;

    private final CustomersMapper customersMapper;

    private final CustomersSearchRepository customersSearchRepository;

    public CustomersServiceImpl(CustomersRepository customersRepository, CustomersMapper customersMapper, CustomersSearchRepository customersSearchRepository) {
        this.customersRepository = customersRepository;
        this.customersMapper = customersMapper;
        this.customersSearchRepository = customersSearchRepository;
    }

    /**
     * Save a customers.
     *
     * @param customersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomersDTO save(CustomersDTO customersDTO) {
        log.debug("Request to save Customers : {}", customersDTO);
        Customers customers = customersMapper.toEntity(customersDTO);
        customers = customersRepository.save(customers);
        CustomersDTO result = customersMapper.toDto(customers);
        customersSearchRepository.save(customers);
        return result;
    }

    /**
     * Get all the customers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomersDTO> findAll() {
        log.debug("Request to get all Customers");
        return customersRepository.findAll().stream()
            .map(customersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomersDTO> findOne(Long id) {
        log.debug("Request to get Customers : {}", id);
        return customersRepository.findById(id)
            .map(customersMapper::toDto);
    }

    /**
     * Delete the customers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customers : {}", id);
        customersRepository.deleteById(id);
        customersSearchRepository.deleteById(id);
    }

    /**
     * Search for the customers corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomersDTO> search(String query) {
        log.debug("Request to search Customers for query {}", query);
        return StreamSupport
            .stream(customersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(customersMapper::toDto)
            .collect(Collectors.toList());
    }
}
