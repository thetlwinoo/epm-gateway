package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.CountriesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.Countries}.
 */
public interface CountriesService {

    /**
     * Save a countries.
     *
     * @param countriesDTO the entity to save.
     * @return the persisted entity.
     */
    CountriesDTO save(CountriesDTO countriesDTO);

    /**
     * Get all the countries.
     *
     * @return the list of entities.
     */
    List<CountriesDTO> findAll();


    /**
     * Get the "id" countries.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CountriesDTO> findOne(Long id);

    /**
     * Delete the "id" countries.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the countries corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<CountriesDTO> search(String query);
}
