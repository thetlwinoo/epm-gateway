package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.SupplierTransactionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.SupplierTransactions}.
 */
public interface SupplierTransactionsService {

    /**
     * Save a supplierTransactions.
     *
     * @param supplierTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    SupplierTransactionsDTO save(SupplierTransactionsDTO supplierTransactionsDTO);

    /**
     * Get all the supplierTransactions.
     *
     * @return the list of entities.
     */
    List<SupplierTransactionsDTO> findAll();


    /**
     * Get the "id" supplierTransactions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupplierTransactionsDTO> findOne(Long id);

    /**
     * Delete the "id" supplierTransactions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the supplierTransactions corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<SupplierTransactionsDTO> search(String query);
}
