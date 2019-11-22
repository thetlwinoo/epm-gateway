package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.ProductOptionSetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOptionSet} and its DTO {@link ProductOptionSetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductOptionSetMapper extends EntityMapper<ProductOptionSetDTO, ProductOptionSet> {



    default ProductOptionSet fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductOptionSet productOptionSet = new ProductOptionSet();
        productOptionSet.setId(id);
        return productOptionSet;
    }
}
