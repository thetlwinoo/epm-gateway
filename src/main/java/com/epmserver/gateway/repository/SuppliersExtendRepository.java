package com.epmserver.gateway.repository;

import com.epmserver.gateway.domain.Suppliers;

import java.util.Optional;

public interface SuppliersExtendRepository extends SuppliersRepository {
    Optional<Suppliers> findSuppliersByUserId(Long userId);
}
