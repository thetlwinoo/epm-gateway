package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ProductSetService;
import com.epmserver.gateway.domain.ProductSet;
import com.epmserver.gateway.repository.ProductSetRepository;
import com.epmserver.gateway.repository.search.ProductSetSearchRepository;
import com.epmserver.gateway.service.dto.ProductSetDTO;
import com.epmserver.gateway.service.mapper.ProductSetMapper;
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
 * Service Implementation for managing {@link ProductSet}.
 */
@Service
@Transactional
public class ProductSetServiceImpl implements ProductSetService {

    private final Logger log = LoggerFactory.getLogger(ProductSetServiceImpl.class);

    private final ProductSetRepository productSetRepository;

    private final ProductSetMapper productSetMapper;

    private final ProductSetSearchRepository productSetSearchRepository;

    public ProductSetServiceImpl(ProductSetRepository productSetRepository, ProductSetMapper productSetMapper, ProductSetSearchRepository productSetSearchRepository) {
        this.productSetRepository = productSetRepository;
        this.productSetMapper = productSetMapper;
        this.productSetSearchRepository = productSetSearchRepository;
    }

    /**
     * Save a productSet.
     *
     * @param productSetDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductSetDTO save(ProductSetDTO productSetDTO) {
        log.debug("Request to save ProductSet : {}", productSetDTO);
        ProductSet productSet = productSetMapper.toEntity(productSetDTO);
        productSet = productSetRepository.save(productSet);
        ProductSetDTO result = productSetMapper.toDto(productSet);
        productSetSearchRepository.save(productSet);
        return result;
    }

    /**
     * Get all the productSets.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductSetDTO> findAll() {
        log.debug("Request to get all ProductSets");
        return productSetRepository.findAll().stream()
            .map(productSetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productSet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSetDTO> findOne(Long id) {
        log.debug("Request to get ProductSet : {}", id);
        return productSetRepository.findById(id)
            .map(productSetMapper::toDto);
    }

    /**
     * Delete the productSet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductSet : {}", id);
        productSetRepository.deleteById(id);
        productSetSearchRepository.deleteById(id);
    }

    /**
     * Search for the productSet corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductSetDTO> search(String query) {
        log.debug("Request to search ProductSets for query {}", query);
        return StreamSupport
            .stream(productSetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productSetMapper::toDto)
            .collect(Collectors.toList());
    }
}
