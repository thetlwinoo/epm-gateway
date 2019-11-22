package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.InvoicesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.Invoices}.
 */
public interface InvoicesService {

    /**
     * Save a invoices.
     *
     * @param invoicesDTO the entity to save.
     * @return the persisted entity.
     */
    InvoicesDTO save(InvoicesDTO invoicesDTO);

    /**
     * Get all the invoices.
     *
     * @return the list of entities.
     */
    List<InvoicesDTO> findAll();


    /**
     * Get the "id" invoices.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InvoicesDTO> findOne(Long id);

    /**
     * Delete the "id" invoices.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the invoices corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<InvoicesDTO> search(String query);
}
