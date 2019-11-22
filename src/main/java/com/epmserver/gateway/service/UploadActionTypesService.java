package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.UploadActionTypesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.UploadActionTypes}.
 */
public interface UploadActionTypesService {

    /**
     * Save a uploadActionTypes.
     *
     * @param uploadActionTypesDTO the entity to save.
     * @return the persisted entity.
     */
    UploadActionTypesDTO save(UploadActionTypesDTO uploadActionTypesDTO);

    /**
     * Get all the uploadActionTypes.
     *
     * @return the list of entities.
     */
    List<UploadActionTypesDTO> findAll();


    /**
     * Get the "id" uploadActionTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UploadActionTypesDTO> findOne(Long id);

    /**
     * Delete the "id" uploadActionTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the uploadActionTypes corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<UploadActionTypesDTO> search(String query);
}
