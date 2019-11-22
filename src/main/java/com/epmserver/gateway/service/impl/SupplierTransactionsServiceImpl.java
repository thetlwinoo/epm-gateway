package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.SupplierTransactionsService;
import com.epmserver.gateway.domain.SupplierTransactions;
import com.epmserver.gateway.repository.SupplierTransactionsRepository;
import com.epmserver.gateway.repository.search.SupplierTransactionsSearchRepository;
import com.epmserver.gateway.service.dto.SupplierTransactionsDTO;
import com.epmserver.gateway.service.mapper.SupplierTransactionsMapper;
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
 * Service Implementation for managing {@link SupplierTransactions}.
 */
@Service
@Transactional
public class SupplierTransactionsServiceImpl implements SupplierTransactionsService {

    private final Logger log = LoggerFactory.getLogger(SupplierTransactionsServiceImpl.class);

    private final SupplierTransactionsRepository supplierTransactionsRepository;

    private final SupplierTransactionsMapper supplierTransactionsMapper;

    private final SupplierTransactionsSearchRepository supplierTransactionsSearchRepository;

    public SupplierTransactionsServiceImpl(SupplierTransactionsRepository supplierTransactionsRepository, SupplierTransactionsMapper supplierTransactionsMapper, SupplierTransactionsSearchRepository supplierTransactionsSearchRepository) {
        this.supplierTransactionsRepository = supplierTransactionsRepository;
        this.supplierTransactionsMapper = supplierTransactionsMapper;
        this.supplierTransactionsSearchRepository = supplierTransactionsSearchRepository;
    }

    /**
     * Save a supplierTransactions.
     *
     * @param supplierTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SupplierTransactionsDTO save(SupplierTransactionsDTO supplierTransactionsDTO) {
        log.debug("Request to save SupplierTransactions : {}", supplierTransactionsDTO);
        SupplierTransactions supplierTransactions = supplierTransactionsMapper.toEntity(supplierTransactionsDTO);
        supplierTransactions = supplierTransactionsRepository.save(supplierTransactions);
        SupplierTransactionsDTO result = supplierTransactionsMapper.toDto(supplierTransactions);
        supplierTransactionsSearchRepository.save(supplierTransactions);
        return result;
    }

    /**
     * Get all the supplierTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SupplierTransactionsDTO> findAll() {
        log.debug("Request to get all SupplierTransactions");
        return supplierTransactionsRepository.findAll().stream()
            .map(supplierTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one supplierTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplierTransactionsDTO> findOne(Long id) {
        log.debug("Request to get SupplierTransactions : {}", id);
        return supplierTransactionsRepository.findById(id)
            .map(supplierTransactionsMapper::toDto);
    }

    /**
     * Delete the supplierTransactions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplierTransactions : {}", id);
        supplierTransactionsRepository.deleteById(id);
        supplierTransactionsSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplierTransactions corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SupplierTransactionsDTO> search(String query) {
        log.debug("Request to search SupplierTransactions for query {}", query);
        return StreamSupport
            .stream(supplierTransactionsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(supplierTransactionsMapper::toDto)
            .collect(Collectors.toList());
    }
}
