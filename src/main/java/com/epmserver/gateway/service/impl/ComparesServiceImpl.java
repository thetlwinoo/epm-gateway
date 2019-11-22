package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ComparesService;
import com.epmserver.gateway.domain.Compares;
import com.epmserver.gateway.repository.ComparesRepository;
import com.epmserver.gateway.repository.search.ComparesSearchRepository;
import com.epmserver.gateway.service.dto.ComparesDTO;
import com.epmserver.gateway.service.mapper.ComparesMapper;
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
 * Service Implementation for managing {@link Compares}.
 */
@Service
@Transactional
public class ComparesServiceImpl implements ComparesService {

    private final Logger log = LoggerFactory.getLogger(ComparesServiceImpl.class);

    private final ComparesRepository comparesRepository;

    private final ComparesMapper comparesMapper;

    private final ComparesSearchRepository comparesSearchRepository;

    public ComparesServiceImpl(ComparesRepository comparesRepository, ComparesMapper comparesMapper, ComparesSearchRepository comparesSearchRepository) {
        this.comparesRepository = comparesRepository;
        this.comparesMapper = comparesMapper;
        this.comparesSearchRepository = comparesSearchRepository;
    }

    /**
     * Save a compares.
     *
     * @param comparesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ComparesDTO save(ComparesDTO comparesDTO) {
        log.debug("Request to save Compares : {}", comparesDTO);
        Compares compares = comparesMapper.toEntity(comparesDTO);
        compares = comparesRepository.save(compares);
        ComparesDTO result = comparesMapper.toDto(compares);
        comparesSearchRepository.save(compares);
        return result;
    }

    /**
     * Get all the compares.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ComparesDTO> findAll() {
        log.debug("Request to get all Compares");
        return comparesRepository.findAll().stream()
            .map(comparesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one compares by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ComparesDTO> findOne(Long id) {
        log.debug("Request to get Compares : {}", id);
        return comparesRepository.findById(id)
            .map(comparesMapper::toDto);
    }

    /**
     * Delete the compares by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Compares : {}", id);
        comparesRepository.deleteById(id);
        comparesSearchRepository.deleteById(id);
    }

    /**
     * Search for the compares corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ComparesDTO> search(String query) {
        log.debug("Request to search Compares for query {}", query);
        return StreamSupport
            .stream(comparesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(comparesMapper::toDto)
            .collect(Collectors.toList());
    }
}
