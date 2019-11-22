package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ProductOptionService;
import com.epmserver.gateway.domain.ProductOption;
import com.epmserver.gateway.repository.ProductOptionRepository;
import com.epmserver.gateway.repository.search.ProductOptionSearchRepository;
import com.epmserver.gateway.service.dto.ProductOptionDTO;
import com.epmserver.gateway.service.mapper.ProductOptionMapper;
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
 * Service Implementation for managing {@link ProductOption}.
 */
@Service
@Transactional
public class ProductOptionServiceImpl implements ProductOptionService {

    private final Logger log = LoggerFactory.getLogger(ProductOptionServiceImpl.class);

    private final ProductOptionRepository productOptionRepository;

    private final ProductOptionMapper productOptionMapper;

    private final ProductOptionSearchRepository productOptionSearchRepository;

    public ProductOptionServiceImpl(ProductOptionRepository productOptionRepository, ProductOptionMapper productOptionMapper, ProductOptionSearchRepository productOptionSearchRepository) {
        this.productOptionRepository = productOptionRepository;
        this.productOptionMapper = productOptionMapper;
        this.productOptionSearchRepository = productOptionSearchRepository;
    }

    /**
     * Save a productOption.
     *
     * @param productOptionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductOptionDTO save(ProductOptionDTO productOptionDTO) {
        log.debug("Request to save ProductOption : {}", productOptionDTO);
        ProductOption productOption = productOptionMapper.toEntity(productOptionDTO);
        productOption = productOptionRepository.save(productOption);
        ProductOptionDTO result = productOptionMapper.toDto(productOption);
        productOptionSearchRepository.save(productOption);
        return result;
    }

    /**
     * Get all the productOptions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductOptionDTO> findAll() {
        log.debug("Request to get all ProductOptions");
        return productOptionRepository.findAll().stream()
            .map(productOptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productOption by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductOptionDTO> findOne(Long id) {
        log.debug("Request to get ProductOption : {}", id);
        return productOptionRepository.findById(id)
            .map(productOptionMapper::toDto);
    }

    /**
     * Delete the productOption by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductOption : {}", id);
        productOptionRepository.deleteById(id);
        productOptionSearchRepository.deleteById(id);
    }

    /**
     * Search for the productOption corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductOptionDTO> search(String query) {
        log.debug("Request to search ProductOptions for query {}", query);
        return StreamSupport
            .stream(productOptionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productOptionMapper::toDto)
            .collect(Collectors.toList());
    }
}
