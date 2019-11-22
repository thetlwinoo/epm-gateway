package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.CustomerCategoriesService;
import com.epmserver.gateway.domain.CustomerCategories;
import com.epmserver.gateway.repository.CustomerCategoriesRepository;
import com.epmserver.gateway.repository.search.CustomerCategoriesSearchRepository;
import com.epmserver.gateway.service.dto.CustomerCategoriesDTO;
import com.epmserver.gateway.service.mapper.CustomerCategoriesMapper;
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
 * Service Implementation for managing {@link CustomerCategories}.
 */
@Service
@Transactional
public class CustomerCategoriesServiceImpl implements CustomerCategoriesService {

    private final Logger log = LoggerFactory.getLogger(CustomerCategoriesServiceImpl.class);

    private final CustomerCategoriesRepository customerCategoriesRepository;

    private final CustomerCategoriesMapper customerCategoriesMapper;

    private final CustomerCategoriesSearchRepository customerCategoriesSearchRepository;

    public CustomerCategoriesServiceImpl(CustomerCategoriesRepository customerCategoriesRepository, CustomerCategoriesMapper customerCategoriesMapper, CustomerCategoriesSearchRepository customerCategoriesSearchRepository) {
        this.customerCategoriesRepository = customerCategoriesRepository;
        this.customerCategoriesMapper = customerCategoriesMapper;
        this.customerCategoriesSearchRepository = customerCategoriesSearchRepository;
    }

    /**
     * Save a customerCategories.
     *
     * @param customerCategoriesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerCategoriesDTO save(CustomerCategoriesDTO customerCategoriesDTO) {
        log.debug("Request to save CustomerCategories : {}", customerCategoriesDTO);
        CustomerCategories customerCategories = customerCategoriesMapper.toEntity(customerCategoriesDTO);
        customerCategories = customerCategoriesRepository.save(customerCategories);
        CustomerCategoriesDTO result = customerCategoriesMapper.toDto(customerCategories);
        customerCategoriesSearchRepository.save(customerCategories);
        return result;
    }

    /**
     * Get all the customerCategories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerCategoriesDTO> findAll() {
        log.debug("Request to get all CustomerCategories");
        return customerCategoriesRepository.findAll().stream()
            .map(customerCategoriesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerCategories by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerCategoriesDTO> findOne(Long id) {
        log.debug("Request to get CustomerCategories : {}", id);
        return customerCategoriesRepository.findById(id)
            .map(customerCategoriesMapper::toDto);
    }

    /**
     * Delete the customerCategories by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerCategories : {}", id);
        customerCategoriesRepository.deleteById(id);
        customerCategoriesSearchRepository.deleteById(id);
    }

    /**
     * Search for the customerCategories corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerCategoriesDTO> search(String query) {
        log.debug("Request to search CustomerCategories for query {}", query);
        return StreamSupport
            .stream(customerCategoriesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(customerCategoriesMapper::toDto)
            .collect(Collectors.toList());
    }
}
