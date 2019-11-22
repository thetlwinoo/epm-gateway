package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ProductOptionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.ProductOption}.
 */
public interface ProductOptionService {

    /**
     * Save a productOption.
     *
     * @param productOptionDTO the entity to save.
     * @return the persisted entity.
     */
    ProductOptionDTO save(ProductOptionDTO productOptionDTO);

    /**
     * Get all the productOptions.
     *
     * @return the list of entities.
     */
    List<ProductOptionDTO> findAll();


    /**
     * Get the "id" productOption.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductOptionDTO> findOne(Long id);

    /**
     * Delete the "id" productOption.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the productOption corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ProductOptionDTO> search(String query);
}
