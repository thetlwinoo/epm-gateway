package com.epmserver.gateway.repository;

import com.epmserver.gateway.domain.ProductAttribute;

import java.util.List;

public interface ProductAttributeExtendRepository extends ProductAttributeRepository {
    List<ProductAttribute> findAllByProductAttributeSetIdAndSupplierId(Long attributeSetId, Long supplierId);
}
