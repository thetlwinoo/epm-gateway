package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ShoppingCartItemsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.ShoppingCartItems}.
 */
public interface ShoppingCartItemsService {

    /**
     * Save a shoppingCartItems.
     *
     * @param shoppingCartItemsDTO the entity to save.
     * @return the persisted entity.
     */
    ShoppingCartItemsDTO save(ShoppingCartItemsDTO shoppingCartItemsDTO);

    /**
     * Get all the shoppingCartItems.
     *
     * @return the list of entities.
     */
    List<ShoppingCartItemsDTO> findAll();


    /**
     * Get the "id" shoppingCartItems.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShoppingCartItemsDTO> findOne(Long id);

    /**
     * Delete the "id" shoppingCartItems.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the shoppingCartItems corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ShoppingCartItemsDTO> search(String query);
}
