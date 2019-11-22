package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.StockItemTransactionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.StockItemTransactions}.
 */
public interface StockItemTransactionsService {

    /**
     * Save a stockItemTransactions.
     *
     * @param stockItemTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    StockItemTransactionsDTO save(StockItemTransactionsDTO stockItemTransactionsDTO);

    /**
     * Get all the stockItemTransactions.
     *
     * @return the list of entities.
     */
    List<StockItemTransactionsDTO> findAll();


    /**
     * Get the "id" stockItemTransactions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockItemTransactionsDTO> findOne(Long id);

    /**
     * Delete the "id" stockItemTransactions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the stockItemTransactions corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<StockItemTransactionsDTO> search(String query);
}
