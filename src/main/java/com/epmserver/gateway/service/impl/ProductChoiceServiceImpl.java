package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ProductChoiceService;
import com.epmserver.gateway.domain.ProductChoice;
import com.epmserver.gateway.repository.ProductChoiceRepository;
import com.epmserver.gateway.repository.search.ProductChoiceSearchRepository;
import com.epmserver.gateway.service.dto.ProductChoiceDTO;
import com.epmserver.gateway.service.mapper.ProductChoiceMapper;
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
 * Service Implementation for managing {@link ProductChoice}.
 */
@Service
@Transactional
public class ProductChoiceServiceImpl implements ProductChoiceService {

    private final Logger log = LoggerFactory.getLogger(ProductChoiceServiceImpl.class);

    private final ProductChoiceRepository productChoiceRepository;

    private final ProductChoiceMapper productChoiceMapper;

    private final ProductChoiceSearchRepository productChoiceSearchRepository;

    public ProductChoiceServiceImpl(ProductChoiceRepository productChoiceRepository, ProductChoiceMapper productChoiceMapper, ProductChoiceSearchRepository productChoiceSearchRepository) {
        this.productChoiceRepository = productChoiceRepository;
        this.productChoiceMapper = productChoiceMapper;
        this.productChoiceSearchRepository = productChoiceSearchRepository;
    }

    /**
     * Save a productChoice.
     *
     * @param productChoiceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductChoiceDTO save(ProductChoiceDTO productChoiceDTO) {
        log.debug("Request to save ProductChoice : {}", productChoiceDTO);
        ProductChoice productChoice = productChoiceMapper.toEntity(productChoiceDTO);
        productChoice = productChoiceRepository.save(productChoice);
        ProductChoiceDTO result = productChoiceMapper.toDto(productChoice);
        productChoiceSearchRepository.save(productChoice);
        return result;
    }

    /**
     * Get all the productChoices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductChoiceDTO> findAll() {
        log.debug("Request to get all ProductChoices");
        return productChoiceRepository.findAll().stream()
            .map(productChoiceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productChoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductChoiceDTO> findOne(Long id) {
        log.debug("Request to get ProductChoice : {}", id);
        return productChoiceRepository.findById(id)
            .map(productChoiceMapper::toDto);
    }

    /**
     * Delete the productChoice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductChoice : {}", id);
        productChoiceRepository.deleteById(id);
        productChoiceSearchRepository.deleteById(id);
    }

    /**
     * Search for the productChoice corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductChoiceDTO> search(String query) {
        log.debug("Request to search ProductChoices for query {}", query);
        return StreamSupport
            .stream(productChoiceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productChoiceMapper::toDto)
            .collect(Collectors.toList());
    }
}
