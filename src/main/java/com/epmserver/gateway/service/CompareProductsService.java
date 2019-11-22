package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.CompareProductsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.CompareProducts}.
 */
public interface CompareProductsService {

    /**
     * Save a compareProducts.
     *
     * @param compareProductsDTO the entity to save.
     * @return the persisted entity.
     */
    CompareProductsDTO save(CompareProductsDTO compareProductsDTO);

    /**
     * Get all the compareProducts.
     *
     * @return the list of entities.
     */
    List<CompareProductsDTO> findAll();


    /**
     * Get the "id" compareProducts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompareProductsDTO> findOne(Long id);

    /**
     * Delete the "id" compareProducts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the compareProducts corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<CompareProductsDTO> search(String query);
}
