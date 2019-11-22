package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.OrderLinesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.OrderLines}.
 */
public interface OrderLinesService {

    /**
     * Save a orderLines.
     *
     * @param orderLinesDTO the entity to save.
     * @return the persisted entity.
     */
    OrderLinesDTO save(OrderLinesDTO orderLinesDTO);

    /**
     * Get all the orderLines.
     *
     * @return the list of entities.
     */
    List<OrderLinesDTO> findAll();


    /**
     * Get the "id" orderLines.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderLinesDTO> findOne(Long id);

    /**
     * Delete the "id" orderLines.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the orderLines corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<OrderLinesDTO> search(String query);
}
