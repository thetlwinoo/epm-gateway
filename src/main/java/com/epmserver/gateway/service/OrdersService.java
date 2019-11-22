package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.OrdersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmserver.gateway.domain.Orders}.
 */
public interface OrdersService {

    /**
     * Save a orders.
     *
     * @param ordersDTO the entity to save.
     * @return the persisted entity.
     */
    OrdersDTO save(OrdersDTO ordersDTO);

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrdersDTO> findAll(Pageable pageable);
    /**
     * Get all the OrdersDTO where PaymentTransaction is {@code null}.
     *
     * @return the list of entities.
     */
    List<OrdersDTO> findAllWherePaymentTransactionIsNull();


    /**
     * Get the "id" orders.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdersDTO> findOne(Long id);

    /**
     * Delete the "id" orders.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the orders corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrdersDTO> search(String query, Pageable pageable);
}
