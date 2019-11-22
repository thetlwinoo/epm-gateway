package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.SupplierCategoriesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupplierCategories} and its DTO {@link SupplierCategoriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SupplierCategoriesMapper extends EntityMapper<SupplierCategoriesDTO, SupplierCategories> {



    default SupplierCategories fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplierCategories supplierCategories = new SupplierCategories();
        supplierCategories.setId(id);
        return supplierCategories;
    }
}
