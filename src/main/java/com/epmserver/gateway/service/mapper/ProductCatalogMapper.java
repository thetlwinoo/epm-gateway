package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.ProductCatalogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductCatalog} and its DTO {@link ProductCatalogDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductCategoryMapper.class, ProductsMapper.class})
public interface ProductCatalogMapper extends EntityMapper<ProductCatalogDTO, ProductCatalog> {

    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ProductCatalogDTO toDto(ProductCatalog productCatalog);

    @Mapping(source = "productCategoryId", target = "productCategory")
    @Mapping(source = "productId", target = "product")
    ProductCatalog toEntity(ProductCatalogDTO productCatalogDTO);

    default ProductCatalog fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductCatalog productCatalog = new ProductCatalog();
        productCatalog.setId(id);
        return productCatalog;
    }
}
