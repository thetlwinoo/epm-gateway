package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ShipMethodService;
import com.epmserver.gateway.domain.ShipMethod;
import com.epmserver.gateway.repository.ShipMethodRepository;
import com.epmserver.gateway.repository.search.ShipMethodSearchRepository;
import com.epmserver.gateway.service.dto.ShipMethodDTO;
import com.epmserver.gateway.service.mapper.ShipMethodMapper;
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
 * Service Implementation for managing {@link ShipMethod}.
 */
@Service
@Transactional
public class ShipMethodServiceImpl implements ShipMethodService {

    private final Logger log = LoggerFactory.getLogger(ShipMethodServiceImpl.class);

    private final ShipMethodRepository shipMethodRepository;

    private final ShipMethodMapper shipMethodMapper;

    private final ShipMethodSearchRepository shipMethodSearchRepository;

    public ShipMethodServiceImpl(ShipMethodRepository shipMethodRepository, ShipMethodMapper shipMethodMapper, ShipMethodSearchRepository shipMethodSearchRepository) {
        this.shipMethodRepository = shipMethodRepository;
        this.shipMethodMapper = shipMethodMapper;
        this.shipMethodSearchRepository = shipMethodSearchRepository;
    }

    /**
     * Save a shipMethod.
     *
     * @param shipMethodDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ShipMethodDTO save(ShipMethodDTO shipMethodDTO) {
        log.debug("Request to save ShipMethod : {}", shipMethodDTO);
        ShipMethod shipMethod = shipMethodMapper.toEntity(shipMethodDTO);
        shipMethod = shipMethodRepository.save(shipMethod);
        ShipMethodDTO result = shipMethodMapper.toDto(shipMethod);
        shipMethodSearchRepository.save(shipMethod);
        return result;
    }

    /**
     * Get all the shipMethods.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipMethodDTO> findAll() {
        log.debug("Request to get all ShipMethods");
        return shipMethodRepository.findAll().stream()
            .map(shipMethodMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shipMethod by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipMethodDTO> findOne(Long id) {
        log.debug("Request to get ShipMethod : {}", id);
        return shipMethodRepository.findById(id)
            .map(shipMethodMapper::toDto);
    }

    /**
     * Delete the shipMethod by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipMethod : {}", id);
        shipMethodRepository.deleteById(id);
        shipMethodSearchRepository.deleteById(id);
    }

    /**
     * Search for the shipMethod corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipMethodDTO> search(String query) {
        log.debug("Request to search ShipMethods for query {}", query);
        return StreamSupport
            .stream(shipMethodSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(shipMethodMapper::toDto)
            .collect(Collectors.toList());
    }
}
