package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ProductBrandService;
import com.epmserver.gateway.domain.ProductBrand;
import com.epmserver.gateway.repository.ProductBrandRepository;
import com.epmserver.gateway.repository.search.ProductBrandSearchRepository;
import com.epmserver.gateway.service.dto.ProductBrandDTO;
import com.epmserver.gateway.service.mapper.ProductBrandMapper;
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
 * Service Implementation for managing {@link ProductBrand}.
 */
@Service
@Transactional
public class ProductBrandServiceImpl implements ProductBrandService {

    private final Logger log = LoggerFactory.getLogger(ProductBrandServiceImpl.class);

    private final ProductBrandRepository productBrandRepository;

    private final ProductBrandMapper productBrandMapper;

    private final ProductBrandSearchRepository productBrandSearchRepository;

    public ProductBrandServiceImpl(ProductBrandRepository productBrandRepository, ProductBrandMapper productBrandMapper, ProductBrandSearchRepository productBrandSearchRepository) {
        this.productBrandRepository = productBrandRepository;
        this.productBrandMapper = productBrandMapper;
        this.productBrandSearchRepository = productBrandSearchRepository;
    }

    /**
     * Save a productBrand.
     *
     * @param productBrandDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductBrandDTO save(ProductBrandDTO productBrandDTO) {
        log.debug("Request to save ProductBrand : {}", productBrandDTO);
        ProductBrand productBrand = productBrandMapper.toEntity(productBrandDTO);
        productBrand = productBrandRepository.save(productBrand);
        ProductBrandDTO result = productBrandMapper.toDto(productBrand);
        productBrandSearchRepository.save(productBrand);
        return result;
    }

    /**
     * Get all the productBrands.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductBrandDTO> findAll() {
        log.debug("Request to get all ProductBrands");
        return productBrandRepository.findAll().stream()
            .map(productBrandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productBrand by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductBrandDTO> findOne(Long id) {
        log.debug("Request to get ProductBrand : {}", id);
        return productBrandRepository.findById(id)
            .map(productBrandMapper::toDto);
    }

    /**
     * Delete the productBrand by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductBrand : {}", id);
        productBrandRepository.deleteById(id);
        productBrandSearchRepository.deleteById(id);
    }

    /**
     * Search for the productBrand corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductBrandDTO> search(String query) {
        log.debug("Request to search ProductBrands for query {}", query);
        return StreamSupport
            .stream(productBrandSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productBrandMapper::toDto)
            .collect(Collectors.toList());
    }
}
