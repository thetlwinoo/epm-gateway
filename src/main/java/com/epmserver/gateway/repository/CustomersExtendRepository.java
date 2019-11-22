package com.epmserver.gateway.repository;

import com.epmserver.gateway.domain.Customers;

public interface CustomersExtendRepository extends CustomersRepository {
    Customers findCustomersByUserId(Long userId);
}
