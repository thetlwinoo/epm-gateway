package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.SupplierCategoriesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.SupplierCategories}.
 */
public interface SupplierCategoriesService {

    /**
     * Save a supplierCategories.
     *
     * @param supplierCategoriesDTO the entity to save.
     * @return the persisted entity.
     */
    SupplierCategoriesDTO save(SupplierCategoriesDTO supplierCategoriesDTO);

    /**
     * Get all the supplierCategories.
     *
     * @return the list of entities.
     */
    List<SupplierCategoriesDTO> findAll();


    /**
     * Get the "id" supplierCategories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupplierCategoriesDTO> findOne(Long id);

    /**
     * Delete the "id" supplierCategories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the supplierCategories corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<SupplierCategoriesDTO> search(String query);
}
