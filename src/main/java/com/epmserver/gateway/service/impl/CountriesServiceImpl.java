package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.CountriesService;
import com.epmserver.gateway.domain.Countries;
import com.epmserver.gateway.repository.CountriesRepository;
import com.epmserver.gateway.repository.search.CountriesSearchRepository;
import com.epmserver.gateway.service.dto.CountriesDTO;
import com.epmserver.gateway.service.mapper.CountriesMapper;
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
 * Service Implementation for managing {@link Countries}.
 */
@Service
@Transactional
public class CountriesServiceImpl implements CountriesService {

    private final Logger log = LoggerFactory.getLogger(CountriesServiceImpl.class);

    private final CountriesRepository countriesRepository;

    private final CountriesMapper countriesMapper;

    private final CountriesSearchRepository countriesSearchRepository;

    public CountriesServiceImpl(CountriesRepository countriesRepository, CountriesMapper countriesMapper, CountriesSearchRepository countriesSearchRepository) {
        this.countriesRepository = countriesRepository;
        this.countriesMapper = countriesMapper;
        this.countriesSearchRepository = countriesSearchRepository;
    }

    /**
     * Save a countries.
     *
     * @param countriesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CountriesDTO save(CountriesDTO countriesDTO) {
        log.debug("Request to save Countries : {}", countriesDTO);
        Countries countries = countriesMapper.toEntity(countriesDTO);
        countries = countriesRepository.save(countries);
        CountriesDTO result = countriesMapper.toDto(countries);
        countriesSearchRepository.save(countries);
        return result;
    }

    /**
     * Get all the countries.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CountriesDTO> findAll() {
        log.debug("Request to get all Countries");
        return countriesRepository.findAll().stream()
            .map(countriesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one countries by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CountriesDTO> findOne(Long id) {
        log.debug("Request to get Countries : {}", id);
        return countriesRepository.findById(id)
            .map(countriesMapper::toDto);
    }

    /**
     * Delete the countries by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Countries : {}", id);
        countriesRepository.deleteById(id);
        countriesSearchRepository.deleteById(id);
    }

    /**
     * Search for the countries corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CountriesDTO> search(String query) {
        log.debug("Request to search Countries for query {}", query);
        return StreamSupport
            .stream(countriesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(countriesMapper::toDto)
            .collect(Collectors.toList());
    }
}
