package com.epmserver.gateway.repository;

import com.epmserver.gateway.domain.Orders;

import java.util.List;

public interface OrdersExtendRepository extends OrdersRepository {
    List<Orders> findAllByCustomerIdOrderByLastEditedWhenDesc(Long id);
    Integer countAllByCustomerId(Long id);
}
