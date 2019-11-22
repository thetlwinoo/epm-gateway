package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.OrdersDTO;

import java.security.Principal;

public interface OrdersExtendService {
    Integer getAllOrdersCount(Principal principal);

    OrdersDTO postOrder(Principal principal, OrdersDTO ordersDTO);
}
