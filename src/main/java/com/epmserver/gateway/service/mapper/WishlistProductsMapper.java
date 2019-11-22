package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.WishlistProductsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WishlistProducts} and its DTO {@link WishlistProductsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class, WishlistsMapper.class})
public interface WishlistProductsMapper extends EntityMapper<WishlistProductsDTO, WishlistProducts> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "wishlist.id", target = "wishlistId")
    WishlistProductsDTO toDto(WishlistProducts wishlistProducts);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "wishlistId", target = "wishlist")
    WishlistProducts toEntity(WishlistProductsDTO wishlistProductsDTO);

    default WishlistProducts fromId(Long id) {
        if (id == null) {
            return null;
        }
        WishlistProducts wishlistProducts = new WishlistProducts();
        wishlistProducts.setId(id);
        return wishlistProducts;
    }
}
