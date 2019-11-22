package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ProductAttributeSetDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.ProductAttributeSet}.
 */
public interface ProductAttributeSetService {

    /**
     * Save a productAttributeSet.
     *
     * @param productAttributeSetDTO the entity to save.
     * @return the persisted entity.
     */
    ProductAttributeSetDTO save(ProductAttributeSetDTO productAttributeSetDTO);

    /**
     * Get all the productAttributeSets.
     *
     * @return the list of entities.
     */
    List<ProductAttributeSetDTO> findAll();


    /**
     * Get the "id" productAttributeSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductAttributeSetDTO> findOne(Long id);

    /**
     * Delete the "id" productAttributeSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the productAttributeSet corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ProductAttributeSetDTO> search(String query);
}
