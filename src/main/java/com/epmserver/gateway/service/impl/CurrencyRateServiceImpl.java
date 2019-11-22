package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.CurrencyRateService;
import com.epmserver.gateway.domain.CurrencyRate;
import com.epmserver.gateway.repository.CurrencyRateRepository;
import com.epmserver.gateway.repository.search.CurrencyRateSearchRepository;
import com.epmserver.gateway.service.dto.CurrencyRateDTO;
import com.epmserver.gateway.service.mapper.CurrencyRateMapper;
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
 * Service Implementation for managing {@link CurrencyRate}.
 */
@Service
@Transactional
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateServiceImpl.class);

    private final CurrencyRateRepository currencyRateRepository;

    private final CurrencyRateMapper currencyRateMapper;

    private final CurrencyRateSearchRepository currencyRateSearchRepository;

    public CurrencyRateServiceImpl(CurrencyRateRepository currencyRateRepository, CurrencyRateMapper currencyRateMapper, CurrencyRateSearchRepository currencyRateSearchRepository) {
        this.currencyRateRepository = currencyRateRepository;
        this.currencyRateMapper = currencyRateMapper;
        this.currencyRateSearchRepository = currencyRateSearchRepository;
    }

    /**
     * Save a currencyRate.
     *
     * @param currencyRateDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CurrencyRateDTO save(CurrencyRateDTO currencyRateDTO) {
        log.debug("Request to save CurrencyRate : {}", currencyRateDTO);
        CurrencyRate currencyRate = currencyRateMapper.toEntity(currencyRateDTO);
        currencyRate = currencyRateRepository.save(currencyRate);
        CurrencyRateDTO result = currencyRateMapper.toDto(currencyRate);
        currencyRateSearchRepository.save(currencyRate);
        return result;
    }

    /**
     * Get all the currencyRates.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CurrencyRateDTO> findAll() {
        log.debug("Request to get all CurrencyRates");
        return currencyRateRepository.findAll().stream()
            .map(currencyRateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one currencyRate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CurrencyRateDTO> findOne(Long id) {
        log.debug("Request to get CurrencyRate : {}", id);
        return currencyRateRepository.findById(id)
            .map(currencyRateMapper::toDto);
    }

    /**
     * Delete the currencyRate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurrencyRate : {}", id);
        currencyRateRepository.deleteById(id);
        currencyRateSearchRepository.deleteById(id);
    }

    /**
     * Search for the currencyRate corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CurrencyRateDTO> search(String query) {
        log.debug("Request to search CurrencyRates for query {}", query);
        return StreamSupport
            .stream(currencyRateSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(currencyRateMapper::toDto)
            .collect(Collectors.toList());
    }
}
