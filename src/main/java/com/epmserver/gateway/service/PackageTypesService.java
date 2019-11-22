package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.PackageTypesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.PackageTypes}.
 */
public interface PackageTypesService {

    /**
     * Save a packageTypes.
     *
     * @param packageTypesDTO the entity to save.
     * @return the persisted entity.
     */
    PackageTypesDTO save(PackageTypesDTO packageTypesDTO);

    /**
     * Get all the packageTypes.
     *
     * @return the list of entities.
     */
    List<PackageTypesDTO> findAll();


    /**
     * Get the "id" packageTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PackageTypesDTO> findOne(Long id);

    /**
     * Delete the "id" packageTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the packageTypes corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PackageTypesDTO> search(String query);
}
