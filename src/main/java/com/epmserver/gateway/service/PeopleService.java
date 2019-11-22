package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.PeopleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.People}.
 */
public interface PeopleService {

    /**
     * Save a people.
     *
     * @param peopleDTO the entity to save.
     * @return the persisted entity.
     */
    PeopleDTO save(PeopleDTO peopleDTO);

    /**
     * Get all the people.
     *
     * @return the list of entities.
     */
    List<PeopleDTO> findAll();
    /**
     * Get all the PeopleDTO where Cart is {@code null}.
     *
     * @return the list of entities.
     */
    List<PeopleDTO> findAllWhereCartIsNull();
    /**
     * Get all the PeopleDTO where Wishlist is {@code null}.
     *
     * @return the list of entities.
     */
    List<PeopleDTO> findAllWhereWishlistIsNull();
    /**
     * Get all the PeopleDTO where Compare is {@code null}.
     *
     * @return the list of entities.
     */
    List<PeopleDTO> findAllWhereCompareIsNull();


    /**
     * Get the "id" people.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PeopleDTO> findOne(Long id);

    /**
     * Delete the "id" people.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the people corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PeopleDTO> search(String query);
}
