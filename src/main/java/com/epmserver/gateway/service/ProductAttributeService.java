package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ProductAttributeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.ProductAttribute}.
 */
public interface ProductAttributeService {

    /**
     * Save a productAttribute.
     *
     * @param productAttributeDTO the entity to save.
     * @return the persisted entity.
     */
    ProductAttributeDTO save(ProductAttributeDTO productAttributeDTO);

    /**
     * Get all the productAttributes.
     *
     * @return the list of entities.
     */
    List<ProductAttributeDTO> findAll();


    /**
     * Get the "id" productAttribute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductAttributeDTO> findOne(Long id);

    /**
     * Delete the "id" productAttribute.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the productAttribute corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ProductAttributeDTO> search(String query);
}
