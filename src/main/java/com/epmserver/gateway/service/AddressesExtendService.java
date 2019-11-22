package com.epmserver.gateway.service;

import com.epmserver.gateway.domain.Addresses;
import com.epmserver.gateway.service.dto.AddressesDTO;

import java.security.Principal;
import java.util.List;

public interface AddressesExtendService {
    List<Addresses> fetchAddresses(Principal principal);

    void clearDefaultAddress(Principal principal);

    void setDefaultAddress(Principal principal, Long addressId);

    AddressesDTO crateAddresses(AddressesDTO addressesDTO, Principal principal);

    AddressesDTO updateAddresses(AddressesDTO addressesDTO, Principal principal);
}
