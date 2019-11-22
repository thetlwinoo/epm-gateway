package com.epmserver.gateway.repository;

import com.epmserver.gateway.domain.ProductCategory;

import java.util.List;

public interface ProductCategoryExtendRepository extends ProductCategoryRepository {
    List<ProductCategory> findAllByParentIdIsNull();
    List<ProductCategory> findAllByParentId(Long parentId);
    ProductCategory findProductCategoryByNameContaining(String categoryName);
}
