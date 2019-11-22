package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ProductAttributeSetService;
import com.epmserver.gateway.domain.ProductAttributeSet;
import com.epmserver.gateway.repository.ProductAttributeSetRepository;
import com.epmserver.gateway.repository.search.ProductAttributeSetSearchRepository;
import com.epmserver.gateway.service.dto.ProductAttributeSetDTO;
import com.epmserver.gateway.service.mapper.ProductAttributeSetMapper;
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
 * Service Implementation for managing {@link ProductAttributeSet}.
 */
@Service
@Transactional
public class ProductAttributeSetServiceImpl implements ProductAttributeSetService {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeSetServiceImpl.class);

    private final ProductAttributeSetRepository productAttributeSetRepository;

    private final ProductAttributeSetMapper productAttributeSetMapper;

    private final ProductAttributeSetSearchRepository productAttributeSetSearchRepository;

    public ProductAttributeSetServiceImpl(ProductAttributeSetRepository productAttributeSetRepository, ProductAttributeSetMapper productAttributeSetMapper, ProductAttributeSetSearchRepository productAttributeSetSearchRepository) {
        this.productAttributeSetRepository = productAttributeSetRepository;
        this.productAttributeSetMapper = productAttributeSetMapper;
        this.productAttributeSetSearchRepository = productAttributeSetSearchRepository;
    }

    /**
     * Save a productAttributeSet.
     *
     * @param productAttributeSetDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductAttributeSetDTO save(ProductAttributeSetDTO productAttributeSetDTO) {
        log.debug("Request to save ProductAttributeSet : {}", productAttributeSetDTO);
        ProductAttributeSet productAttributeSet = productAttributeSetMapper.toEntity(productAttributeSetDTO);
        productAttributeSet = productAttributeSetRepository.save(productAttributeSet);
        ProductAttributeSetDTO result = productAttributeSetMapper.toDto(productAttributeSet);
        productAttributeSetSearchRepository.save(productAttributeSet);
        return result;
    }

    /**
     * Get all the productAttributeSets.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductAttributeSetDTO> findAll() {
        log.debug("Request to get all ProductAttributeSets");
        return productAttributeSetRepository.findAll().stream()
            .map(productAttributeSetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productAttributeSet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductAttributeSetDTO> findOne(Long id) {
        log.debug("Request to get ProductAttributeSet : {}", id);
        return productAttributeSetRepository.findById(id)
            .map(productAttributeSetMapper::toDto);
    }

    /**
     * Delete the productAttributeSet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductAttributeSet : {}", id);
        productAttributeSetRepository.deleteById(id);
        productAttributeSetSearchRepository.deleteById(id);
    }

    /**
     * Search for the productAttributeSet corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductAttributeSetDTO> search(String query) {
        log.debug("Request to search ProductAttributeSets for query {}", query);
        return StreamSupport
            .stream(productAttributeSetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productAttributeSetMapper::toDto)
            .collect(Collectors.toList());
    }
}
