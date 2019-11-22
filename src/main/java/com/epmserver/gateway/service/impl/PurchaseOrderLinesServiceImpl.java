package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.PurchaseOrderLinesService;
import com.epmserver.gateway.domain.PurchaseOrderLines;
import com.epmserver.gateway.repository.PurchaseOrderLinesRepository;
import com.epmserver.gateway.repository.search.PurchaseOrderLinesSearchRepository;
import com.epmserver.gateway.service.dto.PurchaseOrderLinesDTO;
import com.epmserver.gateway.service.mapper.PurchaseOrderLinesMapper;
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
 * Service Implementation for managing {@link PurchaseOrderLines}.
 */
@Service
@Transactional
public class PurchaseOrderLinesServiceImpl implements PurchaseOrderLinesService {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderLinesServiceImpl.class);

    private final PurchaseOrderLinesRepository purchaseOrderLinesRepository;

    private final PurchaseOrderLinesMapper purchaseOrderLinesMapper;

    private final PurchaseOrderLinesSearchRepository purchaseOrderLinesSearchRepository;

    public PurchaseOrderLinesServiceImpl(PurchaseOrderLinesRepository purchaseOrderLinesRepository, PurchaseOrderLinesMapper purchaseOrderLinesMapper, PurchaseOrderLinesSearchRepository purchaseOrderLinesSearchRepository) {
        this.purchaseOrderLinesRepository = purchaseOrderLinesRepository;
        this.purchaseOrderLinesMapper = purchaseOrderLinesMapper;
        this.purchaseOrderLinesSearchRepository = purchaseOrderLinesSearchRepository;
    }

    /**
     * Save a purchaseOrderLines.
     *
     * @param purchaseOrderLinesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PurchaseOrderLinesDTO save(PurchaseOrderLinesDTO purchaseOrderLinesDTO) {
        log.debug("Request to save PurchaseOrderLines : {}", purchaseOrderLinesDTO);
        PurchaseOrderLines purchaseOrderLines = purchaseOrderLinesMapper.toEntity(purchaseOrderLinesDTO);
        purchaseOrderLines = purchaseOrderLinesRepository.save(purchaseOrderLines);
        PurchaseOrderLinesDTO result = purchaseOrderLinesMapper.toDto(purchaseOrderLines);
        purchaseOrderLinesSearchRepository.save(purchaseOrderLines);
        return result;
    }

    /**
     * Get all the purchaseOrderLines.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrderLinesDTO> findAll() {
        log.debug("Request to get all PurchaseOrderLines");
        return purchaseOrderLinesRepository.findAll().stream()
            .map(purchaseOrderLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one purchaseOrderLines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseOrderLinesDTO> findOne(Long id) {
        log.debug("Request to get PurchaseOrderLines : {}", id);
        return purchaseOrderLinesRepository.findById(id)
            .map(purchaseOrderLinesMapper::toDto);
    }

    /**
     * Delete the purchaseOrderLines by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrderLines : {}", id);
        purchaseOrderLinesRepository.deleteById(id);
        purchaseOrderLinesSearchRepository.deleteById(id);
    }

    /**
     * Search for the purchaseOrderLines corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrderLinesDTO> search(String query) {
        log.debug("Request to search PurchaseOrderLines for query {}", query);
        return StreamSupport
            .stream(purchaseOrderLinesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(purchaseOrderLinesMapper::toDto)
            .collect(Collectors.toList());
    }
}
