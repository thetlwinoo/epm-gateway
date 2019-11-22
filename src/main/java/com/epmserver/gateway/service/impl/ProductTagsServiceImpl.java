package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ProductTagsService;
import com.epmserver.gateway.domain.ProductTags;
import com.epmserver.gateway.repository.ProductTagsRepository;
import com.epmserver.gateway.repository.search.ProductTagsSearchRepository;
import com.epmserver.gateway.service.dto.ProductTagsDTO;
import com.epmserver.gateway.service.mapper.ProductTagsMapper;
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
 * Service Implementation for managing {@link ProductTags}.
 */
@Service
@Transactional
public class ProductTagsServiceImpl implements ProductTagsService {

    private final Logger log = LoggerFactory.getLogger(ProductTagsServiceImpl.class);

    private final ProductTagsRepository productTagsRepository;

    private final ProductTagsMapper productTagsMapper;

    private final ProductTagsSearchRepository productTagsSearchRepository;

    public ProductTagsServiceImpl(ProductTagsRepository productTagsRepository, ProductTagsMapper productTagsMapper, ProductTagsSearchRepository productTagsSearchRepository) {
        this.productTagsRepository = productTagsRepository;
        this.productTagsMapper = productTagsMapper;
        this.productTagsSearchRepository = productTagsSearchRepository;
    }

    /**
     * Save a productTags.
     *
     * @param productTagsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductTagsDTO save(ProductTagsDTO productTagsDTO) {
        log.debug("Request to save ProductTags : {}", productTagsDTO);
        ProductTags productTags = productTagsMapper.toEntity(productTagsDTO);
        productTags = productTagsRepository.save(productTags);
        ProductTagsDTO result = productTagsMapper.toDto(productTags);
        productTagsSearchRepository.save(productTags);
        return result;
    }

    /**
     * Get all the productTags.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductTagsDTO> findAll() {
        log.debug("Request to get all ProductTags");
        return productTagsRepository.findAll().stream()
            .map(productTagsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productTags by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductTagsDTO> findOne(Long id) {
        log.debug("Request to get ProductTags : {}", id);
        return productTagsRepository.findById(id)
            .map(productTagsMapper::toDto);
    }

    /**
     * Delete the productTags by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductTags : {}", id);
        productTagsRepository.deleteById(id);
        productTagsSearchRepository.deleteById(id);
    }

    /**
     * Search for the productTags corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductTagsDTO> search(String query) {
        log.debug("Request to search ProductTags for query {}", query);
        return StreamSupport
            .stream(productTagsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productTagsMapper::toDto)
            .collect(Collectors.toList());
    }
}
