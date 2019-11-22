package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.ProductChoiceDTO;

import java.util.List;

public interface ProductChoiceExtendService {
    List<ProductChoiceDTO> getAllProductChoice(Long categoryId);
}
