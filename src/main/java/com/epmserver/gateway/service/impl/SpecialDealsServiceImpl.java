package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.SpecialDealsService;
import com.epmserver.gateway.domain.SpecialDeals;
import com.epmserver.gateway.repository.SpecialDealsRepository;
import com.epmserver.gateway.repository.search.SpecialDealsSearchRepository;
import com.epmserver.gateway.service.dto.SpecialDealsDTO;
import com.epmserver.gateway.service.mapper.SpecialDealsMapper;
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
 * Service Implementation for managing {@link SpecialDeals}.
 */
@Service
@Transactional
public class SpecialDealsServiceImpl implements SpecialDealsService {

    private final Logger log = LoggerFactory.getLogger(SpecialDealsServiceImpl.class);

    private final SpecialDealsRepository specialDealsRepository;

    private final SpecialDealsMapper specialDealsMapper;

    private final SpecialDealsSearchRepository specialDealsSearchRepository;

    public SpecialDealsServiceImpl(SpecialDealsRepository specialDealsRepository, SpecialDealsMapper specialDealsMapper, SpecialDealsSearchRepository specialDealsSearchRepository) {
        this.specialDealsRepository = specialDealsRepository;
        this.specialDealsMapper = specialDealsMapper;
        this.specialDealsSearchRepository = specialDealsSearchRepository;
    }

    /**
     * Save a specialDeals.
     *
     * @param specialDealsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SpecialDealsDTO save(SpecialDealsDTO specialDealsDTO) {
        log.debug("Request to save SpecialDeals : {}", specialDealsDTO);
        SpecialDeals specialDeals = specialDealsMapper.toEntity(specialDealsDTO);
        specialDeals = specialDealsRepository.save(specialDeals);
        SpecialDealsDTO result = specialDealsMapper.toDto(specialDeals);
        specialDealsSearchRepository.save(specialDeals);
        return result;
    }

    /**
     * Get all the specialDeals.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpecialDealsDTO> findAll() {
        log.debug("Request to get all SpecialDeals");
        return specialDealsRepository.findAll().stream()
            .map(specialDealsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one specialDeals by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SpecialDealsDTO> findOne(Long id) {
        log.debug("Request to get SpecialDeals : {}", id);
        return specialDealsRepository.findById(id)
            .map(specialDealsMapper::toDto);
    }

    /**
     * Delete the specialDeals by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SpecialDeals : {}", id);
        specialDealsRepository.deleteById(id);
        specialDealsSearchRepository.deleteById(id);
    }

    /**
     * Search for the specialDeals corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpecialDealsDTO> search(String query) {
        log.debug("Request to search SpecialDeals for query {}", query);
        return StreamSupport
            .stream(specialDealsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(specialDealsMapper::toDto)
            .collect(Collectors.toList());
    }
}
