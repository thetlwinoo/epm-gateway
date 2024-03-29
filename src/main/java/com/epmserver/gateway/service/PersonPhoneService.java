package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.PersonPhoneDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.PersonPhone}.
 */
public interface PersonPhoneService {

    /**
     * Save a personPhone.
     *
     * @param personPhoneDTO the entity to save.
     * @return the persisted entity.
     */
    PersonPhoneDTO save(PersonPhoneDTO personPhoneDTO);

    /**
     * Get all the personPhones.
     *
     * @return the list of entities.
     */
    List<PersonPhoneDTO> findAll();


    /**
     * Get the "id" personPhone.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonPhoneDTO> findOne(Long id);

    /**
     * Delete the "id" personPhone.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the personPhone corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PersonPhoneDTO> search(String query);
}
