package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.StockItemTempService;
import com.epmserver.gateway.domain.StockItemTemp;
import com.epmserver.gateway.repository.StockItemTempRepository;
import com.epmserver.gateway.repository.search.StockItemTempSearchRepository;
import com.epmserver.gateway.service.dto.StockItemTempDTO;
import com.epmserver.gateway.service.mapper.StockItemTempMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link StockItemTemp}.
 */
@Service
@Transactional
public class StockItemTempServiceImpl implements StockItemTempService {

    private final Logger log = LoggerFactory.getLogger(StockItemTempServiceImpl.class);

    private final StockItemTempRepository stockItemTempRepository;

    private final StockItemTempMapper stockItemTempMapper;

    private final StockItemTempSearchRepository stockItemTempSearchRepository;

    public StockItemTempServiceImpl(StockItemTempRepository stockItemTempRepository, StockItemTempMapper stockItemTempMapper, StockItemTempSearchRepository stockItemTempSearchRepository) {
        this.stockItemTempRepository = stockItemTempRepository;
        this.stockItemTempMapper = stockItemTempMapper;
        this.stockItemTempSearchRepository = stockItemTempSearchRepository;
    }

    /**
     * Save a stockItemTemp.
     *
     * @param stockItemTempDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockItemTempDTO save(StockItemTempDTO stockItemTempDTO) {
        log.debug("Request to save StockItemTemp : {}", stockItemTempDTO);
        StockItemTemp stockItemTemp = stockItemTempMapper.toEntity(stockItemTempDTO);
        stockItemTemp = stockItemTempRepository.save(stockItemTemp);
        StockItemTempDTO result = stockItemTempMapper.toDto(stockItemTemp);
        stockItemTempSearchRepository.save(stockItemTemp);
        return result;
    }

    /**
     * Get all the stockItemTemps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockItemTempDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockItemTemps");
        return stockItemTempRepository.findAll(pageable)
            .map(stockItemTempMapper::toDto);
    }


    /**
     * Get one stockItemTemp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItemTempDTO> findOne(Long id) {
        log.debug("Request to get StockItemTemp : {}", id);
        return stockItemTempRepository.findById(id)
            .map(stockItemTempMapper::toDto);
    }

    /**
     * Delete the stockItemTemp by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItemTemp : {}", id);
        stockItemTempRepository.deleteById(id);
        stockItemTempSearchRepository.deleteById(id);
    }

    /**
     * Search for the stockItemTemp corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockItemTempDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StockItemTemps for query {}", query);
        return stockItemTempSearchRepository.search(queryStringQuery(query), pageable)
            .map(stockItemTempMapper::toDto);
    }
}
