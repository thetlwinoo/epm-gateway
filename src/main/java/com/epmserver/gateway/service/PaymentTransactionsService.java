package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.PaymentTransactionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.PaymentTransactions}.
 */
public interface PaymentTransactionsService {

    /**
     * Save a paymentTransactions.
     *
     * @param paymentTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentTransactionsDTO save(PaymentTransactionsDTO paymentTransactionsDTO);

    /**
     * Get all the paymentTransactions.
     *
     * @return the list of entities.
     */
    List<PaymentTransactionsDTO> findAll();


    /**
     * Get the "id" paymentTransactions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentTransactionsDTO> findOne(Long id);

    /**
     * Delete the "id" paymentTransactions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the paymentTransactions corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PaymentTransactionsDTO> search(String query);
}
