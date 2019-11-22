package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.DangerousGoodsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.DangerousGoods}.
 */
public interface DangerousGoodsService {

    /**
     * Save a dangerousGoods.
     *
     * @param dangerousGoodsDTO the entity to save.
     * @return the persisted entity.
     */
    DangerousGoodsDTO save(DangerousGoodsDTO dangerousGoodsDTO);

    /**
     * Get all the dangerousGoods.
     *
     * @return the list of entities.
     */
    List<DangerousGoodsDTO> findAll();


    /**
     * Get the "id" dangerousGoods.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DangerousGoodsDTO> findOne(Long id);

    /**
     * Delete the "id" dangerousGoods.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the dangerousGoods corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<DangerousGoodsDTO> search(String query);
}
