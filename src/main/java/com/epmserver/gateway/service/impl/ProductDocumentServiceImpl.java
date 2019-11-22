package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ProductDocumentService;
import com.epmserver.gateway.domain.ProductDocument;
import com.epmserver.gateway.repository.ProductDocumentRepository;
import com.epmserver.gateway.repository.search.ProductDocumentSearchRepository;
import com.epmserver.gateway.service.dto.ProductDocumentDTO;
import com.epmserver.gateway.service.mapper.ProductDocumentMapper;
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
 * Service Implementation for managing {@link ProductDocument}.
 */
@Service
@Transactional
public class ProductDocumentServiceImpl implements ProductDocumentService {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentServiceImpl.class);

    private final ProductDocumentRepository productDocumentRepository;

    private final ProductDocumentMapper productDocumentMapper;

    private final ProductDocumentSearchRepository productDocumentSearchRepository;

    public ProductDocumentServiceImpl(ProductDocumentRepository productDocumentRepository, ProductDocumentMapper productDocumentMapper, ProductDocumentSearchRepository productDocumentSearchRepository) {
        this.productDocumentRepository = productDocumentRepository;
        this.productDocumentMapper = productDocumentMapper;
        this.productDocumentSearchRepository = productDocumentSearchRepository;
    }

    /**
     * Save a productDocument.
     *
     * @param productDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductDocumentDTO save(ProductDocumentDTO productDocumentDTO) {
        log.debug("Request to save ProductDocument : {}", productDocumentDTO);
        ProductDocument productDocument = productDocumentMapper.toEntity(productDocumentDTO);
        productDocument = productDocumentRepository.save(productDocument);
        ProductDocumentDTO result = productDocumentMapper.toDto(productDocument);
        productDocumentSearchRepository.save(productDocument);
        return result;
    }

    /**
     * Get all the productDocuments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductDocumentDTO> findAll() {
        log.debug("Request to get all ProductDocuments");
        return productDocumentRepository.findAll().stream()
            .map(productDocumentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDocumentDTO> findOne(Long id) {
        log.debug("Request to get ProductDocument : {}", id);
        return productDocumentRepository.findById(id)
            .map(productDocumentMapper::toDto);
    }

    /**
     * Delete the productDocument by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductDocument : {}", id);
        productDocumentRepository.deleteById(id);
        productDocumentSearchRepository.deleteById(id);
    }

    /**
     * Search for the productDocument corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductDocumentDTO> search(String query) {
        log.debug("Request to search ProductDocuments for query {}", query);
        return StreamSupport
            .stream(productDocumentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productDocumentMapper::toDto)
            .collect(Collectors.toList());
    }
}
