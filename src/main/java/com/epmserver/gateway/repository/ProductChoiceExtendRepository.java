package com.epmserver.gateway.repository;

import com.epmserver.gateway.domain.ProductChoice;

import java.util.List;

public interface ProductChoiceExtendRepository extends ProductChoiceRepository {
    List<ProductChoice> findAllByProductCategoryId(Long categoryId);
}
