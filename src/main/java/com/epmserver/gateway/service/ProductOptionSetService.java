package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ProductOptionSetDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.ProductOptionSet}.
 */
public interface ProductOptionSetService {

    /**
     * Save a productOptionSet.
     *
     * @param productOptionSetDTO the entity to save.
     * @return the persisted entity.
     */
    ProductOptionSetDTO save(ProductOptionSetDTO productOptionSetDTO);

    /**
     * Get all the productOptionSets.
     *
     * @return the list of entities.
     */
    List<ProductOptionSetDTO> findAll();


    /**
     * Get the "id" productOptionSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductOptionSetDTO> findOne(Long id);

    /**
     * Delete the "id" productOptionSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the productOptionSet corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ProductOptionSetDTO> search(String query);
}
