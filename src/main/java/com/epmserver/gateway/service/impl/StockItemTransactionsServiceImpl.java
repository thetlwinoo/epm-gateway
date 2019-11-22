package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.StockItemTransactionsService;
import com.epmserver.gateway.domain.StockItemTransactions;
import com.epmserver.gateway.repository.StockItemTransactionsRepository;
import com.epmserver.gateway.repository.search.StockItemTransactionsSearchRepository;
import com.epmserver.gateway.service.dto.StockItemTransactionsDTO;
import com.epmserver.gateway.service.mapper.StockItemTransactionsMapper;
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
 * Service Implementation for managing {@link StockItemTransactions}.
 */
@Service
@Transactional
public class StockItemTransactionsServiceImpl implements StockItemTransactionsService {

    private final Logger log = LoggerFactory.getLogger(StockItemTransactionsServiceImpl.class);

    private final StockItemTransactionsRepository stockItemTransactionsRepository;

    private final StockItemTransactionsMapper stockItemTransactionsMapper;

    private final StockItemTransactionsSearchRepository stockItemTransactionsSearchRepository;

    public StockItemTransactionsServiceImpl(StockItemTransactionsRepository stockItemTransactionsRepository, StockItemTransactionsMapper stockItemTransactionsMapper, StockItemTransactionsSearchRepository stockItemTransactionsSearchRepository) {
        this.stockItemTransactionsRepository = stockItemTransactionsRepository;
        this.stockItemTransactionsMapper = stockItemTransactionsMapper;
        this.stockItemTransactionsSearchRepository = stockItemTransactionsSearchRepository;
    }

    /**
     * Save a stockItemTransactions.
     *
     * @param stockItemTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockItemTransactionsDTO save(StockItemTransactionsDTO stockItemTransactionsDTO) {
        log.debug("Request to save StockItemTransactions : {}", stockItemTransactionsDTO);
        StockItemTransactions stockItemTransactions = stockItemTransactionsMapper.toEntity(stockItemTransactionsDTO);
        stockItemTransactions = stockItemTransactionsRepository.save(stockItemTransactions);
        StockItemTransactionsDTO result = stockItemTransactionsMapper.toDto(stockItemTransactions);
        stockItemTransactionsSearchRepository.save(stockItemTransactions);
        return result;
    }

    /**
     * Get all the stockItemTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockItemTransactionsDTO> findAll() {
        log.debug("Request to get all StockItemTransactions");
        return stockItemTransactionsRepository.findAll().stream()
            .map(stockItemTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stockItemTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItemTransactionsDTO> findOne(Long id) {
        log.debug("Request to get StockItemTransactions : {}", id);
        return stockItemTransactionsRepository.findById(id)
            .map(stockItemTransactionsMapper::toDto);
    }

    /**
     * Delete the stockItemTransactions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItemTransactions : {}", id);
        stockItemTransactionsRepository.deleteById(id);
        stockItemTransactionsSearchRepository.deleteById(id);
    }

    /**
     * Search for the stockItemTransactions corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockItemTransactionsDTO> search(String query) {
        log.debug("Request to search StockItemTransactions for query {}", query);
        return StreamSupport
            .stream(stockItemTransactionsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(stockItemTransactionsMapper::toDto)
            .collect(Collectors.toList());
    }
}
