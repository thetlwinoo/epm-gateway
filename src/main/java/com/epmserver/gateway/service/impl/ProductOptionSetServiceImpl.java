package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ProductOptionSetService;
import com.epmserver.gateway.domain.ProductOptionSet;
import com.epmserver.gateway.repository.ProductOptionSetRepository;
import com.epmserver.gateway.repository.search.ProductOptionSetSearchRepository;
import com.epmserver.gateway.service.dto.ProductOptionSetDTO;
import com.epmserver.gateway.service.mapper.ProductOptionSetMapper;
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
 * Service Implementation for managing {@link ProductOptionSet}.
 */
@Service
@Transactional
public class ProductOptionSetServiceImpl implements ProductOptionSetService {

    private final Logger log = LoggerFactory.getLogger(ProductOptionSetServiceImpl.class);

    private final ProductOptionSetRepository productOptionSetRepository;

    private final ProductOptionSetMapper productOptionSetMapper;

    private final ProductOptionSetSearchRepository productOptionSetSearchRepository;

    public ProductOptionSetServiceImpl(ProductOptionSetRepository productOptionSetRepository, ProductOptionSetMapper productOptionSetMapper, ProductOptionSetSearchRepository productOptionSetSearchRepository) {
        this.productOptionSetRepository = productOptionSetRepository;
        this.productOptionSetMapper = productOptionSetMapper;
        this.productOptionSetSearchRepository = productOptionSetSearchRepository;
    }

    /**
     * Save a productOptionSet.
     *
     * @param productOptionSetDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductOptionSetDTO save(ProductOptionSetDTO productOptionSetDTO) {
        log.debug("Request to save ProductOptionSet : {}", productOptionSetDTO);
        ProductOptionSet productOptionSet = productOptionSetMapper.toEntity(productOptionSetDTO);
        productOptionSet = productOptionSetRepository.save(productOptionSet);
        ProductOptionSetDTO result = productOptionSetMapper.toDto(productOptionSet);
        productOptionSetSearchRepository.save(productOptionSet);
        return result;
    }

    /**
     * Get all the productOptionSets.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductOptionSetDTO> findAll() {
        log.debug("Request to get all ProductOptionSets");
        return productOptionSetRepository.findAll().stream()
            .map(productOptionSetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productOptionSet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductOptionSetDTO> findOne(Long id) {
        log.debug("Request to get ProductOptionSet : {}", id);
        return productOptionSetRepository.findById(id)
            .map(productOptionSetMapper::toDto);
    }

    /**
     * Delete the productOptionSet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductOptionSet : {}", id);
        productOptionSetRepository.deleteById(id);
        productOptionSetSearchRepository.deleteById(id);
    }

    /**
     * Search for the productOptionSet corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductOptionSetDTO> search(String query) {
        log.debug("Request to search ProductOptionSets for query {}", query);
        return StreamSupport
            .stream(productOptionSetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productOptionSetMapper::toDto)
            .collect(Collectors.toList());
    }
}
