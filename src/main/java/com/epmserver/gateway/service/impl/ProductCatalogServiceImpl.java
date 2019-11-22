package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ProductCatalogService;
import com.epmserver.gateway.domain.ProductCatalog;
import com.epmserver.gateway.repository.ProductCatalogRepository;
import com.epmserver.gateway.repository.search.ProductCatalogSearchRepository;
import com.epmserver.gateway.service.dto.ProductCatalogDTO;
import com.epmserver.gateway.service.mapper.ProductCatalogMapper;
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
 * Service Implementation for managing {@link ProductCatalog}.
 */
@Service
@Transactional
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final Logger log = LoggerFactory.getLogger(ProductCatalogServiceImpl.class);

    private final ProductCatalogRepository productCatalogRepository;

    private final ProductCatalogMapper productCatalogMapper;

    private final ProductCatalogSearchRepository productCatalogSearchRepository;

    public ProductCatalogServiceImpl(ProductCatalogRepository productCatalogRepository, ProductCatalogMapper productCatalogMapper, ProductCatalogSearchRepository productCatalogSearchRepository) {
        this.productCatalogRepository = productCatalogRepository;
        this.productCatalogMapper = productCatalogMapper;
        this.productCatalogSearchRepository = productCatalogSearchRepository;
    }

    /**
     * Save a productCatalog.
     *
     * @param productCatalogDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductCatalogDTO save(ProductCatalogDTO productCatalogDTO) {
        log.debug("Request to save ProductCatalog : {}", productCatalogDTO);
        ProductCatalog productCatalog = productCatalogMapper.toEntity(productCatalogDTO);
        productCatalog = productCatalogRepository.save(productCatalog);
        ProductCatalogDTO result = productCatalogMapper.toDto(productCatalog);
        productCatalogSearchRepository.save(productCatalog);
        return result;
    }

    /**
     * Get all the productCatalogs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductCatalogDTO> findAll() {
        log.debug("Request to get all ProductCatalogs");
        return productCatalogRepository.findAll().stream()
            .map(productCatalogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productCatalog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductCatalogDTO> findOne(Long id) {
        log.debug("Request to get ProductCatalog : {}", id);
        return productCatalogRepository.findById(id)
            .map(productCatalogMapper::toDto);
    }

    /**
     * Delete the productCatalog by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductCatalog : {}", id);
        productCatalogRepository.deleteById(id);
        productCatalogSearchRepository.deleteById(id);
    }

    /**
     * Search for the productCatalog corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductCatalogDTO> search(String query) {
        log.debug("Request to search ProductCatalogs for query {}", query);
        return StreamSupport
            .stream(productCatalogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productCatalogMapper::toDto)
            .collect(Collectors.toList());
    }
}
