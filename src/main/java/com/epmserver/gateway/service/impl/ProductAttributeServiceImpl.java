package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ProductAttributeService;
import com.epmserver.gateway.domain.ProductAttribute;
import com.epmserver.gateway.repository.ProductAttributeRepository;
import com.epmserver.gateway.repository.search.ProductAttributeSearchRepository;
import com.epmserver.gateway.service.dto.ProductAttributeDTO;
import com.epmserver.gateway.service.mapper.ProductAttributeMapper;
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
 * Service Implementation for managing {@link ProductAttribute}.
 */
@Service
@Transactional
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeServiceImpl.class);

    private final ProductAttributeRepository productAttributeRepository;

    private final ProductAttributeMapper productAttributeMapper;

    private final ProductAttributeSearchRepository productAttributeSearchRepository;

    public ProductAttributeServiceImpl(ProductAttributeRepository productAttributeRepository, ProductAttributeMapper productAttributeMapper, ProductAttributeSearchRepository productAttributeSearchRepository) {
        this.productAttributeRepository = productAttributeRepository;
        this.productAttributeMapper = productAttributeMapper;
        this.productAttributeSearchRepository = productAttributeSearchRepository;
    }

    /**
     * Save a productAttribute.
     *
     * @param productAttributeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductAttributeDTO save(ProductAttributeDTO productAttributeDTO) {
        log.debug("Request to save ProductAttribute : {}", productAttributeDTO);
        ProductAttribute productAttribute = productAttributeMapper.toEntity(productAttributeDTO);
        productAttribute = productAttributeRepository.save(productAttribute);
        ProductAttributeDTO result = productAttributeMapper.toDto(productAttribute);
        productAttributeSearchRepository.save(productAttribute);
        return result;
    }

    /**
     * Get all the productAttributes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductAttributeDTO> findAll() {
        log.debug("Request to get all ProductAttributes");
        return productAttributeRepository.findAll().stream()
            .map(productAttributeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productAttribute by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductAttributeDTO> findOne(Long id) {
        log.debug("Request to get ProductAttribute : {}", id);
        return productAttributeRepository.findById(id)
            .map(productAttributeMapper::toDto);
    }

    /**
     * Delete the productAttribute by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductAttribute : {}", id);
        productAttributeRepository.deleteById(id);
        productAttributeSearchRepository.deleteById(id);
    }

    /**
     * Search for the productAttribute corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductAttributeDTO> search(String query) {
        log.debug("Request to search ProductAttributes for query {}", query);
        return StreamSupport
            .stream(productAttributeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productAttributeMapper::toDto)
            .collect(Collectors.toList());
    }
}
