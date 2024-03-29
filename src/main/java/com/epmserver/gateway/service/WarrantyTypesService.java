package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.WarrantyTypesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.WarrantyTypes}.
 */
public interface WarrantyTypesService {

    /**
     * Save a warrantyTypes.
     *
     * @param warrantyTypesDTO the entity to save.
     * @return the persisted entity.
     */
    WarrantyTypesDTO save(WarrantyTypesDTO warrantyTypesDTO);

    /**
     * Get all the warrantyTypes.
     *
     * @return the list of entities.
     */
    List<WarrantyTypesDTO> findAll();


    /**
     * Get the "id" warrantyTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WarrantyTypesDTO> findOne(Long id);

    /**
     * Delete the "id" warrantyTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the warrantyTypes corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<WarrantyTypesDTO> search(String query);
}
