package com.epmserver.gateway.repository;

import com.epmserver.gateway.domain.ProductOption;

import java.util.List;

public interface ProductOptionExtendRepository extends ProductOptionRepository {
    List<ProductOption> findAllByProductOptionSetIdAndSupplierId(Long attributeSetId, Long supplierId);
}
