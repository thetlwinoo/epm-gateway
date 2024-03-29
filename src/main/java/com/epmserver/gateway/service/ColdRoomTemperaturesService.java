package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ColdRoomTemperaturesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.ColdRoomTemperatures}.
 */
public interface ColdRoomTemperaturesService {

    /**
     * Save a coldRoomTemperatures.
     *
     * @param coldRoomTemperaturesDTO the entity to save.
     * @return the persisted entity.
     */
    ColdRoomTemperaturesDTO save(ColdRoomTemperaturesDTO coldRoomTemperaturesDTO);

    /**
     * Get all the coldRoomTemperatures.
     *
     * @return the list of entities.
     */
    List<ColdRoomTemperaturesDTO> findAll();


    /**
     * Get the "id" coldRoomTemperatures.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ColdRoomTemperaturesDTO> findOne(Long id);

    /**
     * Delete the "id" coldRoomTemperatures.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the coldRoomTemperatures corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<ColdRoomTemperaturesDTO> search(String query);
}
