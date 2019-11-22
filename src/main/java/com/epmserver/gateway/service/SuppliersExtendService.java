package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.SuppliersDTO;

import java.security.Principal;
import java.util.Optional;

public interface SuppliersExtendService {
    Optional<SuppliersDTO> getSupplierByPrincipal(Principal principal);
}
