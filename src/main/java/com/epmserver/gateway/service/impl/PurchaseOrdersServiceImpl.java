package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.PurchaseOrdersService;
import com.epmserver.gateway.domain.PurchaseOrders;
import com.epmserver.gateway.repository.PurchaseOrdersRepository;
import com.epmserver.gateway.repository.search.PurchaseOrdersSearchRepository;
import com.epmserver.gateway.service.dto.PurchaseOrdersDTO;
import com.epmserver.gateway.service.mapper.PurchaseOrdersMapper;
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
 * Service Implementation for managing {@link PurchaseOrders}.
 */
@Service
@Transactional
public class PurchaseOrdersServiceImpl implements PurchaseOrdersService {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrdersServiceImpl.class);

    private final PurchaseOrdersRepository purchaseOrdersRepository;

    private final PurchaseOrdersMapper purchaseOrdersMapper;

    private final PurchaseOrdersSearchRepository purchaseOrdersSearchRepository;

    public PurchaseOrdersServiceImpl(PurchaseOrdersRepository purchaseOrdersRepository, PurchaseOrdersMapper purchaseOrdersMapper, PurchaseOrdersSearchRepository purchaseOrdersSearchRepository) {
        this.purchaseOrdersRepository = purchaseOrdersRepository;
        this.purchaseOrdersMapper = purchaseOrdersMapper;
        this.purchaseOrdersSearchRepository = purchaseOrdersSearchRepository;
    }

    /**
     * Save a purchaseOrders.
     *
     * @param purchaseOrdersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PurchaseOrdersDTO save(PurchaseOrdersDTO purchaseOrdersDTO) {
        log.debug("Request to save PurchaseOrders : {}", purchaseOrdersDTO);
        PurchaseOrders purchaseOrders = purchaseOrdersMapper.toEntity(purchaseOrdersDTO);
        purchaseOrders = purchaseOrdersRepository.save(purchaseOrders);
        PurchaseOrdersDTO result = purchaseOrdersMapper.toDto(purchaseOrders);
        purchaseOrdersSearchRepository.save(purchaseOrders);
        return result;
    }

    /**
     * Get all the purchaseOrders.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrdersDTO> findAll() {
        log.debug("Request to get all PurchaseOrders");
        return purchaseOrdersRepository.findAll().stream()
            .map(purchaseOrdersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one purchaseOrders by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseOrdersDTO> findOne(Long id) {
        log.debug("Request to get PurchaseOrders : {}", id);
        return purchaseOrdersRepository.findById(id)
            .map(purchaseOrdersMapper::toDto);
    }

    /**
     * Delete the purchaseOrders by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrders : {}", id);
        purchaseOrdersRepository.deleteById(id);
        purchaseOrdersSearchRepository.deleteById(id);
    }

    /**
     * Search for the purchaseOrders corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrdersDTO> search(String query) {
        log.debug("Request to search PurchaseOrders for query {}", query);
        return StreamSupport
            .stream(purchaseOrdersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(purchaseOrdersMapper::toDto)
            .collect(Collectors.toList());
    }
}
