package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.CitiesService;
import com.epmserver.gateway.domain.Cities;
import com.epmserver.gateway.repository.CitiesRepository;
import com.epmserver.gateway.repository.search.CitiesSearchRepository;
import com.epmserver.gateway.service.dto.CitiesDTO;
import com.epmserver.gateway.service.mapper.CitiesMapper;
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
 * Service Implementation for managing {@link Cities}.
 */
@Service
@Transactional
public class CitiesServiceImpl implements CitiesService {

    private final Logger log = LoggerFactory.getLogger(CitiesServiceImpl.class);

    private final CitiesRepository citiesRepository;

    private final CitiesMapper citiesMapper;

    private final CitiesSearchRepository citiesSearchRepository;

    public CitiesServiceImpl(CitiesRepository citiesRepository, CitiesMapper citiesMapper, CitiesSearchRepository citiesSearchRepository) {
        this.citiesRepository = citiesRepository;
        this.citiesMapper = citiesMapper;
        this.citiesSearchRepository = citiesSearchRepository;
    }

    /**
     * Save a cities.
     *
     * @param citiesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CitiesDTO save(CitiesDTO citiesDTO) {
        log.debug("Request to save Cities : {}", citiesDTO);
        Cities cities = citiesMapper.toEntity(citiesDTO);
        cities = citiesRepository.save(cities);
        CitiesDTO result = citiesMapper.toDto(cities);
        citiesSearchRepository.save(cities);
        return result;
    }

    /**
     * Get all the cities.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitiesDTO> findAll() {
        log.debug("Request to get all Cities");
        return citiesRepository.findAll().stream()
            .map(citiesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one cities by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CitiesDTO> findOne(Long id) {
        log.debug("Request to get Cities : {}", id);
        return citiesRepository.findById(id)
            .map(citiesMapper::toDto);
    }

    /**
     * Delete the cities by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cities : {}", id);
        citiesRepository.deleteById(id);
        citiesSearchRepository.deleteById(id);
    }

    /**
     * Search for the cities corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitiesDTO> search(String query) {
        log.debug("Request to search Cities for query {}", query);
        return StreamSupport
            .stream(citiesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(citiesMapper::toDto)
            .collect(Collectors.toList());
    }
}
