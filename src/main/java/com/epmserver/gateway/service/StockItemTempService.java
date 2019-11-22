package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.StockItemTempDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.StockItemTemp}.
 */
public interface StockItemTempService {

    /**
     * Save a stockItemTemp.
     *
     * @param stockItemTempDTO the entity to save.
     * @return the persisted entity.
     */
    StockItemTempDTO save(StockItemTempDTO stockItemTempDTO);

    /**
     * Get all the stockItemTemps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockItemTempDTO> findAll(Pageable pageable);


    /**
     * Get the "id" stockItemTemp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockItemTempDTO> findOne(Long id);

    /**
     * Delete the "id" stockItemTemp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the stockItemTemp corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockItemTempDTO> search(String query, Pageable pageable);
}
