package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ShipMethodDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.ShipMethod}.
 */
public interface ShipMethodService {

    /**
     * Save a shipMethod.
     *
     * @param shipMethodDTO the entity to save.
     * @return the persisted entity.
     */
    ShipMethodDTO save(ShipMethodDTO shipMethodDTO);

    /**
     * Get all the shipMethods.
     *
     * @return the list of entities.
     */
    List<ShipMethodDTO> findAll();


    /**
     * Get the "id" shipMethod.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShipMethodDTO> findOne(Long id);

    /**
     * Delete the "id" shipMethod.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the shipMethod corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ShipMethodDTO> search(String query);
}
