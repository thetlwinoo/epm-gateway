package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ProductAttributeDTO;

import java.security.Principal;
import java.util.List;

public interface ProductAttributeExtendService {
    List<ProductAttributeDTO> getAllProductAttributes(Long attributeSetId, Principal principal);
}
