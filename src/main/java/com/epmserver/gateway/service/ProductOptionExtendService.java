package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ProductOptionDTO;

import java.security.Principal;
import java.util.List;

public interface ProductOptionExtendService {
    List<ProductOptionDTO> getAllProductOptions(Long optionSetId, Principal principal);
}
