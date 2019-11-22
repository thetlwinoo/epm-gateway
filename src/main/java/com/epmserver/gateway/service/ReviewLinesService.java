package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ReviewLinesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.ReviewLines}.
 */
public interface ReviewLinesService {

    /**
     * Save a reviewLines.
     *
     * @param reviewLinesDTO the entity to save.
     * @return the persisted entity.
     */
    ReviewLinesDTO save(ReviewLinesDTO reviewLinesDTO);

    /**
     * Get all the reviewLines.
     *
     * @return the list of entities.
     */
    List<ReviewLinesDTO> findAll();
    /**
     * Get all the ReviewLinesDTO where StockItem is {@code null}.
     *
     * @return the list of entities.
     */
    List<ReviewLinesDTO> findAllWhereStockItemIsNull();


    /**
     * Get the "id" reviewLines.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReviewLinesDTO> findOne(Long id);

    /**
     * Delete the "id" reviewLines.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the reviewLines corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ReviewLinesDTO> search(String query);
}
