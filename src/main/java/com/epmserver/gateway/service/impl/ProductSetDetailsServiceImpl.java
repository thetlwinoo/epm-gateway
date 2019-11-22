package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ProductSetDetailsService;
import com.epmserver.gateway.domain.ProductSetDetails;
import com.epmserver.gateway.repository.ProductSetDetailsRepository;
import com.epmserver.gateway.repository.search.ProductSetDetailsSearchRepository;
import com.epmserver.gateway.service.dto.ProductSetDetailsDTO;
import com.epmserver.gateway.service.mapper.ProductSetDetailsMapper;
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
 * Service Implementation for managing {@link ProductSetDetails}.
 */
@Service
@Transactional
public class ProductSetDetailsServiceImpl implements ProductSetDetailsService {

    private final Logger log = LoggerFactory.getLogger(ProductSetDetailsServiceImpl.class);

    private final ProductSetDetailsRepository productSetDetailsRepository;

    private final ProductSetDetailsMapper productSetDetailsMapper;

    private final ProductSetDetailsSearchRepository productSetDetailsSearchRepository;

    public ProductSetDetailsServiceImpl(ProductSetDetailsRepository productSetDetailsRepository, ProductSetDetailsMapper productSetDetailsMapper, ProductSetDetailsSearchRepository productSetDetailsSearchRepository) {
        this.productSetDetailsRepository = productSetDetailsRepository;
        this.productSetDetailsMapper = productSetDetailsMapper;
        this.productSetDetailsSearchRepository = productSetDetailsSearchRepository;
    }

    /**
     * Save a productSetDetails.
     *
     * @param productSetDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductSetDetailsDTO save(ProductSetDetailsDTO productSetDetailsDTO) {
        log.debug("Request to save ProductSetDetails : {}", productSetDetailsDTO);
        ProductSetDetails productSetDetails = productSetDetailsMapper.toEntity(productSetDetailsDTO);
        productSetDetails = productSetDetailsRepository.save(productSetDetails);
        ProductSetDetailsDTO result = productSetDetailsMapper.toDto(productSetDetails);
        productSetDetailsSearchRepository.save(productSetDetails);
        return result;
    }

    /**
     * Get all the productSetDetails.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductSetDetailsDTO> findAll() {
        log.debug("Request to get all ProductSetDetails");
        return productSetDetailsRepository.findAll().stream()
            .map(productSetDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productSetDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSetDetailsDTO> findOne(Long id) {
        log.debug("Request to get ProductSetDetails : {}", id);
        return productSetDetailsRepository.findById(id)
            .map(productSetDetailsMapper::toDto);
    }

    /**
     * Delete the productSetDetails by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductSetDetails : {}", id);
        productSetDetailsRepository.deleteById(id);
        productSetDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the productSetDetails corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductSetDetailsDTO> search(String query) {
        log.debug("Request to search ProductSetDetails for query {}", query);
        return StreamSupport
            .stream(productSetDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productSetDetailsMapper::toDto)
            .collect(Collectors.toList());
    }
}
