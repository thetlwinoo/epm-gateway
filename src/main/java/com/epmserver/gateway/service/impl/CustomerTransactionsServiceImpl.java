package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.CustomerTransactionsService;
import com.epmserver.gateway.domain.CustomerTransactions;
import com.epmserver.gateway.repository.CustomerTransactionsRepository;
import com.epmserver.gateway.repository.search.CustomerTransactionsSearchRepository;
import com.epmserver.gateway.service.dto.CustomerTransactionsDTO;
import com.epmserver.gateway.service.mapper.CustomerTransactionsMapper;
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
 * Service Implementation for managing {@link CustomerTransactions}.
 */
@Service
@Transactional
public class CustomerTransactionsServiceImpl implements CustomerTransactionsService {

    private final Logger log = LoggerFactory.getLogger(CustomerTransactionsServiceImpl.class);

    private final CustomerTransactionsRepository customerTransactionsRepository;

    private final CustomerTransactionsMapper customerTransactionsMapper;

    private final CustomerTransactionsSearchRepository customerTransactionsSearchRepository;

    public CustomerTransactionsServiceImpl(CustomerTransactionsRepository customerTransactionsRepository, CustomerTransactionsMapper customerTransactionsMapper, CustomerTransactionsSearchRepository customerTransactionsSearchRepository) {
        this.customerTransactionsRepository = customerTransactionsRepository;
        this.customerTransactionsMapper = customerTransactionsMapper;
        this.customerTransactionsSearchRepository = customerTransactionsSearchRepository;
    }

    /**
     * Save a customerTransactions.
     *
     * @param customerTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerTransactionsDTO save(CustomerTransactionsDTO customerTransactionsDTO) {
        log.debug("Request to save CustomerTransactions : {}", customerTransactionsDTO);
        CustomerTransactions customerTransactions = customerTransactionsMapper.toEntity(customerTransactionsDTO);
        customerTransactions = customerTransactionsRepository.save(customerTransactions);
        CustomerTransactionsDTO result = customerTransactionsMapper.toDto(customerTransactions);
        customerTransactionsSearchRepository.save(customerTransactions);
        return result;
    }

    /**
     * Get all the customerTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerTransactionsDTO> findAll() {
        log.debug("Request to get all CustomerTransactions");
        return customerTransactionsRepository.findAll().stream()
            .map(customerTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerTransactionsDTO> findOne(Long id) {
        log.debug("Request to get CustomerTransactions : {}", id);
        return customerTransactionsRepository.findById(id)
            .map(customerTransactionsMapper::toDto);
    }

    /**
     * Delete the customerTransactions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerTransactions : {}", id);
        customerTransactionsRepository.deleteById(id);
        customerTransactionsSearchRepository.deleteById(id);
    }

    /**
     * Search for the customerTransactions corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerTransactionsDTO> search(String query) {
        log.debug("Request to search CustomerTransactions for query {}", query);
        return StreamSupport
            .stream(customerTransactionsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(customerTransactionsMapper::toDto)
            .collect(Collectors.toList());
    }
}
