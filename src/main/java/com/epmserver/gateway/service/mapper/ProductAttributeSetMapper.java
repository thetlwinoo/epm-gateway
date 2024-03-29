package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.ProductAttributeSetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductAttributeSet} and its DTO {@link ProductAttributeSetDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductOptionSetMapper.class})
public interface ProductAttributeSetMapper extends EntityMapper<ProductAttributeSetDTO, ProductAttributeSet> {

    @Mapping(source = "productOptionSet.id", target = "productOptionSetId")
    @Mapping(source = "productOptionSet.value", target = "productOptionSetValue")
    ProductAttributeSetDTO toDto(ProductAttributeSet productAttributeSet);

    @Mapping(source = "productOptionSetId", target = "productOptionSet")
    ProductAttributeSet toEntity(ProductAttributeSetDTO productAttributeSetDTO);

    default ProductAttributeSet fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductAttributeSet productAttributeSet = new ProductAttributeSet();
        productAttributeSet.setId(id);
        return productAttributeSet;
    }
}
