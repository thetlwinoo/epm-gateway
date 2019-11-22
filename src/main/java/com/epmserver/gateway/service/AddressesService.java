package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.AddressesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.Addresses}.
 */
public interface AddressesService {

    /**
     * Save a addresses.
     *
     * @param addressesDTO the entity to save.
     * @return the persisted entity.
     */
    AddressesDTO save(AddressesDTO addressesDTO);

    /**
     * Get all the addresses.
     *
     * @return the list of entities.
     */
    List<AddressesDTO> findAll();


    /**
     * Get the "id" addresses.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AddressesDTO> findOne(Long id);

    /**
     * Delete the "id" addresses.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the addresses corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<AddressesDTO> search(String query);
}
