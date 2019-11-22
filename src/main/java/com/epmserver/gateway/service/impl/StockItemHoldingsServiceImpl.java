package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.StockItemHoldingsService;
import com.epmserver.gateway.domain.StockItemHoldings;
import com.epmserver.gateway.repository.StockItemHoldingsRepository;
import com.epmserver.gateway.repository.search.StockItemHoldingsSearchRepository;
import com.epmserver.gateway.service.dto.StockItemHoldingsDTO;
import com.epmserver.gateway.service.mapper.StockItemHoldingsMapper;
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
 * Service Implementation for managing {@link StockItemHoldings}.
 */
@Service
@Transactional
public class StockItemHoldingsServiceImpl implements StockItemHoldingsService {

    private final Logger log = LoggerFactory.getLogger(StockItemHoldingsServiceImpl.class);

    private final StockItemHoldingsRepository stockItemHoldingsRepository;

    private final StockItemHoldingsMapper stockItemHoldingsMapper;

    private final StockItemHoldingsSearchRepository stockItemHoldingsSearchRepository;

    public StockItemHoldingsServiceImpl(StockItemHoldingsRepository stockItemHoldingsRepository, StockItemHoldingsMapper stockItemHoldingsMapper, StockItemHoldingsSearchRepository stockItemHoldingsSearchRepository) {
        this.stockItemHoldingsRepository = stockItemHoldingsRepository;
        this.stockItemHoldingsMapper = stockItemHoldingsMapper;
        this.stockItemHoldingsSearchRepository = stockItemHoldingsSearchRepository;
    }

    /**
     * Save a stockItemHoldings.
     *
     * @param stockItemHoldingsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockItemHoldingsDTO save(StockItemHoldingsDTO stockItemHoldingsDTO) {
        log.debug("Request to save StockItemHoldings : {}", stockItemHoldingsDTO);
        StockItemHoldings stockItemHoldings = stockItemHoldingsMapper.toEntity(stockItemHoldingsDTO);
        stockItemHoldings = stockItemHoldingsRepository.save(stockItemHoldings);
        StockItemHoldingsDTO result = stockItemHoldingsMapper.toDto(stockItemHoldings);
        stockItemHoldingsSearchRepository.save(stockItemHoldings);
        return result;
    }

    /**
     * Get all the stockItemHoldings.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockItemHoldingsDTO> findAll() {
        log.debug("Request to get all StockItemHoldings");
        return stockItemHoldingsRepository.findAll().stream()
            .map(stockItemHoldingsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stockItemHoldings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItemHoldingsDTO> findOne(Long id) {
        log.debug("Request to get StockItemHoldings : {}", id);
        return stockItemHoldingsRepository.findById(id)
            .map(stockItemHoldingsMapper::toDto);
    }

    /**
     * Delete the stockItemHoldings by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItemHoldings : {}", id);
        stockItemHoldingsRepository.deleteById(id);
        stockItemHoldingsSearchRepository.deleteById(id);
    }

    /**
     * Search for the stockItemHoldings corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockItemHoldingsDTO> search(String query) {
        log.debug("Request to search StockItemHoldings for query {}", query);
        return StreamSupport
            .stream(stockItemHoldingsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(stockItemHoldingsMapper::toDto)
            .collect(Collectors.toList());
    }
}
