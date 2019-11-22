package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.StateProvincesService;
import com.epmserver.gateway.domain.StateProvinces;
import com.epmserver.gateway.repository.StateProvincesRepository;
import com.epmserver.gateway.repository.search.StateProvincesSearchRepository;
import com.epmserver.gateway.service.dto.StateProvincesDTO;
import com.epmserver.gateway.service.mapper.StateProvincesMapper;
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
 * Service Implementation for managing {@link StateProvinces}.
 */
@Service
@Transactional
public class StateProvincesServiceImpl implements StateProvincesService {

    private final Logger log = LoggerFactory.getLogger(StateProvincesServiceImpl.class);

    private final StateProvincesRepository stateProvincesRepository;

    private final StateProvincesMapper stateProvincesMapper;

    private final StateProvincesSearchRepository stateProvincesSearchRepository;

    public StateProvincesServiceImpl(StateProvincesRepository stateProvincesRepository, StateProvincesMapper stateProvincesMapper, StateProvincesSearchRepository stateProvincesSearchRepository) {
        this.stateProvincesRepository = stateProvincesRepository;
        this.stateProvincesMapper = stateProvincesMapper;
        this.stateProvincesSearchRepository = stateProvincesSearchRepository;
    }

    /**
     * Save a stateProvinces.
     *
     * @param stateProvincesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StateProvincesDTO save(StateProvincesDTO stateProvincesDTO) {
        log.debug("Request to save StateProvinces : {}", stateProvincesDTO);
        StateProvinces stateProvinces = stateProvincesMapper.toEntity(stateProvincesDTO);
        stateProvinces = stateProvincesRepository.save(stateProvinces);
        StateProvincesDTO result = stateProvincesMapper.toDto(stateProvinces);
        stateProvincesSearchRepository.save(stateProvinces);
        return result;
    }

    /**
     * Get all the stateProvinces.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StateProvincesDTO> findAll() {
        log.debug("Request to get all StateProvinces");
        return stateProvincesRepository.findAll().stream()
            .map(stateProvincesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stateProvinces by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StateProvincesDTO> findOne(Long id) {
        log.debug("Request to get StateProvinces : {}", id);
        return stateProvincesRepository.findById(id)
            .map(stateProvincesMapper::toDto);
    }

    /**
     * Delete the stateProvinces by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StateProvinces : {}", id);
        stateProvincesRepository.deleteById(id);
        stateProvincesSearchRepository.deleteById(id);
    }

    /**
     * Search for the stateProvinces corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StateProvincesDTO> search(String query) {
        log.debug("Request to search StateProvinces for query {}", query);
        return StreamSupport
            .stream(stateProvincesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(stateProvincesMapper::toDto)
            .collect(Collectors.toList());
    }
}
