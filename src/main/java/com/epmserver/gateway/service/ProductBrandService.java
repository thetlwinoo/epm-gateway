package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ProductBrandDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.ProductBrand}.
 */
public interface ProductBrandService {

    /**
     * Save a productBrand.
     *
     * @param productBrandDTO the entity to save.
     * @return the persisted entity.
     */
    ProductBrandDTO save(ProductBrandDTO productBrandDTO);

    /**
     * Get all the productBrands.
     *
     * @return the list of entities.
     */
    List<ProductBrandDTO> findAll();


    /**
     * Get the "id" productBrand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductBrandDTO> findOne(Long id);

    /**
     * Delete the "id" productBrand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the productBrand corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ProductBrandDTO> search(String query);
}
