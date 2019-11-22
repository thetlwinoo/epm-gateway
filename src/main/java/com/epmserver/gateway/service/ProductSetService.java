package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ProductSetDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.ProductSet}.
 */
public interface ProductSetService {

    /**
     * Save a productSet.
     *
     * @param productSetDTO the entity to save.
     * @return the persisted entity.
     */
    ProductSetDTO save(ProductSetDTO productSetDTO);

    /**
     * Get all the productSets.
     *
     * @return the list of entities.
     */
    List<ProductSetDTO> findAll();


    /**
     * Get the "id" productSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductSetDTO> findOne(Long id);

    /**
     * Delete the "id" productSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the productSet corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ProductSetDTO> search(String query);
}
