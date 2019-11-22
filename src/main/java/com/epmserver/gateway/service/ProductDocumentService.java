package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ProductDocumentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.ProductDocument}.
 */
public interface ProductDocumentService {

    /**
     * Save a productDocument.
     *
     * @param productDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    ProductDocumentDTO save(ProductDocumentDTO productDocumentDTO);

    /**
     * Get all the productDocuments.
     *
     * @return the list of entities.
     */
    List<ProductDocumentDTO> findAll();


    /**
     * Get the "id" productDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" productDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the productDocument corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ProductDocumentDTO> search(String query);
}
