package com.epmserver.gateway.repository;

import com.epmserver.gateway.domain.Addresses;

import java.util.List;

public interface AddressesExtendRepository extends AddressesRepository {
    List<Addresses> findAllByPersonId(Long id);
}
