package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ContactTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.ContactType}.
 */
public interface ContactTypeService {

    /**
     * Save a contactType.
     *
     * @param contactTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ContactTypeDTO save(ContactTypeDTO contactTypeDTO);

    /**
     * Get all the contactTypes.
     *
     * @return the list of entities.
     */
    List<ContactTypeDTO> findAll();


    /**
     * Get the "id" contactType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactTypeDTO> findOne(Long id);

    /**
     * Delete the "id" contactType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the contactType corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ContactTypeDTO> search(String query);
}
