package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.OrderLinesService;
import com.epmserver.gateway.domain.OrderLines;
import com.epmserver.gateway.repository.OrderLinesRepository;
import com.epmserver.gateway.repository.search.OrderLinesSearchRepository;
import com.epmserver.gateway.service.dto.OrderLinesDTO;
import com.epmserver.gateway.service.mapper.OrderLinesMapper;
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
 * Service Implementation for managing {@link OrderLines}.
 */
@Service
@Transactional
public class OrderLinesServiceImpl implements OrderLinesService {

    private final Logger log = LoggerFactory.getLogger(OrderLinesServiceImpl.class);

    private final OrderLinesRepository orderLinesRepository;

    private final OrderLinesMapper orderLinesMapper;

    private final OrderLinesSearchRepository orderLinesSearchRepository;

    public OrderLinesServiceImpl(OrderLinesRepository orderLinesRepository, OrderLinesMapper orderLinesMapper, OrderLinesSearchRepository orderLinesSearchRepository) {
        this.orderLinesRepository = orderLinesRepository;
        this.orderLinesMapper = orderLinesMapper;
        this.orderLinesSearchRepository = orderLinesSearchRepository;
    }

    /**
     * Save a orderLines.
     *
     * @param orderLinesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderLinesDTO save(OrderLinesDTO orderLinesDTO) {
        log.debug("Request to save OrderLines : {}", orderLinesDTO);
        OrderLines orderLines = orderLinesMapper.toEntity(orderLinesDTO);
        orderLines = orderLinesRepository.save(orderLines);
        OrderLinesDTO result = orderLinesMapper.toDto(orderLines);
        orderLinesSearchRepository.save(orderLines);
        return result;
    }

    /**
     * Get all the orderLines.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderLinesDTO> findAll() {
        log.debug("Request to get all OrderLines");
        return orderLinesRepository.findAll().stream()
            .map(orderLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orderLines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderLinesDTO> findOne(Long id) {
        log.debug("Request to get OrderLines : {}", id);
        return orderLinesRepository.findById(id)
            .map(orderLinesMapper::toDto);
    }

    /**
     * Delete the orderLines by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderLines : {}", id);
        orderLinesRepository.deleteById(id);
        orderLinesSearchRepository.deleteById(id);
    }

    /**
     * Search for the orderLines corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderLinesDTO> search(String query) {
        log.debug("Request to search OrderLines for query {}", query);
        return StreamSupport
            .stream(orderLinesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(orderLinesMapper::toDto)
            .collect(Collectors.toList());
    }
}
